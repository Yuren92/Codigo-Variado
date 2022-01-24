#include "id.h"

identificador *primero, *ultimo;

void inicializacionTablaSimbolos(){
	primero = ultimo = NULL;
}

void establecerTipo(int tipo){
	identificador *auxiliar = primero;
	
	while (auxiliar != NULL){
		if (auxiliar->tipo == 0)
			auxiliar->tipo = tipo;
		auxiliar = auxiliar->sig;
	}
}

void insertarID(char* id, int linea){
	int busqueda = existeEnTablaSimbolos(id);
	if(!existeEnTablaSimbolos(id))
		insertarTablaSimbolos(id, linea);
	else
		printf("Error en la línea %d: Variable %s ya declarada en la línea %d\n", linea, id, busqueda);
}

void existeID(char *nombre, int linea){
	if(!existeEnTablaSimbolos(nombre))
		printf("Error en la línea %d: Variable %s no declarada\n", linea, nombre);
}

void insertarTablaSimbolos(char *nombre, int valor){
	identificador *auxiliar = buscaID(nombre);
	if (auxiliar != NULL)
		auxiliar->valor = valor;
	else{
		auxiliar = (identificador *) malloc(sizeof(identificador));
		if (auxiliar == NULL){
			fprintf(stderr, "Error: no hay memoria!\n");
			exit(1);
		}

		auxiliar->valor = valor;
		auxiliar->nombre = nombre;
		auxiliar->tipo = 0;
		auxiliar->sig = NULL;
		if(ultimo != NULL)
			ultimo->sig = auxiliar;

		if(primero == NULL)
			primero = auxiliar;

		ultimo = auxiliar;
	}
}

int existeEnTablaSimbolos(char *nombre){
	identificador * auxiliar = buscaID(nombre);

	if (auxiliar != NULL)
		return auxiliar->valor;
	
	return 0;
}

identificador *buscaID(char *nombre){
	identificador *auxiliar = primero;
	while (auxiliar != NULL){
		if (!strcmp(nombre, auxiliar->nombre))
			return auxiliar;
		
		auxiliar = auxiliar -> sig;
	}
	
	return NULL;
}

int esConstante(char *nombre){

	identificador *auxiliar = buscaID(nombre);
	if (auxiliar != NULL)
		if (auxiliar->tipo == 2)
			return 1;

	return 0;
}

void imprimirID(FILE *archivo){
	identificador* auxiliar = primero;

	fprintf(archivo,"# Variables globales\n");
	while(auxiliar != NULL){
		fprintf(archivo,"_%s:\n", auxiliar->nombre);
		fprintf(archivo,"\t.word 0\n");
		auxiliar = auxiliar->sig;
	}
}

void borrarTablaSimbolos(){
	identificador *auxiliar = primero;
	while (auxiliar != NULL) {
		primero = auxiliar->sig;
		free(auxiliar->nombre);
		free(auxiliar);
		auxiliar = primero;
	}

	ultimo = NULL;
}

