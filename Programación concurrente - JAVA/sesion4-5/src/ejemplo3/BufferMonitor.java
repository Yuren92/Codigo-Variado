package ejemplo3;

public class BufferMonitor {

	public static void main(String[] args) {
		BufferLimitado buffer=new BufferLimitado(10);

		Productor p1=new Productor(buffer);
		Consumidor c1=new Consumidor(buffer);

		p1.start();
		c1.start();
	}
}
