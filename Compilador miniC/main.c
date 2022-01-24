#include "codigo.h"

extern FILE *yyin;
extern int yyparse(void);
extern int yydebug;

codigo codigoFinal;

void volcarMIPS(){
	FILE *archivo = fopen("codigoEnsamblador","w");
	if(archivo != NULL){
		fprintf(archivo,".data\n\n");
		imprimirString(archivo);
		fprintf(archivo,"\n");
		imprimirID(archivo);
		fprintf(archivo,"\n.text\n\n");
		imprimir(archivo, &codigoFinal);
		fclose(archivo);
	} else
		printf("Error archivo de salida no creado.\n");
}

int main (int argc, char *argv[]) {
	
	if (argc != 2){
		printf("Uso: %s fichero\n",argv[0]);
		exit(1);	
	}

	FILE *fichero = fopen(argv[1],"r");
	
	if (fichero == NULL){
		printf("Error al abrir fichero %s\n", argv[1]);
		exit(2);
	}

	yyin = fichero;
	yydebug = 0;

	inicializacionTablaSimbolos();	
	inicializarString();

	int resultado = yyparse();

	volcarMIPS();

	borrarTablaSimbolos();
	borrarString();

// PARA LIBERAR LA MEMORIA
	linea* auxiliar = codigoFinal.primero;
	
	while(auxiliar != NULL){
		codigoFinal.primero = auxiliar->sig;
		if(auxiliar->operacion != NULL)
			free(auxiliar->operacion);
		
		if(auxiliar->argumento1 != NULL)
			free(auxiliar->argumento1);
		
		if(auxiliar->argumento2 != NULL)
			free(auxiliar->argumento2);
		
		if(auxiliar->argumento3 != NULL)
			free(auxiliar->argumento3);
		
		free(auxiliar);
		auxiliar = codigoFinal.primero;
	}

	if(codigoFinal.resultado != NULL)
		free(codigoFinal.resultado);

	fclose(fichero);

	return resultado; 
}
