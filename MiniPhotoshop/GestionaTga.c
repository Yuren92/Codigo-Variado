/* Lee un tga de 24 bits, hace un proceso básico y escribe un tga out.tga      */
/* out.tga se puede visualizar con photoshop                                   */
/* Para generar un tga, con photoshop coger cualquier imagen, y guardarla como */
/* tga a 24 bits sin compresion, a menor resolucion de 1200x1200               */
/* 									PEDRO BALLESTA GARRES   				   */

#include <stdio.h>
#include <math.h>

#define SALIDA "out.tga"            // el archivo de salida
#define MAXRX 1200                  // el tamaño máximo del array en x
#define MAXRY 1200                  // y en y

unsigned char Img[MAXRX][MAXRY][3];  // aqui se guarda la imagen, tres bytes R, G, B
unsigned char Img2[MAXRX][MAXRY][3]; // Un array es muchisimo más rápido que un array de estructuras
unsigned char Img3[MAXRX][MAXRY][3]; // o una estructura de tres arrays
unsigned char Img4[MAXRX][MAXRY][3];
unsigned char Img5[MAXRX][MAXRY][3];
int Rx,Ry;                           // la Resolución de la imagen


void LeeTga(char na[],unsigned char Img[MAXRX][MAXRY][3])               // Lee un tga 24 de bits.
{
 FILE *fe;
 int x,y,i;
 unsigned char c,resto,cociente;
 
 fe=fopen(na,"rb");
 if (fe==NULL)
   {printf("Error al abrir archivo %s\nPulsa...",na); getch();exit(1);}

 for(i=0;i<12;i++)                              //leo 12 bytes de la cabecera que no se usan para nada
   c=fgetc(fe);
   
 resto=fgetc(fe);                               //La resolucion se codifica en dos bytes
 cociente=fgetc(fe);                            //el primero es el resto de dividir por 256
 Rx=cociente*256+resto;                         //y el segundo el cociente. Y lo recompongo

 resto=fgetc(fe);                               // igual para la Ry
 cociente=fgetc(fe);                            
 Ry=cociente*256+resto;

 c=fgetc(fe);
 c=fgetc(fe);                                   //los ultimos 2 bytes de la cabecera
 
 for(y=0;y<Ry;y++)                              //aqui leo la imagen
  for(x=0;x<Rx;x++)
  {
    Img[x][y][2]=fgetc(fe);                     //lee en orden B, G, R
    Img[x][y][1]=fgetc(fe);
    Img[x][y][0]=fgetc(fe);
  }

  fclose(fe);
}


void GuardaTga(char nombre[], unsigned char Img[MAXRX][MAXRY][3])  // Escribe un TGA guardado en memoria, a 24 bits
{
 FILE *fs;
 int x,y;
 
 fs=fopen(nombre,"wb");                        //abro para escritura binaria
 if (fs==NULL)
   {printf("Error al crear archivo %s\nPulsa...",SALIDA); getch();exit(1);}

 fputc(0,fs); /* 0 */                          // guardo valores de cebecera: la mayoria son ceros
 fputc(0,fs); /* 1 */
 fputc(2,fs); /* 2 */
 fputc(0,fs); /* 3 */
 fputc(0,fs); /* 4 */
 fputc(0,fs); /* 5 */
 fputc(0,fs); /* 6 */
 fputc(0,fs); /* 7 */
 fputc(0,fs); /* 8 */
 fputc(0,fs); /* 9 */
 fputc(0,fs); /* 10 */
 fputc(0,fs); /* 11 */
 fputc(Rx%256,fs); /* 12 */                   //La resolucion x en dos bytes
 fputc(Rx/256,fs); /* 13 */
 fputc(Ry%256,fs); /* 14 */                   //la resolucion y en dos bytes
 fputc(Ry/256,fs); /* 15 */
 fputc(24,fs); /* 16 */                       //indica 24 bits por pixel
 fputc(0,fs); /* 17 */                        // acabo la cabecera

 for(y=0;y<Ry;y++)                            // y aqui guardo la imagen por filas horizontales
  for(x=0;x<Rx;x++)
  {
    fputc((char)Img[x][y][2],fs);             //tres bytes por pixel, B, G, R
    fputc((char)Img[x][y][1],fs);
    fputc((char)Img[x][y][0],fs);
  }

  fclose(fs);
}

