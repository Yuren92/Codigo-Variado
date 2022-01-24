package ejemplo3;

class Consumidor extends Thread{
	private BufferLimitado buffer;

	public Consumidor(BufferLimitado b){
		buffer=b;
	}

	public void run(){
		for(int i=1;i<100;i++){
			try {
				buffer.extraer();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(buffer);
		}
	}
} 
