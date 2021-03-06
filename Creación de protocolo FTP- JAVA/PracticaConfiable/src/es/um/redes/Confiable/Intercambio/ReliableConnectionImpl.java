package es.um.redes.Confiable.Intercambio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Timer;
import java.util.concurrent.locks.ReentrantLock;

import es.um.redes.Confiable.App.Log;
import es.um.redes.Confiable.Intercambio.util.RecvBuffer;
import es.um.redes.Confiable.Medio.MediumPacket;
import es.um.redes.Confiable.Medio.MediumSocket;
import es.um.redes.intercambio.impl.Segment;

public class ReliableConnectionImpl implements ReliableConnectionIface {

	private static final int PORT = 8009;
	private static final int TIMEOUT = 1500;

	private static final String ENVIO_DATOS = "Envío de datos";
	private static final String ENVIO_ACK = "Envío de segmento de ack";
	private static final String ENVIO_SYN = "Envío de segmento SYN";
	private static final String ENVIO_FIN = "Envío de segmento FIN";
	private static final String RECIBE_DATOS = "Recibe datos";
	private static final String RECIBE_ACK = "Recibe confirmación o segmento de ack";
	private static final String RECIBE_SYN = "Recibe segmento SYN";
	private static final String RECIBE_FIN = "Recibe segmento FIN";

	private MediumSocket socket;
	private int MSS = 500;
	private byte numSec;
	private RecvBuffer bufferRecibe;
	private InetSocketAddress addr;
	private boolean cliente;
	private ReentrantLock cerrojoTimeout;
	private Timer marcaTiempo;
	private Log log;

	public ReliableConnectionImpl(String LogID) {
		bufferRecibe = new RecvBuffer();
		cliente = false;
		cerrojoTimeout = new ReentrantLock();
		marcaTiempo = new Timer();
		this.log = new Log(LogID);
	}

	@Override
	public void connect(InetAddress to) throws IOException {
		addr = new InetSocketAddress(to, PORT);
		socket = new MediumSocket();
		Segment segmento = new Segment();
		cliente = true;
		segmento.generarSegmentoSYN(MSS, numSec);
		byte[] buf = segmento.toByteArray();
		MediumPacket paq = new MediumPacket(buf, buf.length, addr);
		socket.send(paq);
		cambiarNumSec();
		log.add(ENVIO_SYN, segmento);
		TimeoutTask t = new TimeoutTask(socket, cerrojoTimeout, this, addr,
				segmento);
		marcaTiempo.schedule(t, TIMEOUT);
		MediumPacket confirmacion = new MediumPacket();
		boolean flag = true;
		while (flag) {
			confirmacion = socket.receive();
			if (confirmacion != null) {
				Segment segmentoAux = new Segment();
				byte[] buffer = confirmacion.getData();
				segmentoAux.fromByteArray(buffer);
				if (segmentoAux.checksumComprobacion()) {
					log.add(RECIBE_ACK, segmentoAux);
					t.cancel();
					flag = false;
				}
			}
		}

	}

	@Override
	public void accept() throws IOException {
		cliente = false;
		socket = new MediumSocket(PORT);
		MediumPacket recibe = new MediumPacket();
		Segment segmentoAux = new Segment();
		recibe = socket.receive();
		while (recibe == null) {
			recibe = socket.receive();
		}
		byte[] buffer = recibe.getData();
		segmentoAux.fromByteArray(buffer);
		addr = recibe.getInetAddr();
		if (segmentoAux.checksumComprobacion()) {
			log.add(RECIBE_SYN, segmentoAux);
			Segment confirmacion = new Segment();
			confirmacion.generarSegmentoConfirmacion(cambiaSec(segmentoAux));
			byte[] buf = confirmacion.toByteArray();
			MediumPacket paq = new MediumPacket(buf, buf.length, addr);
			socket.send(paq);
			log.add(ENVIO_ACK, confirmacion);
			// deberia esperar una confirmacion de establecimiento de conexion?
		}
	}