void bn(unsigned char Img[MAXRX][MAXRY][3],unsigned char Img2[MAXRX][MAXRY][3])
{
 int x,y;
 int r,g,b, newR, newG, newB,med;

 for (y=0;y<Ry;y++)
  for (x=0;x<Rx;x++)
   {
    r=Img[x][y][0];g=Img[x][y][1];b=Img[x][y][2];
    med=(r+g+b)/3;
  	Img2[x][y][0]=Img2[x][y][1]=Img2[x][y][2]=med;
   }
}

/*************SUMA*************/

void suma(int constante, unsigned char Img[MAXRX][MAXRY][3],unsigned char Img2[MAXRX][MAXRY][3]) {
 int x,y;
 int r,g,b, newR, newG, newB,med;

 for (y=0;y<Ry;y++)
  for (x=0;x<Rx;x++)
   {
  	Img2[x][y][0]=sumaOverflow(Img[x][y][0],constante);
	Img2[x][y][1]=sumaOverflow(Img[x][y][1],constante);
	Img2[x][y][2]=sumaOverflow(Img[x][y][2],constante);
   }
}

int sumaOverflow(int valor, int constante) {
	if (valor + constante >255) return 255;
	return valor + constante;
}


/*************RESTA*************/

void resta(int constante, unsigned char Img[MAXRX][MAXRY][3],unsigned char Img2[MAXRX][MAXRY][3]) {
 int x,y;
 int r,g,b, newR, newG, newB,med;

 for (y=0;y<Ry;y++)
  for (x=0;x<Rx;x++)
   {
  	Img2[x][y][0]=restaOverflow(Img[x][y][0],constante);
	Img2[x][y][1]=restaOverflow(Img[x][y][1],constante);
	Img2[x][y][2]=restaOverflow(Img[x][y][2],constante);
   }
}

int restaOverflow(int valor, int constante) {
	if (valor - constante < 0) return 0;
	return valor - constante;
}

/*************MULTIPLICACION*************/

void multiplica(int constante, unsigned char Img[MAXRX][MAXRY][3],unsigned char Img2[MAXRX][MAXRY][3]) {
 int x,y;
 int r,g,b, newR, newG, newB,med;

 for (y=0;y<Ry;y++)
  for (x=0;x<Rx;x++)
   {
  	Img2[x][y][0]=multiplicaOverflow(Img[x][y][0],constante);
	Img2[x][y][1]=multiplicaOverflow(Img[x][y][1],constante);
	Img2[x][y][2]=multiplicaOverflow(Img[x][y][2],constante);
   }
}

int multiplicaOverflow(int valor, int constante) {
	if (valor * constante > 255) return 255;
	return valor * constante;
}

/*************DIVISION/*************/

void divide(int constante, unsigned char Img[MAXRX][MAXRY][3],unsigned char Img2[MAXRX][MAXRY][3]) {
 int x,y;
 int r,g,b, newR, newG, newB,med;

 for (y=0;y<Ry;y++)
  for (x=0;x<Rx;x++)
   {
  	Img2[x][y][0]=divideOverflow(Img[x][y][0],constante);
	Img2[x][y][1]=divideOverflow(Img[x][y][1],constante);
	Img2[x][y][2]=divideOverflow(Img[x][y][2],constante);
   }
}

int divideOverflow(int valor, int constante) {
	return valor / constante;
}

/*************NEGATIVO*************/

