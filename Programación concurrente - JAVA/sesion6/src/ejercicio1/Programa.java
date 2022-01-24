package ejercicio1;

import messagepassing.MailBox;

public class Programa {

	public static void main(String[] args) {
		MailBox m1 = new MailBox();
		MailBox m2 = new MailBox();
		MailBox m3 = new MailBox();
		MailBox m4 = new MailBox();
	
		Thread a = new HiloHola(m1,m2,1);
		Thread b = new HiloAmigos(m2,m3,1);
		Thread c = new HiloDel(m3,m4,1);
		Thread d = new HiloMundo(m4,m1,1);
		
		m1.send(null);
		
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
