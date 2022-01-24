package ejercicio5;

public class HiloHola extends Thread {
	public void run() {
		for(int i=0;i<10;i++){
			try{
				Programa.hola.acquire();
			}
			catch (InterruptedException e){}
			System.out.println("hola ");
			Programa.amigos.release();
		}
	} 
}