	@Override
	public void close() throws IOException {
		if (cliente == true) {
			// send FIN
			Segment FIN = new Segment();
			FIN.segmentoCierre(numSec);
			byte[] data = FIN.toByteArray();
			MediumPacket fin = new MediumPacket(data, data.length, addr);
			TimeoutTaskClose c = new TimeoutTaskClose(socket, cerrojoTimeout,
					this, addr, FIN);
			marcaTiempo.schedule(c, TIMEOUT);
			// envia fin
			socket.send(fin);
			log.add(ENVIO_FIN, FIN);
			// receive ack
			MediumPacket paq = new MediumPacket();
			paq = socket.receive();
			while (paq == null) {
				paq = socket.receive();
			}
			Segment aux = new Segment();
			byte[] aux2 = paq.getData();
			aux.fromByteArray(aux2);
			if (aux.checksumComprobacion()) {
				c.cancel();
				log.add(RECIBE_ACK, aux);
			}

			// receive fin
			MediumPacket paq2 = new MediumPacket();
			paq2 = socket.receive();
			while (paq2 == null)
				paq2 = socket.receive();
			Segment aux3 = new Segment();
			byte[] datospaq = paq2.getData();
			aux3.fromByteArray(datospaq);
			if (aux3.checksumComprobacion()) {
				log.add(RECIBE_FIN, aux3);
				// send ack
				aux3.generarSegmentoConfirmacion(cambiaSec(aux3));
				byte[] datos = aux3.toByteArray();
				MediumPacket closeconn = new MediumPacket(datos, datos.length,
						addr);
				socket.send(closeconn);
				log.add(ENVIO_ACK, aux3);
				log.cerrarFichero();
			}
		} else {
			// caso servidor
			// receive fin
			MediumPacket paqfin = new MediumPacket();
			paqfin = socket.receive();
			while (paqfin == null)
				paqfin = socket.receive();
			Segment s = new Segment();
			byte[] datos = paqfin.getData();
			s.fromByteArray(datos);
			if (s.checksumComprobacion()) {
				log.add(RECIBE_FIN, s);
				// send ack
				Segment s1 = new Segment();
				s1.generarSegmentoConfirmacion(cambiaSec(s));
				byte[] datosPuros = s1.toByteArray();
				MediumPacket ackfin = new MediumPacket(datosPuros,
						datosPuros.length, addr);
				socket.send(ackfin);
				log.add(ENVIO_ACK, s1);
			}

			// send fin
			Segment segfin = new Segment();
			segfin.segmentoCierre(numSec);
			byte[] datasegfin = segfin.toByteArray();
			MediumPacket segundoenvio = new MediumPacket(datasegfin,
					datasegfin.length, addr);
			TimeoutTaskClose close = new TimeoutTaskClose(socket,
					cerrojoTimeout, this, addr, segfin);
			marcaTiempo.schedule(close, TIMEOUT);
			socket.send(segundoenvio);
			log.add(ENVIO_FIN, segfin);
			// receive ack
			MediumPacket paq3 = new MediumPacket();
			paq3 = socket.receive();
			while (paq3 == null)
				paq3 = socket.receive();
			Segment s1 = new Segment();
			byte[] datos1 = paq3.getData();
			s1.fromByteArray(datos1);
			if (s1.checksumComprobacion()) {
				log.add(RECIBE_ACK, s1);
				log.cerrarFichero();
				close.cancel();
			}
		}
	}

	@Override
	public int getMSS() {
		return MSS;
	}

	@Override
	public void setMSS(int mss) {
		this.MSS = (byte) mss;

	}

	@Override
	public int getTimeoutValue() {
		return TIMEOUT;
	}

	@Override
	public InetSocketAddress getPeerAddress() {
		return addr;
	}

	public int write(byte[] buffer) throws IOException {
		int datosRestantes = buffer.length;
		int datosEnviados = 0;
		int datosAEnviar = 0;
		while (datosRestantes > 0) {
			datosAEnviar = Math.min(datosRestantes, MSS);
			byte[] data = new byte[datosAEnviar];
			data = Arrays.copyOfRange(buffer, datosEnviados, datosEnviados
					+ datosAEnviar);
			datosEnviados = datosEnviados + datosAEnviar;
			datosRestantes = datosRestantes - datosAEnviar;
			// creo segmento y envio segmento
			Segment s = new Segment();
			s.generarSegmentoDatos(data, numSec);
			byte[] datos = s.toByteArray();
			MediumPacket paquete = new MediumPacket(datos, datos.length, addr);
			socket.send(paquete);
			cambiarNumSec();
			log.add(ENVIO_DATOS, s);
			TimeoutTask t = new TimeoutTask(socket, cerrojoTimeout, this, addr,
					s);
			marcaTiempo.schedule(t, TIMEOUT);
			boolean flag = true;
			MediumPacket ack = new MediumPacket();
			while (flag) {
				ack = socket.receive();
				if (ack != null) {
					Segment segmento = new Segment();
					byte[] puros = ack.getData();
					segmento.fromByteArray(puros);
					if (segmento.checksumComprobacion()) {
						log.add(RECIBE_ACK, segmento);
						t.cancel();
						flag = false;
					}

				}
				// actualizo nseg
			}
		}
		// mientras que me queden cosas envio segmento y espero ack
		return buffer.length;

	}

	@Override
	public int read(byte[] buffer) throws IOException {
		MediumPacket recibidos = new MediumPacket();
		while (bufferRecibe.getAvailableDataLength() < buffer.length) {
			recibidos = socket.receive();
			// recibo
			while (recibidos == null) {
				recibidos = socket.receive();
			}
			Segment datos = new Segment();
			byte[] puros = recibidos.getData();
			datos.fromByteArray(puros);
			if (datos.checksumComprobacion()) {
				log.add(RECIBE_DATOS, datos);
				bufferRecibe.addReceivedSegment(datos);
				Segment confirmacion = new Segment();
				confirmacion.generarSegmentoConfirmacion(cambiaSec(datos));
				byte[] conf = confirmacion.toByteArray();
				MediumPacket ack = new MediumPacket(conf, conf.length, addr);
				socket.send(ack);
				log.add(ENVIO_ACK, confirmacion);
			}
		}
		bufferRecibe.obtainDataInOrder(buffer);
		return buffer.length;
	}

	public void cierraFichero() {
		log.cerrarFichero();
	}

	public void cambiarNumSec() {
		switch (numSec) {
		case 0:
			numSec = 1;
			break;

		default:
			numSec = 0;
			break;
		}
	}

	public byte cambiaSec(Segment s) {
		if (s.getSeq_n() == 0)
			return 1;
		else
			return 0;
	}

}
