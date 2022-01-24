package ejercicio5;

import java.util.concurrent.Semaphore;

public class Programa {
	public static Semaphore hola=new Semaphore(1);
	public static Semaphore amigos=new Semaphore(0);
	public static Semaphore del=new Semaphore(0);
	public static Semaphore mundo=new Semaphore(0);
	
	public static void main(String[] args) {
		Thread a = new HiloHola();
		Thread b = new HiloAmigos();
		Thread c = new HiloDel();
		Thread d = new HiloMundo();
		
		a.start();
		b.start();
		c.start();
		d.start();
		
		try {
			a.join();
			b.join();
			c.join();
			d.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Fin del hilo principal ");
	} 

}
