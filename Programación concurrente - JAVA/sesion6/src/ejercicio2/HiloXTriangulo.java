package ejercicio2;

import messagepassing.MailBox;

public class HiloXTriangulo extends Thread {
	private MailBox buzon;
	private MailBox buzon2;
	int a;
		
	public HiloXTriangulo(MailBox buzon, MailBox buzon2, int a) {
		this.buzon = buzon;
		this.buzon2 = buzon2;
		this.a = a;
	}
		
	public void run(){
		while(true){
			buzon.send(a);
			buzon2.receive();
		}
	}
}
