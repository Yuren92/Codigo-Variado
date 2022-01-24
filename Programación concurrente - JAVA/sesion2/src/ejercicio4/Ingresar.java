package ejercicio4;

public class Ingresar extends Thread {

	public void run(){
		int a;
		for (int i=0;i<100;i++){
			try {
				MemoriaGlobal.l.lock();
				a = MemoriaGlobal.a;
				try {
					sleep((long) Math.random());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
				a += 10;
			
				MemoriaGlobal.a = a;
			} finally {
				MemoriaGlobal.l.unlock();
			}
			
		}
	}
}
