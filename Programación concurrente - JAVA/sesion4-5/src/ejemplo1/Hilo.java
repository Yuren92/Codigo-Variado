package ejemplo1;

class Hilo extends Thread{
	private Monitor1 monitor;
	private int tipo;
	private int cantidad;
	
	public Hilo(Monitor1 _monitor,int _tipo,int _cantidad){
		monitor=_monitor;
		tipo=_tipo;
		cantidad=_cantidad;
	}
	
	public void run(){
		for(int i=0 ; i<100 ; i++){
			if (tipo==2)
				monitor.incrementar(cantidad);
			else
				monitor.decrementar(cantidad);
		}
	}
} 
