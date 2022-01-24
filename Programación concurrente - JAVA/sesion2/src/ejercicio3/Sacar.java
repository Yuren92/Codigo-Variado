package ejercicio3;

public class Sacar extends Thread {
	
	public void run(){
		int a;
		for (int i=0;i<100;i++){
			synchronized (this) {
				a = MemoriaGlobal.a;
				try {
					sleep((long) Math.random());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
				a -= 10;
			
				MemoriaGlobal.a = a;
			}
		}
	}
}
