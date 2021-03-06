#include "miniC.h"

char registros[10] = { 0 };
int numeroTag = 1;

linea *crearLinea(char *operacion, char *argumento1, char *argumento2, char *argumento3){
	linea *linea1 = (linea*)malloc(sizeof(linea));

	if (linea1 == NULL){
		printf("Error, no hay espacio de memoria.\n");
		exit(1);
	}
	
	linea1->operacion = strdup(operacion);

	if(argumento1 != NULL)
		linea1->argumento1 = strdup(argumento1);
	else
		linea1->argumento1 = NULL;

	if(argumento2 != NULL)
		linea1->argumento2 = strdup(argumento2);
	else
		linea1->argumento2 = NULL;
	
	if(argumento3 != NULL)
		linea1->argumento3 = strdup(argumento3);
	else
		linea1->argumento3 = NULL;
	
	linea1->sig = NULL;

	return linea1;
}

char *registroLibre(){
		int i;
	for(int i=0; i < 10; i++){
		if (registros[i] == 0){
			registros[i] = 1;
			char auxiliar[4];
			sprintf(auxiliar,"$t%d",i);
			return strdup(auxiliar);
		}
	}

	if (i == 10){
		printf("Error: registros agotados!\n");
		exit(1);
	}

}

void inicializaPadre(codigo *cod){
	cod->primero = cod->ultimo = NULL;
	cod->resultado = NULL;
}

void concatenaLineaACodigo(codigo *cod, linea *linea1){
	if (cod->primero == NULL)
		cod->primero = cod->ultimo = linea1;
	else{
		cod->ultimo->sig = linea1;
		cod->ultimo = linea1;
	}

}

void comprobarIdentificador(char* nombre, int lineaActual){
	if(!buscaListaVar(nombre))
		printf("Error en la línea %d: Variable %s no declarada\n", linea, nombre);
}

char *concatenarString(char* prefijo, char *sufijo){
	char *auxiliar = (char *) malloc(strlen(prefijo)+strlen(sufijo)+1);

	sprintf(auxiliar,"%s%s",prefijo,sufijo);
	return auxiliar;
}

/*void subirCodigoAPadre(codigo *cod1, codigo *cod2){
	cod1->primero = cod2->primero;
	cod1->ultimo = cod2->ultimo;
	cod1->resultado = cod2->resultado;
}*/

void concatenarCodigo(codigo *cod1, codigo *cod2){
		if(!esNulo(cod2)){
			if(esNulo(cod1)){
				cod1->primero=cod2->primero;
				cod1->ultimo=cod2->ultimo;
				cod1->resultado = cod2->resultado;
			}
			else{	
				cod1->ultimo->sig=cod2->primero;
				cod1->ultimo=cod2->ultimo;	
			}
		}
}

void liberarRegistros(char *reg){
	int i = reg[2] - '0';
	registros[i] = 0;
}

char *crearTag(){
	char* resultado = (char*) malloc(sizeof(char)*5);
	sprintf(resultado, "$l%d", numeroTag);
	numeroTag++;

	return resultado;
}

