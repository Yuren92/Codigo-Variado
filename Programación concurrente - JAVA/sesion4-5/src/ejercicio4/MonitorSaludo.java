package ejercicio4;

public class MonitorSaludo {
	private volatile int num;
	private int turno;
	
	public MonitorSaludo(int _num){
		num = _num;
		
		for (int i = 0; i < _num; i++) {
			
		}
	}
	
	synchronized public String Hola(){
		String hola = "Hola ";
		while(turno!=0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		turno = 1;
		return hola;
	}
	
	synchronized public String Amigos(){
		String amigos = "amigos ";
		while(turno!=1){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		turno = 2;
		return amigos;
	}
	
	synchronized public String Del(){
		String del = "del ";
		while(turno!=2){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		turno = 3;
		return del;
	}
	
	synchronized public String Mundo(){
		String mundo = "mundo ";
		while(turno!=3){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		turno = 0;
		return mundo;
	}
	
}
