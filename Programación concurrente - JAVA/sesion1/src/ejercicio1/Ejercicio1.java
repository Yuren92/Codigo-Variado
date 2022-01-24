package ejercicio1;

public class Ejercicio1 extends Thread {
	private String saludo;
	
	public Ejercicio1(String saludo){
		this.saludo = saludo;
	}
	
	public void run(){
		
		for(int i=0;i<10;i++){
			System.out.println(saludo);
			try {
				Thread.sleep((int)(Math.random()*10));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} 

	}

}
