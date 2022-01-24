package ejemplo3;

import java.util.concurrent.locks.*;

	public class BufferLimitado {
		private ReentrantLock l=new ReentrantLock();
		private Condition nolleno=l.newCondition();
		private Condition novacio=l.newCondition();
		private Object buffer[];
		private int in,out,tam;

	public BufferLimitado(int ele){
		buffer=new Object[ele];
		for(int i=0;i<buffer.length;i++)
			buffer[i]=null;
		
		in=out=tam=0;
	}

	public void depositar(Object ele) throws InterruptedException{
		l.lock();
		try{
			while(tam>=buffer.length)
				nolleno.await();
			
			buffer[in]=ele;
			in=(in+1)%buffer.length;
			tam++;
			novacio.signal();
		}finally{
			l.unlock();
		}
	}

	public Object extraer() throws InterruptedException{
		l.lock();
		try{
			while(tam<=0)
				novacio.await();
 
			Object ele=buffer[out];
			buffer[out]=null;
			out=(out+1)%buffer.length;
			tam--;
			nolleno.signal();
			return ele;
		}finally{
			l.unlock();
		}
	}

	public String toString(){
		l.lock();
		try{
			String texto=new String();
			for(int i=0;i<buffer.length;i++)
				texto=texto.concat(String.valueOf(buffer[i])+" ");
 
			return texto;
		}finally{
			l.unlock();
		}
	}
} 
