package ejercicio4;

import java.util.concurrent.locks.ReentrantLock;

public class MemoriaGlobal {
	public static ReentrantLock l=new ReentrantLock(); 
	public static volatile int a=1000;
	
}
