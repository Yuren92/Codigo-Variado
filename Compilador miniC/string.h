#ifndef  ___STRING_H
#define ___STRING_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void inicializarString();
void insertarString(char* cadena);
int buscarNumeroCadena(char *cadena);
char *buscarString(char *cadena);
void imprimirString(FILE *archivo);
void borrarString();

#endif
