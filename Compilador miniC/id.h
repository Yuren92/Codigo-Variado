#ifndef  ___ID_H
#define ___ID_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct identificador{
	char *nombre;
	int valor;
	int tipo;
	struct identificador *sig;
} identificador;

void inicializacionTablaSimbolos();
void establecerTipo(int tipo);
void insertarID(char* id, int linea);
void existeID(char *nombre, int linea);
void insertarTablaSimbolos(char *nombre, int valor);
int existeEnTablaSimbolos(char *nombre);
identificador *buscaID(char *nombre);
int esConstante(char *nombre);
void imprimirID(FILE *archivo);
void borrarTablaSimbolos();

#endif