void negativo(unsigned char Img[MAXRX][MAXRY][3],unsigned char Img2[MAXRX][MAXRY][3]) {
 int x,y;
 int r,g,b, newR, newG, newB,med;

 for (y=0;y<Ry;y++)
  for (x=0;x<Rx;x++)
   {
  	Img2[x][y][0]=255-Img[x][y][0];
	Img2[x][y][1]=255-Img[x][y][1];
	Img2[x][y][2]=255-Img[x][y][2];
   }
}

/*************AJUSTE LINEAL REAL*************/

void linealReal(unsigned char Img[MAXRX][MAXRY][3],unsigned char Img2[MAXRX][MAXRY][3]){
 int x,y;
 int r,g,b, newR, newG, newB,med;
 
 int min[3],max[3];
 min[0]=buscarMin(Img,0);
 min[1]=buscarMin(Img,1);
 min[2]=buscarMin(Img,2);
 max[0]=buscarMax(Img,0);
 max[1]=buscarMax(Img,1);
 max[2]=buscarMax(Img,2);

 for (y=0;y<Ry;y++)
  for (x=0;x<Rx;x++)
   {
  	Img2[x][y][0]=((Img[x][y][0]-min[0])*255)/(max[0]-min[0]);
	Img2[x][y][1]=((Img[x][y][1]-min[1])*255)/(max[1]-min[1]);
	Img2[x][y][2]=((Img[x][y][2]-min[2])*255)/(max[2]-min[2]);
   }
}

int buscarMin(unsigned char Img[MAXRX][MAXRY][3], int num) {
 int x,y;
 int r,g,b, newR, newG, newB,med;
 int min=256;

 for (y=0;y<Ry;y++)
  for (x=0;x<Rx;x++)
   {
   	if (Img[x][y][num]<min) min=Img[x][y][num];
   }
 return min;
}

int buscarMax(unsigned char Img[MAXRX][MAXRY][3], int num) {
 int x,y;
 int r,g,b, newR, newG, newB,med;
 int max=-1;

 for (y=0;y<Ry;y++)
  for (x=0;x<Rx;x++)
   {
   	if (Img[x][y][num]>max) max=Img[x][y][num];
   }
 return max;
}

/*************UMBRALIZACION*************/

void umbraliza(int constante, unsigned char Img[MAXRX][MAXRY][3],unsigned char Img2[MAXRX][MAXRY][3]) {
 int x,y;
 int r,g,b, newR, newG, newB,med, valor;

 for (y=0;y<Ry;y++)
  for (x=0;x<Rx;x++)
   {
  	r=Img[x][y][0];g=Img[x][y][1];b=Img[x][y][2];
    med=(r+g+b)/3;
    if (med<constante) valor=0;
    else valor=255;
  	Img2[x][y][0]=Img2[x][y][1]=Img2[x][y][2]=valor;
   }
}

/*************VISITAR SITIO*************/
void visitar(int constante, unsigned char Img[MAXRX][MAXRY][3],unsigned char Img2[MAXRX][MAXRY][3]) {
 umbraliza(constante,Img2,Img3);
 int x,y;
 int r,g,b, newR, newG, newB,med, valor;

 for (y=0;y<Ry;y++)
  for (x=0;x<Rx;x++)
   {
  	Img2[x][y][0]=Img2[x][y][0]&~Img3[x][y][0];
	Img2[x][y][1]=Img2[x][y][1]&~Img3[x][y][1];
	Img2[x][y][2]=Img2[x][y][2]&~Img3[x][y][2];
   }
   
 for (y=0;y<Ry;y++)
  for (x=0;x<Rx;x++)
   {
  	Img[x][y][0]=Img[x][y][0]&Img3[x][y][0];
	Img[x][y][1]=Img[x][y][1]&Img3[x][y][1];
	Img[x][y][2]=Img[x][y][2]&Img3[x][y][2];
   }
   
 for (y=0;y<Ry;y++)
  for (x=0;x<Rx;x++)
   {
  	Img2[x][y][0]=Img[x][y][0]|Img2[x][y][0];
	Img2[x][y][1]=Img[x][y][1]|Img2[x][y][1];
	Img2[x][y][2]=Img[x][y][2]|Img2[x][y][2];
   }
}

