package ejercicio6;

public class Productor extends Thread {
	
	public void run(){
		int aleatorio;
		for(int i=0;i<50;i++){
			try {
				sleep((long) Math.random());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			aleatorio = (int)(Math.random() * 100) % 5;
			
			try {
				Semaforos.lleno.acquire();
				Semaforos.mutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			MemoriaGlobal.enteros[i] = aleatorio;
			Semaforos.mutex.release();
			Semaforos.vacio.release();
		}
		
	}

}
