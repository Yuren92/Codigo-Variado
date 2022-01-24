package ejercicio1;

import messagepassing.CommunicationScheme;

public class HiloMundo extends Thread {
	private CommunicationScheme cs;
	private CommunicationScheme cd;
	private int id;
	
	public HiloMundo(CommunicationScheme cs, CommunicationScheme cd, int id) {
		this.cs = cs;
		this.id = id;
		this.cd = cd;
	}
	
	public void run(){
		for(int i=0;i<10;i++){
			cs.receive();
			System.out.println("Mundo.\n");
			cd.send(null);
		}
			
	}

}
