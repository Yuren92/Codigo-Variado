package es.um.redes.Confiable.Intercambio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

import es.um.redes.Confiable.Medio.MediumPacket;
import es.um.redes.Confiable.Medio.MediumSocket;
import es.um.redes.intercambio.impl.Segment;

public class TimeoutTask extends TimerTask {
	// aqui establezco el reenvio de los paquetes perdidos
	// en cada inicio del temporizador en reliable se genera un objeto de tipo
	// timeouttask
	// se cancela al recibir el ack
	private InetSocketAddress dir;
	private ReentrantLock cerrojoMedium;
	private ReliableConnectionImpl conection;
	private MediumSocket medium;
	private Segment segTimeout;

	public TimeoutTask(MediumSocket medium, ReentrantLock cerrojoMedium,
			ReliableConnectionImpl conexion, InetSocketAddress direccion,
			Segment seg) {
		this.cerrojoMedium = cerrojoMedium;
		this.conection = conexion;
		this.segTimeout = seg;
		this.medium = medium;
		this.dir = direccion;
	}
	@Override
	public void run() {
		byte[] crudosSeg = segTimeout.toByteArray();
		MediumPacket paquete = new MediumPacket(crudosSeg, crudosSeg.length,
				dir);
		try {
			cerrojoMedium.lock();
			medium.send(paquete);
			cerrojoMedium.unlock();
		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	@Override
	public String toString() {
		return "TimeoutTask [dir=" + dir + ", cerrojoMedium=" + cerrojoMedium
				+ ", conection=" + conection + ", medium=" + medium
				+ ", segTimeout=" + segTimeout + "]";
	}
}
