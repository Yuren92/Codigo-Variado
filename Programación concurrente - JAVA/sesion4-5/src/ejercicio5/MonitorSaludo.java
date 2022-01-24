package ejercicio5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorSaludo {
	private ReentrantLock l=new ReentrantLock();
	private Condition h=l.newCondition();	//hola
	private Condition a=l.newCondition();	//amigos
	private Condition d=l.newCondition();	//del
	private Condition m=l.newCondition();	//mundo
	int turno=0;
	int n=0;
	
	public void hola(){
		n+=1;
		l.lock();
		try {
			while(turno != 0) 
				h.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(n + ".Hola ");
		turno=1;
		a.signal();
		l.unlock();
	}
	
	public void amigos(){
		l.lock();
		try {
			while(turno != 1)
				a.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("amigos ");
		turno=2;
		d.signal();
		l.unlock();
	}
	
	public void del(){
		l.lock();
		try {
			while(turno != 2)
				d.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("del ");
		turno=3;
		m.signal();
		l.unlock();
	}
	
	public void mundo(){
		l.lock();
		try {
			while(turno != 3)
				m.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("mundo ");
		turno=0;
		h.signal();
		l.unlock();
	}
}
