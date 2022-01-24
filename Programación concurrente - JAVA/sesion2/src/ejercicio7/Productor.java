package ejercicio7;

public class Productor extends Thread {
	private int tipo;
	
	public Productor(int tipo){
		this.tipo = tipo;
	}
	
	public void run(){
		int aleatorio;
		for(int i=0;i<100;i++){
			try {
				sleep((long) Math.random());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			aleatorio = (int)(Math.random() * 100) % 5;
			
			try {
				if(tipo == 1)
					Semaforos.lleno1.acquire();
				else
					Semaforos.lleno2.acquire();
				
				Semaforos.mutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			MemoriaGlobal.enteros[i % 10] = aleatorio;
			Semaforos.mutex.release();
			if(tipo == 1)
				Semaforos.vacio1.release();
			else
				Semaforos.vacio2.release();
		}
		
	}

}
