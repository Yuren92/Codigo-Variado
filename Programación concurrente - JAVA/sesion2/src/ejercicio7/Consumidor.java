package ejercicio7;

public class Consumidor extends Thread {
	private int tipo;
	
	public Consumidor(int tipo){
		this.tipo = tipo;
	}
	
	public void run(){
		for(int i=0;i<100;i++){
			try {
				sleep((long) Math.random());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				if(tipo == 1)
					Semaforos.vacio1.acquire();
				else
					Semaforos.vacio2.acquire();
				
				Semaforos.mutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(MemoriaGlobal.enteros[0 % 10]);
			Semaforos.mutex.release();
			if(tipo == 1)
				Semaforos.lleno1.release();
			else
				Semaforos.lleno2.release();
			
		}
	}

}