/*************MEDIA PONDERADA COMBINAR IMAGENES*************/
combinarPonderada(float constante, unsigned char Img[MAXRX][MAXRY][3],unsigned char Img2[MAXRX][MAXRY][3]) {
 int x,y;
 int r,g,b, newR, newG, newB,med, valor;

 for (y=0;y<Ry;y++)
  for (x=0;x<Rx;x++)
   {
  	Img2[x][y][0]=sumaOverflow(constante*Img[x][y][0],(1-constante)*Img2[x][y][0]);
	Img2[x][y][1]=sumaOverflow(constante*Img[x][y][1],(1-constante)*Img2[x][y][1]);
	Img2[x][y][2]=sumaOverflow(constante*Img[x][y][2],(1-constante)*Img2[x][y][2]);
   }
  
}

/*************DIFERENCIAR IMAGENES*************/
diferenciar(unsigned char Img[MAXRX][MAXRY][3],unsigned char Img2[MAXRX][MAXRY][3]) {
 int x,y;
 int r,g,b, newR, newG, newB,med, valor;

 for (y=0;y<Ry;y++)
  for (x=0;x<Rx;x++)
   {
  	Img2[x][y][0]=restaOverflow(Img[x][y][0], Img2[x][y][0]);
	Img2[x][y][1]=restaOverflow(Img[x][y][1], Img2[x][y][1]);
	Img2[x][y][2]=restaOverflow(Img[x][y][2], Img2[x][y][2]);
   }
   bn(Img2,Img2);
}

/*************INCLINAR IMAGEN*************/
inclinar(int valor, unsigned char Img[MAXRX][MAXRY][3],unsigned char Img2[MAXRX][MAXRY][3]) {
 int x,y;
 int r,g,b, newR, newG, newB,med;

 for (y=0;y<Ry;y++)
  for (x=0;x<Rx+valor*y;x++)
   {
	   	if ((x-(valor*y)<0)) {
	   		Img2[x][y][0]=0;
			Img2[x][y][1]=0;
			Img2[x][y][2]=0;
	   	} else {
		  	Img2[x][y][0]=Img[x-(valor*y)][y][0];
			Img2[x][y][1]=Img[x-(valor*y)][y][1];
			Img2[x][y][2]=Img[x-(valor*y)][y][2];
		}
   }
   Rx+=valor*Ry;
}

/*************FILTRO MAXIMO*************/
filtromax(int valor, unsigned char Img[MAXRX][MAXRY][3],unsigned char Img2[MAXRX][MAXRY][3]) {
 int x,y;
 int r,g,b, newR, newG, newB,med;

 for (y=0;y<Ry;y++)
  for (x=0;x<Rx;x++)
   {
   		int iniciox=x-valor;
   		int inicioy=y-valor;
   		while(iniciox<0) iniciox++;
   		while(inicioy<0) inicioy++;
   		int finalx=x+valor;
   		int finaly=y+valor;
   		while(finalx>Rx) finalx--;
   		while(finaly>Ry) finaly--;
   		
   		int max=0;
   		int max0=0;
   		int max1=0;
   		int max2=0;
   		
   		for ( ; inicioy<finaly; inicioy++) {
   			for ( ; iniciox<finalx; iniciox++)
   				if (Img[iniciox][inicioy][0]+Img[iniciox][inicioy][1]+Img[iniciox][inicioy][2]>max) {
   					max=Img[iniciox][inicioy][0]+Img[iniciox][inicioy][1]+Img[iniciox][inicioy][2];
   					max0=Img[iniciox][inicioy][0];
   					max1=Img[iniciox][inicioy][1];
   					max2=Img[iniciox][inicioy][2];
   				}
   		}
   		

	   		Img2[x][y][0]=max0;
			Img2[x][y][1]=max1;
			Img2[x][y][2]=max2;

   }
}

