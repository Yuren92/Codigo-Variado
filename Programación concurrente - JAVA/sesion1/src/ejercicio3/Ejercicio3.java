package ejercicio3;

public class Ejercicio3 extends Thread {
	private int a; //variable local
	public static volatile int valor;
	
	public void run(){
		a = valor;
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(int i=0;i<1000;i++){
			a++;
			valor = a;
		}
		
	}
}
