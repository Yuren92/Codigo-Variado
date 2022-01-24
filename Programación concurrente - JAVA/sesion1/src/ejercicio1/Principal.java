package ejercicio1;

public class Principal {

	public static void main(String[] args) {
		
		Thread hola = new Ejercicio1("Hola");
		Thread mundo = new Ejercicio1("Mundo");
		
		hola.start();
		mundo.start();
		
		System.out.println("Final del programa");

	}

}
