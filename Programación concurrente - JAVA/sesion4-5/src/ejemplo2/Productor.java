package ejemplo2;

class Productor extends Thread {
	private MonitorBuffer buffer;

	public Productor(MonitorBuffer b) {
		buffer=b;
	}

public void run(){
	for(int i=1 ; i<100 ; i++) {
		buffer.depositar(i);
		System.out.println("Insertado " + i);
	}
 }
} 

