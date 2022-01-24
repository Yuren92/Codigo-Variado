package ejercicio1;

class Hilo extends Thread{
	public void run(){
		for(int i=0;i<10;i++){
			while (PrimerIntento.v==1);
			try {
				Thread.sleep(Math.round((Math.random()*1000)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			PrimerIntento.v=1;
			System.out.println("Entrando a la seccion critica "
			+ Thread.currentThread().getName());
			try {
				Thread.sleep(Math.round((Math.random()*1000)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Saliendo de la seccion critica "
			+ Thread.currentThread().getName());
			PrimerIntento.v=0;
		}
	}
} 
