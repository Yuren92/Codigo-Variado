package ejemplo2;

class Consumidor extends Thread{
	private MonitorBuffer buffer;

	public Consumidor(MonitorBuffer b){
		buffer=b;
	}

	public void run(){
		for(int i=1 ; i<100 ; i++) {
			int e = buffer.extraer();
			System.out.println("Extraido " + e);
		}
	}
}