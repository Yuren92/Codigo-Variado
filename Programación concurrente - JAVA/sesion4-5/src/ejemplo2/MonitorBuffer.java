package ejemplo2;

public class MonitorBuffer {
	private volatile int buffer[],in,out,tam;

	public MonitorBuffer(int ele){
		buffer=new int[ele];
		for( int i=0 ; i<buffer.length ; i++ )
			buffer[i]=0;
		
		in=out=tam=0;
	}

	synchronized public void depositar(int ele){
		while(tam>=buffer.length) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		buffer[in]=ele;
		in=(in+1)%buffer.length;
		tam++;
		notifyAll();
	}

	synchronized public int extraer(){
		int ele=0;

		while(tam<=0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		ele=buffer[out];
		buffer[out]=0;
		out=(out+1)%buffer.length;
		tam--;
		notifyAll();
		return ele;
	}

	synchronized public String toString(){
		String texto=new String();
		for(int i=0 ; i<buffer.length ; i++)
			texto = texto.concat(String.valueOf(buffer[i])+" ");
		return texto;
	}
} 
