package es.um.redes.intercambio.impl;

import java.nio.ByteBuffer;
import java.util.Arrays;

import es.um.redes.Confiable.Intercambio.data.SegmentIface;

public class Segment implements SegmentIface {
	private byte operacion;
	private byte confirmacion;
	private int checksum;
	private byte Nsegmento;
	private byte[] datos;
	private int mss;

	public Segment(byte op, byte confirmacion, byte nsegm, byte[] msj, int mss) {
		this.operacion = op;
		this.confirmacion = confirmacion;
		this.Nsegmento = nsegm;
		this.datos = msj;
		this.mss = mss;
		checksum();
	}

	public Segment() {
	}

	public byte getOperacion() {
		return operacion;
	}

	@Override
	public int getSeq_n() {
		return Nsegmento;
	}

	@Override
	public int getAck_n() {
		return confirmacion;
	}

	@Override
	public int getData_length() {
		return datos.length;
	}

	@Override
	public byte[] getData() {
		return datos;
	}

	public byte[] concatenar(byte[] a, byte[] b) {
		byte[] aux = new byte[(a.length + b.length)];
		for (int i = 0; i < a.length; i++) {
			aux[i] = a[i];
		}
		for (int i = 0; i < b.length; i++) {
			aux[a.length + i] = b[i];
		}
		return aux;
	}

	// auxiliar para generar un segmento concreto
	public void generarSegmentoSYN(int mss, byte segmento) {
		this.operacion = 1;
		this.mss = mss;
		this.Nsegmento = segmento;
		checksum();
	}

	// generar segmento de cierre de conexion
	public void segmentoCierre(byte numSec) {
		this.operacion = 2;
		this.Nsegmento = numSec;
		checksum();
	}

	// generar segmento con datos
	public void generarSegmentoDatos(byte[] informacion, byte nsegmento) {
		this.operacion = 4;
		this.datos = informacion;
		this.Nsegmento = nsegmento;
		checksum();
	}

	// generar segmento ack
	public void generarSegmentoConfirmacion(byte conf) {
		this.operacion = 3;
		this.confirmacion = conf;
		checksum();
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer bOperacion = ByteBuffer.allocate(3);
		byte aux = 1;
		bOperacion.put(aux);
		bOperacion.put(aux);
		bOperacion.put(operacion);
		switch (operacion) {
		case 1:
			// establecer conexion
			ByteBuffer mssc1 = ByteBuffer.allocate(6);
			aux = 5;
			mssc1.put(aux);
			aux = 4;
			mssc1.put(aux);
			mssc1.putInt(mss);
			byte[] sol1 = concatenar(bOperacion.array(), mssc1.array());
			ByteBuffer nsegc1 = ByteBuffer.allocate(3);
			aux = 6;
			nsegc1.put(aux);
			aux = 1;
			nsegc1.put(aux);
			nsegc1.put(Nsegmento);
			byte[] sol12 = concatenar(sol1, nsegc1.array());
			ByteBuffer checksumc1 = ByteBuffer.allocate(6);
			aux = 3;
			checksumc1.put(aux);
			aux = 4;
			checksumc1.put(aux);
			checksumc1.putInt(checksum);
			byte[] sol13 = concatenar(sol12, checksumc1.array());
			return sol13;
		case 2:
			// desconexion
			ByteBuffer nsegc2 = ByteBuffer.allocate(3);
			aux = 6;
			nsegc2.put(aux);
			aux = 1;
			nsegc2.put(aux);
			nsegc2.put(Nsegmento);
			byte[] sol2 = concatenar(bOperacion.array(), nsegc2.array());
			ByteBuffer checksumc2 = ByteBuffer.allocate(6);
			aux = 3;
			checksumc2.put(aux);
			aux = 1;
			checksumc2.put(aux);
			checksumc2.putInt(checksum);
			byte[] sol21 = concatenar(sol2, checksumc2.array());
			return sol21;
		case 3:
			// ack
			ByteBuffer ackc3 = ByteBuffer.allocate(3);
			aux = 2;
			ackc3.put(aux);
			aux = 1;
			ackc3.put(aux);
			ackc3.put(confirmacion);
			byte[] sol3 = concatenar(bOperacion.array(), ackc3.array());
			ByteBuffer checksumc3 = ByteBuffer.allocate(6);
			aux = 3;
			checksumc3.put(aux);
			aux = 4;
			checksumc3.put(aux);
			checksumc3.putInt(checksum);
			byte[] sol31 = concatenar(sol3, checksumc3.array());
			return sol31;
		case 4:
			// envio de datos
			ByteBuffer nsegc4 = ByteBuffer.allocate(3);
			aux = 6;
			nsegc4.put(aux);
			aux = 1;
			nsegc4.put(aux);
			nsegc4.put(Nsegmento);
			byte[] sol4 = concatenar(bOperacion.array(), nsegc4.array());
			int len = datos.length;
			len = len + 5;
			ByteBuffer sendmsjc4 = ByteBuffer.allocate(len);
			aux = 4;
			sendmsjc4.put(aux);
			len = datos.length;
			sendmsjc4.putInt(len);
			sendmsjc4.put(datos);
			byte[] sol41 = concatenar(sol4, sendmsjc4.array());
			ByteBuffer checksumc4 = ByteBuffer.allocate(6);
			aux = 3;
			checksumc4.put(aux);
			aux = 4;
			checksumc4.put(aux);
			checksumc4.putInt(checksum);
			byte[] sol42 = concatenar(sol41, checksumc4.array());
			return sol42;
		case 5:
			// envio de datos mas ack
			ByteBuffer nsegmentc5 = ByteBuffer.allocate(6);
			aux = 6;
			nsegmentc5.put(aux);
			aux = 4;
			nsegmentc5.put(aux);
			nsegmentc5.put(Nsegmento);
			byte[] sol5 = concatenar(bOperacion.array(), nsegmentc5.array());

			ByteBuffer ackc5 = ByteBuffer.allocate(3);
			aux = 2;
			ackc5.put(aux);
			aux = 1;
			ackc5.put(aux);
			ackc5.put(confirmacion);
			byte[] sol51 = concatenar(sol5, ackc5.array());

			int longitud = datos.length;
			longitud = longitud + 2;
			ByteBuffer sendmsjc5 = ByteBuffer.allocate(longitud);
			aux = 4;
			sendmsjc5.put(aux);
			len = datos.length;
			sendmsjc5.putInt(len);
			sendmsjc5.put(datos);
			byte[] sol52 = concatenar(sol51, sendmsjc5.array());

			ByteBuffer checksumc5 = ByteBuffer.allocate(6);
			aux = 3;
			checksumc5.put(aux);
			aux = 4;
			checksumc5.put(aux);
			checksumc5.putInt(checksum);
			byte[] sol53 = concatenar(sol52, checksumc5.array());
			return sol53;

		default:
			System.err.println("Operaci??n incorrecta\n");
			return null;
		}
	}

