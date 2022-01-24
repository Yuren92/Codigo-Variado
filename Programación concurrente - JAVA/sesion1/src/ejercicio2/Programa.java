package ejercicio2;

public class Programa {

	public static void main(String[] args) {
		Ejercicio2 hola = new Ejercicio2("Hola");
		Ejercicio2 mundo = new Ejercicio2("Mundo");
		
		Thread h = new Thread(hola);
		Thread m = new Thread(mundo);
		
		h.start();
		m.start();
		
		try {
			 h.join();
			 m.join();
			 } catch (InterruptedException e) {
			 e.printStackTrace();
			 } 
		
		System.out.println("Fin del programa");

	}

}
