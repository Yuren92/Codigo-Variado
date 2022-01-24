package ejercicio7;

import java.util.concurrent.Semaphore;

public class Semaforos {
	public static Semaphore mutex = new Semaphore(1);
	public static Semaphore lleno1 = new Semaphore(0);
	public static Semaphore vacio1 = new Semaphore(1);
	public static Semaphore lleno2 = new Semaphore(0);
	public static Semaphore vacio2 = new Semaphore(1);

}
