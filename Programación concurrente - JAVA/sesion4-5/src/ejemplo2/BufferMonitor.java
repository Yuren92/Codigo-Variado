package ejemplo2;

public class BufferMonitor {

	public static void main(String[] args) {
		MonitorBuffer buffer=new MonitorBuffer(10);

		Productor p1=new Productor(buffer);
		Consumidor c1=new Consumidor(buffer);

		p1.start();
		c1.start();
	}
} 

