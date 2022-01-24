package ejemplo3;

class Productor extends Thread{
	private BufferLimitado buffer;

	public Productor(BufferLimitado b){
		buffer=b;
	}

	public void run(){
		for(int i=1;i<100;i++){
			try {
				buffer.depositar((Object)new Integer(i));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(buffer);
		}
	}
} 
