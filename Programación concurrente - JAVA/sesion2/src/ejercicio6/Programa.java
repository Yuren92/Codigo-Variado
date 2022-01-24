package ejercicio6;

public class Programa {

	public static void main(String[] args) {
		Thread a = new Productor();
		Thread b = new Consumidor();
		a.start();
		b.start();
		
		try {
			a.join();
			b.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Fin del hilo princiapl");

	}

}
