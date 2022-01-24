package ejercicio6;

import java.util.concurrent.Semaphore;

public class Semaforos {
	public static Semaphore mutex = new Semaphore(1);
	public static Semaphore lleno = new Semaphore(0);
	public static Semaphore vacio = new Semaphore(1);

}
