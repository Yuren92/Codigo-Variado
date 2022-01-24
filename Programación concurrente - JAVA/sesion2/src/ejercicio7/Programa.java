package ejercicio7;

public class Programa {

	public static void main(String[] args) {
		Thread a = new Productor(1);
		Thread b = new Consumidor(1);
		Thread c = new Productor(2);
		Thread d = new Consumidor(2);
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
		
		System.out.println("Fin del hilo princiapl");

	}

}
