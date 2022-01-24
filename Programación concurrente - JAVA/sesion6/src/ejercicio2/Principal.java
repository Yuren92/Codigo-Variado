package ejercicio2;

import messagepassing.MailBox;
import messagepassing.Selector;

public class Principal {
	
	public static void main(String[] args) {
		
		MailBox cuadradoX = new MailBox();
		MailBox cuadradoY = new MailBox(); 
		MailBox trianguloX = new MailBox();
		MailBox trianguloY = new MailBox(); 
		MailBox rectanguloX = new MailBox();
		MailBox rectanguloY = new MailBox();
		
		MailBox ecuadradoX1 = new MailBox();
		MailBox ecuadradoY1 = new MailBox(); 
		MailBox etrianguloX1 = new MailBox();
		MailBox etrianguloY1 = new MailBox(); 
		MailBox erectanguloX1 = new MailBox();
		MailBox erectanguloY1 = new MailBox();
		
		MailBox ecuadradoX2 = new MailBox();
		MailBox ecuadradoY2 = new MailBox(); 
		MailBox etrianguloX2 = new MailBox();
		MailBox etrianguloY2 = new MailBox(); 
		MailBox erectanguloX2 = new MailBox();
		MailBox erectanguloY2 = new MailBox();
		
		MailBox ecuadradoX3 = new MailBox();
		MailBox ecuadradoY3 = new MailBox(); 
		MailBox etrianguloX3 = new MailBox();
		MailBox etrianguloY3 = new MailBox(); 
		MailBox erectanguloX3 = new MailBox();
		MailBox erectanguloY3 = new MailBox();
		
		Selector s = new Selector();
		
		HiloXCuadrado xc1 = new HiloXCuadrado(cuadradoX, ecuadradoX1, 1);
		HiloXCuadrado xc2 = new HiloXCuadrado(cuadradoX, ecuadradoX2, 2);
		HiloXCuadrado xc3 = new HiloXCuadrado(cuadradoX, ecuadradoX3, 3);
		
		HiloXCuadrado yc1 = new HiloXCuadrado(cuadradoY, ecuadradoY1, 1);
		HiloXCuadrado yc2 = new HiloXCuadrado(cuadradoY, ecuadradoY2, 2);
		HiloXCuadrado yc3 = new HiloXCuadrado(cuadradoY, ecuadradoY3, 3);
		
		HiloXCuadrado xt1 = new HiloXCuadrado(trianguloX, etrianguloX1, 1);
		HiloXCuadrado xt2 = new HiloXCuadrado(trianguloX, etrianguloX2, 2);
		HiloXCuadrado xt3 = new HiloXCuadrado(trianguloX, etrianguloX3, 3);
		
		HiloXCuadrado yt1 = new HiloXCuadrado(trianguloY, etrianguloY1, 1);
		HiloXCuadrado yt2 = new HiloXCuadrado(trianguloY, etrianguloY2, 2);
		HiloXCuadrado yt3 = new HiloXCuadrado(trianguloY, etrianguloY3, 3);
		
		HiloXCuadrado xr1 = new HiloXCuadrado(rectanguloX, erectanguloX1, 1);
		HiloXCuadrado xr2 = new HiloXCuadrado(rectanguloX, erectanguloX2, 2);
		HiloXCuadrado xr3 = new HiloXCuadrado(rectanguloX, erectanguloX3, 3);
		
		HiloXCuadrado yr1 = new HiloXCuadrado(rectanguloY, erectanguloY1, 1);
		HiloXCuadrado yr2 = new HiloXCuadrado(rectanguloY, erectanguloY2, 2);
		HiloXCuadrado yr3 = new HiloXCuadrado(rectanguloY, erectanguloY3, 3);
		
		
	}

}


