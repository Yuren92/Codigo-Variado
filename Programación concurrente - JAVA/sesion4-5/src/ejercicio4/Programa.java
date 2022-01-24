package ejercicio4;

public class Programa {

	public static void main(String[] args) {
		MonitorSaludo _monitor = new MonitorSaludo(10);
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
