package ejercicio1;

import messagepassing.CommunicationScheme;

public class HiloAmigos extends Thread {
	
	private CommunicationScheme cs;
	private CommunicationScheme cd;
	private int id;
	
	public HiloAmigos(CommunicationScheme cs, CommunicationScheme cd, int id) {
		this.cs = cs;
		this.id = id;
		this.cd = cd;
	}
	
	public void run(){
		for(int i=0;i<10;i++){
			cs.receive();
			System.out.println("Amigos ");
			cd.send(null);
		}
			
	}

}
