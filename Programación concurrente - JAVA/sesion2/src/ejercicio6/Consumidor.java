package ejercicio6;

public class Consumidor extends Thread {
	
	public void run(){
		for(int i=0;i<50;i++){
			try {
				sleep((long) Math.random());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				Semaforos.vacio.acquire();
				Semaforos.mutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(MemoriaGlobal.enteros[0]);
			Semaforos.mutex.release();
			Semaforos.lleno.release();
			
		}
	}

}
