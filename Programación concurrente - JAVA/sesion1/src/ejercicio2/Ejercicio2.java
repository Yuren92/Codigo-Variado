package ejercicio2;

public class Ejercicio2 implements Runnable {
	private String saludo;
	
	public Ejercicio2(String saludo){
		this.saludo = saludo;
	}
	
	public void run(){
		for(int i=0; i<10; i++){
			System.out.println(saludo);
		}
	}

}
