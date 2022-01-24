package ejemplo1;

class Monitor1 {
	private volatile int compar;
	
	public Monitor1(int val){
		compar=val;
	}
	
	synchronized public void decrementar(int cantidad){
		while(cantidad>compar){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		compar-=cantidad;
		System.out.println("variable="+compar);
	}
	
	synchronized public void incrementar(int cantidad){
		compar+=cantidad;
		notifyAll();
		System.out.println("variable="+compar);
	}
} 