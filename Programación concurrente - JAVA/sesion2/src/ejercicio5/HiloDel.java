package ejercicio5;

public class HiloDel extends Thread {
	public void run() {
		for(int i=0;i<10;i++){
			try{
				Programa.del.acquire();
			}
			catch (InterruptedException e){}
			System.out.println("del ");
			Programa.mundo.release();
		}
	} 
}
