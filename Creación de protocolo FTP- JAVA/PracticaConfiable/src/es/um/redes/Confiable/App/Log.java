package es.um.redes.Confiable.App;

import java.io.*;
import java.util.LinkedList;

import es.um.redes.intercambio.impl.Segment;

public class Log {
 	private PrintWriter fichero;
 	private long marcaTiempo;
	
	
	public Log(String nombre){
		marcaTiempo = System.currentTimeMillis();
		try {
			 fichero= new PrintWriter(nombre+".txt", "ISO-8859-1");
			 } catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void add(String cadena, Segment s){
		fichero.println(cadena);
		fichero.println("(t = "+" "+marcaTiempo()+" "+" )");
		fichero.println("Información del segmento"+" "+ s);
		
	}
	public long marcaTiempo(){
		return System.currentTimeMillis() - marcaTiempo;
	}
	
	public void cerrarFichero(){
		fichero.close();
	}

}