main()
{
 char na[100];
 int opcion=-1;
 int constante=0;
 float valor=0;
 while (opcion!=0)
 {
 	 printf("*********PEDRO BALLESTA GARRES*************:\n");
 	 printf("PROYECTO INFORMATICA GRAFICA:\n");
	 printf("MENU:\n");
	 printf("1: Suma\n");
	 printf("2: Resta\n");
	 printf("3: Multiplicacion\n");
	 printf("4: Division\n");
	 printf("5: Negativo\n");
	 printf("6: Pasar a blanco y negro\n");
	 printf("7: Ajuste Lineal Real\n");
	 printf("8: Umbralizacion\n");
	 printf("9: visitar sitio\n");
	 printf("10: Combinar imagenes con media ponderada\n");
	 printf("11: Diferenciar dos imagenes\n");
	 printf("12: Inclinar imagen en el eje x\n");
	 printf("13: Filtro maximo\n");
	 printf("0: SALIR\n");
	 printf("Elige la operacion a realizar : ");
	 scanf("%d",&opcion);
	 
	
	 if (opcion==6) {
	 	printf("Pasar imagen a blanco y negro\n");
		printf("Nombre de archivo : ");
		scanf("%s",na);
		LeeTga(na,Img);
		printf("Resolucion de %d x %d\n",Rx,Ry);
		bn(Img,Img);
		printf("Nombre de archivo a guardar : ");
		scanf("%s",na);
		GuardaTga(na, Img);
	
	 } else if (opcion==1) {
	 	printf("Sumar constante a imagen\n");
		printf("Nombre de archivo : ");
		scanf("%s",na);
		LeeTga(na,Img);
		printf("Resolucion de %d x %d\n",Rx,Ry);
		printf("Elige constante a sumar : ");
		scanf("%d",&constante);
		suma(constante,Img,Img);
		printf("Nombre de archivo a guardar : ");
		scanf("%s",na);
		GuardaTga(na, Img);
	 
	 } else if (opcion==2) {
	 	printf("Restar constante a imagen\n");
		printf("Nombre de archivo : ");
		scanf("%s",na);
		LeeTga(na,Img);
		printf("Resolucion de %d x %d\n",Rx,Ry);
		printf("Elige constante a restar : ");
		scanf("%d",&constante);
		resta(constante,Img,Img);
		printf("Nombre de archivo a guardar : ");
		scanf("%s",na);
		GuardaTga(na, Img);
	 
	 } else if (opcion==3) {
	 	printf("Multiplicar una constante a una imagen\n");
		printf("Nombre de archivo : ");
		scanf("%s",na);
		LeeTga(na,Img);
		printf("Resolucion de %d x %d\n",Rx,Ry);
		printf("Elige constante a multiplicar : ");
		scanf("%d",&constante);
		multiplica(constante,Img,Img);
		printf("Nombre de archivo a guardar : ");
		scanf("%s",na);
		GuardaTga(na, Img);
	 
	 } else if (opcion==4) {
	 	printf("Dividir una constante a una imagen\n");
		printf("Nombre de archivo : ");
		scanf("%s",na);
		LeeTga(na,Img);
		printf("Resolucion de %d x %d\n",Rx,Ry);
		printf("Elige constante a dividir : ");
		scanf("%d",&constante);
		divide(constante,Img,Img);
		printf("Nombre de archivo a guardar : ");
		scanf("%s",na);
		GuardaTga(na, Img);
		
	 } else if (opcion==5) {
	 	printf("Poner a negativo una imagen\n");
		printf("Nombre de archivo : ");
		scanf("%s",na);
		LeeTga(na,Img);
		printf("Resolucion de %d x %d\n",Rx,Ry);
		negativo(Img,Img);
		printf("Nombre de archivo a guardar : ");
		scanf("%s",na);
		GuardaTga(na, Img);
		
	 } else if (opcion==7) {
	 	printf("Hacer un ajuste lineal de una imagen\n");
		printf("Nombre de archivo : ");
		scanf("%s",na);
		LeeTga(na,Img);
		printf("Resolucion de %d x %d\n",Rx,Ry);
		linealReal(Img,Img);
		printf("Nombre de archivo a guardar : ");
		scanf("%s",na);
		GuardaTga(na, Img);
		
	 } else if (opcion==8) {
	 	printf("Umbralizar una imagen\n");
		printf("Nombre de archivo : ");
		scanf("%s",na);
		LeeTga(na,Img);
		printf("Resolucion de %d x %d\n",Rx,Ry);
		printf("Elige valor del umbral : ");
		scanf("%d",&constante);
		umbraliza(constante,Img,Img);
		printf("Nombre de archivo a guardar : ");
		scanf("%s",na);
		GuardaTga(na, Img);
		
	 } else if (opcion==9) {
	 	printf("Poner una foto de alguien visitando un lugar\n");
		printf("Nombre de imagen con el lugar : ");
		scanf("%s",na);
		LeeTga(na,Img);
		printf("Nombre de imagen de la persona : ");
		scanf("%s",na);
		LeeTga(na,Img2);
		printf("Resolucion de %d x %d\n",Rx,Ry);
		printf("Elige valor del umbral : ");
		scanf("%d",&constante);
		visitar(constante,Img,Img2);
		printf("Nombre de archivo a guardar : ");
		scanf("%s",na);
		GuardaTga(na, Img2);
		
	 } else if (opcion==10) {
	 	printf("Combinar dos imagenes\n");
		printf("Nombre de la primera imagen : ");
		scanf("%s",na);
		LeeTga(na,Img);
		printf("Nombre de la segunda imagen : ");
		scanf("%s",na);
		LeeTga(na,Img2);
		printf("Resolucion de %d x %d\n",Rx,Ry);
		printf("Elige valor para ponderar : ");
		scanf("%f",&valor);
		combinarPonderada(valor,Img,Img2);
		printf("Nombre de archivo a guardar : ");
		scanf("%s",na);
		GuardaTga(na, Img2);
		
	 } else if (opcion==11) {
	 	printf("Diferenciar dos imagenes\n");
		printf("Nombre de la primera imagen : ");
		scanf("%s",na);
		LeeTga(na,Img);
		printf("Nombre de la segunda imagen : ");
		scanf("%s",na);
		LeeTga(na,Img2);
		printf("Resolucion de %d x %d\n",Rx,Ry);
		diferenciar(Img,Img2);
		printf("Nombre de archivo a guardar : ");
		scanf("%s",na);
		GuardaTga(na, Img2);
		
	 }  else if (opcion==12) {
	 	printf("Inclinar una imagen\n");
		printf("Nombre de la imagen : ");
		scanf("%s",na);
		LeeTga(na,Img);
		printf("Resolucion de %d x %d\n",Rx,Ry);
		printf("Elige valor de la inclinacion : ");
		scanf("%d",&constante); 
		inclinar(constante,Img,Img2);
		printf("Nombre de archivo a guardar : ");
		scanf("%s",na);
		GuardaTga(na, Img2);
		
	 }  else if (opcion==13) {
	 	printf("Hacer el filtro maximo\n");
		printf("Nombre de la imagen : ");
		scanf("%s",na);
		LeeTga(na,Img);
		printf("Resolucion de %d x %d\n",Rx,Ry);
		printf("Elige num de columnas vecinas: ");
		scanf("%d",&constante);
		filtromax(constante,Img,Img2);
		printf("Nombre de archivo a guardar : ");
		scanf("%s",na);
		GuardaTga(na, Img2);
	 }
}
 
 printf("Terminado. Pulsa...\n"); 
 getchar();
}
