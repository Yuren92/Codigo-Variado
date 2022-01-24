package ejercicio3;

public class Programa {

	public static void main(String[] args) {
		Ejercicio3 a = new Ejercicio3();
		Ejercicio3 b = new Ejercicio3();
		a.start();
		b.start();
		
		try {
			a.join();
			b.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Valor final " + Ejercicio3.valor);
		
		
	}

}
