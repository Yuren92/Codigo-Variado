#include "string.h"

typedef struct tString{
	char *cadena;
	char *nombreGenerico;
	struct tString *sig;
} tString;

tString *primeroStr, *ultimoStr;

void inicializarString(){
	primeroStr = ultimoStr = NULL;
}

void insertarString(char* cadena){
	int numCadena = buscarNumeroCadena(cadena);
	if (numCadena){
		tString* auxiliar = (tString *) malloc(sizeof(tString));
		auxiliar->nombreGenerico = malloc(sizeof(char) * 150); 
		
		if ((auxiliar == NULL) || (auxiliar->nombreGenerico == NULL)){
			fprintf(stderr, "Error: no hay memoria!\n");
			exit(1);
		}
		auxiliar->cadena = cadena;
		sprintf(auxiliar->nombreGenerico, "$str%d", numCadena);
		auxiliar->sig = NULL;
		if(ultimoStr != NULL)
			ultimoStr->sig = auxiliar;
		if(primeroStr == NULL)
			primeroStr = auxiliar;

		ultimoStr = auxiliar;
	}
}

int buscarNumeroCadena(char *cadena){
	tString *auxiliar = primeroStr;
	int i = 1;
	while (auxiliar != NULL){
		if (!strcmp(cadena,auxiliar->cadena))
			return 0;
		i++;
		auxiliar = auxiliar -> sig;
	}
	return i;
}

char *buscarString(char *cadena){
	tString* auxiliar = primeroStr;
	while(auxiliar != NULL){
		if(!strcmp(auxiliar->cadena,cadena))
			return auxiliar->nombreGenerico;
		auxiliar = auxiliar->sig;
	}
	return NULL;
}

void imprimirString(FILE *archivo){
	tString* auxiliar = primeroStr;

	fprintf(archivo,"# Cadenas del programa\n");
	while(auxiliar != NULL){
		fprintf(archivo,"%s:\n", auxiliar->nombreGenerico);
		fprintf(archivo,"\t.asciiz %s\n",auxiliar->cadena);
		auxiliar = auxiliar->sig;
	}
}

void borrarString(){
	tString *auxiliar = primeroStr;
	while (auxiliar != NULL) {
		primeroStr = auxiliar->sig;
		free(auxiliar->cadena);
		free(auxiliar->nombreGenerico);
		free(auxiliar);
		auxiliar = primeroStr;
	}
	ultimoStr = NULL;
}

