package ejercicio3;

public class Programa {

	public static void main(String[] args) {
		Thread h1 = new Sacar();
		Thread h2 = new Ingresar();
		
		h1.start();
		h2.start();
		
		
		try {
			h1.join();
			h2.join();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
		System.out.println("Operacion: " + MemoriaGlobal.a);
		

	}

}