	@Override
	public void fromByteArray(byte[] buf) {
		ByteBuffer b = ByteBuffer.wrap(buf);
		byte cod = b.get();
		cod = b.get();
		cod = b.get();
		this.operacion = cod;
		switch (cod) {
		case 1:
			b.get();
			b.get();
			this.mss = b.getInt();

			b.get();
			b.get();
			this.Nsegmento = b.get();

			b.get();
			b.get();
			this.checksum = b.getInt();

			break;
		case 2:
			b.get();
			b.get();
			this.Nsegmento = b.get();

			b.get();
			b.get();
			this.checksum = b.getInt();
			break;
		case 3:

			b.get();
			b.get();
			this.confirmacion = b.get();

			b.get();
			b.get();
			this.checksum = b.getInt();

			break;
		case 4:
			b.get();
			b.get();
			this.Nsegmento = b.get();

			b.get();
			int z = b.getInt();
			datos = new byte[z];
			for (int i = 0; i < z; i++) {
				datos[i] = b.get();
			}

			b.get();
			b.get();
			this.checksum = b.getInt();

			break;
		case 5:
			b.get();
			b.get();
			this.Nsegmento = b.get();

			b.get();
			b.get();
			this.confirmacion = b.get();

			b.get();
			int h = b.getInt();
			datos = new byte[h];
			for (int i = 0; i < h; i++) {
				datos[i] = b.get();
			}

			b.get();
			b.get();
			this.checksum = b.getInt();
			break;
		default:
			System.err.println("Datos introducidos incorrectos\n");
			break;
		}
	}

	public void checksum() {
		this.checksum = 0;
		java.util.zip.Checksum chck = new java.util.zip.CRC32();
		byte[] aux = this.toByteArray();
		chck.update(aux, 0, aux.length);
		long valorChecksum = chck.getValue();
		this.checksum = (int) valorChecksum;
	}

	public boolean checksumComprobacion() {
		int chk = this.checksum;
		this.checksum = 0;
		this.checksum();
		if (chk == this.checksum) {
			this.checksum = chk;
			return true;
		} else {
			this.checksum = chk;
			return false;
		}
	}

	@Override
	public String toString() {
		return "Segment [operacion=" + operacion + ", confirmacion="
				+ confirmacion + ", checksum=" + checksum + ", Nsegmento="
				+ Nsegmento + ", datos=" + Arrays.toString(datos) + ", mss="
				+ mss + "]";
	}

}
