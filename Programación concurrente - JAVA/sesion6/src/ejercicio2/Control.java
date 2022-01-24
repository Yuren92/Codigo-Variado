package ejercicio2;

import messagepassing.MailBox;

public class Control extends Thread {
	MailBox cuadradoX;
	MailBox cuadradoY; 
	MailBox trianguloX;
	MailBox trianguloY; 
	MailBox rectanguloX;
	MailBox rectanguloY;
	
	MailBox ecuadradoX;
	MailBox ecuadradoY; 
	MailBox etrianguloX;
	MailBox etrianguloY; 
	MailBox erectanguloX;
	MailBox erectanguloY;
	
	int cuadrado;
	int triangulo;
	int rectangulo;
	
	public Control(){
		cuadrado = (int) (Math.random()%2);
		triangulo = (int) (Math.random()%2);
		rectangulo = (int) (Math.random()%2);
	}

}
