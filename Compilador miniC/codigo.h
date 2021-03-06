#ifndef  ___MINIC_H
#define ___MINIC_H

#include <stdio.h>
#include <stdlib.h>
#include "string.h"
#include "id.h"

typedef struct linea{
	char *operacion;
	char *argumento1;
	char *argumento2;
	char *argumento3;
	struct linea *sig;
}linea;

typedef struct codigo{
	linea *primero;
	linea *ultimo;
	char *resultado;
}codigo;

linea *crearLinea(char *operacion, char *argumento1, char *argumento2, char *argumento3);
char *registroLibre();
void inicializaPadre(codigo *cod);
void concatenaLineaACodigo(codigo *cod, linea *linea1);
void comprobarIdentificador(char* nombre, int lineaActual);
char *concatenarString(char* prefijo, char *sufijo);
void subirCodigoAPadre(codigo *cod1, codigo *cod2);
void concatenarCodigo(codigo *cod1, codigo *cod2);
char *crearTag();
int esNulo(codigo *cod);

#endif
