package ejercicio1;

public class PrimerIntento {
	
	public static volatile int v=0;
	/**
	 * @param args
	 */

	public static void main(String[] args) {
		 Thread a=new Hilo();
		 Thread b=new Hilo();
		 a.start();
		 b.start();
		 try {
			 a.join();
			 b.join();
		 } catch (InterruptedException e) {
			 e.printStackTrace();
		 }
		 System.out.println("Finalizando hilo principal");
	}
}

