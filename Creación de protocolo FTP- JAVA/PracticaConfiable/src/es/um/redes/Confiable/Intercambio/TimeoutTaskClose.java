package es.um.redes.Confiable.Intercambio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

import es.um.redes.Confiable.Medio.MediumPacket;
import es.um.redes.Confiable.Medio.MediumSocket;
import es.um.redes.intercambio.impl.Segment;

public class TimeoutTaskClose extends TimerTask {
	private InetSocketAddress dir;
	private ReentrantLock cerrojoMedium;
	private ReliableConnectionImpl conection;
	private MediumSocket medium;
	private Segment segTimeout;
	private static final int MAX_INTENTOS = 5;
	private int intentosRealizados;

	public TimeoutTaskClose(MediumSocket medium, ReentrantLock cerrojoMedium,
			ReliableConnectionImpl conexion, InetSocketAddress direccion,
			Segment seg) {
		this.cerrojoMedium = cerrojoMedium;
		this.conection = conexion;
		this.segTimeout = seg;
		this.medium = medium;
		this.dir = direccion;
		this.intentosRealizados = 0;
	}

	@Override
	public void run() {
		int op = segTimeout.getOperacion();
		switch (op) {
		case 2:
			byte[] puros = segTimeout.toByteArray();
			MediumPacket paq = new MediumPacket(puros, puros.length, dir);
			cerrojoMedium.lock();
			try {
				medium.send(paq);
			} catch (IOException e) {
				e.printStackTrace();
			}
			cerrojoMedium.unlock();
			break;
		case 3:
			intentosRealizados++;
			if (intentosRealizados < MAX_INTENTOS) {
				byte[] datos = segTimeout.toByteArray();
				MediumPacket reenvio = new MediumPacket(datos, datos.length,
						dir);
				cerrojoMedium.lock();
				try {
					medium.send(reenvio);
				} catch (IOException e) {
					e.printStackTrace();
				}
				cerrojoMedium.unlock();
			}
			break;
		}

	}
}
