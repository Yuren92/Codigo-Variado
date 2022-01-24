package ejercicio5;

public class HiloAmigos extends Thread {
	public void run() {
		for(int i=0;i<10;i++){
			try{
				Programa.amigos.acquire();
			}
			catch (InterruptedException e){}
			System.out.println("amigos ");
			Programa.del.release();
		}
	} 

}
