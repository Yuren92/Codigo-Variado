package ejercicio5;

public class HiloDel extends Thread {
	public void run(){
		for(int i=0;i<10;i++){
			MemoriaCompartida.monitor.del();
		}
			
	}
}
