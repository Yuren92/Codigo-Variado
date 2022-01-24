package ejercicio2;

public class VariableCompartida {
	private volatile int var;
	
	public VariableCompartida(int val){
		 var=val;
	}
	
	public int getVar(){
		return var;
	}
	
	public synchronized void incrementa(){
		int temp;
		temp=var;
		try {
			Thread.sleep((int)Math.round(Math.random()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			temp++;
			var=temp;
		}
}
