package ejemplo1;

public class Ejemplo {

	public static void main(String[] args){
		Monitor1 monitor=new Monitor1(0);
		Thread a=new Hilo(monitor,1,10); // tipo 1 decrementa
		Thread b=new Hilo(monitor,2,10); // tipo 2 incrementa
		a.start();
		b.start();
	}
}

