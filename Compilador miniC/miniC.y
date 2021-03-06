%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "codigo.h"
#include "id.h"
#include "string.h"

void yyerror(const char *msg);

extern int yylex(void);
extern codigo codigoFinal;
extern int yylineno;
int flag=0;

%}

/*Declaraciones de tokens*/
%token IF ELSE READ WHILE PRINT FUNC VAR LET DO FOR LPAREN RPAREN LKEY RKEY SEMICOLON COMMA ASSIGNOP PLUSOP MINUSOP ASTERISK BARRA
%type <cod> expression statement statement_list read_list print_item print_list identifier_list asig declarations
%token <cadena> ID STRING INTLITERAL

%union {

   char *cadena;
   codigo cod;
}

%start program
%left PLUSOP MINUSOP
%left ASTERISK BARRA
%left UMENOS

%%

/*Gramatica miniC*/
program : FUNC ID LPAREN RPAREN LKEY declarations statement_list RKEY { 
																		concatenarCodigo(&$6,&$7);
																		linea *linea1 = crearLinea("etiq", "main", NULL,NULL);
																		concatenaLineaACodigo(&codigoFinal, linea1);
																		concatenarCodigo(&codigoFinal, &$6);
																		linea *linea2 = crearLinea("li", "$v0", "10", NULL);
																		concatenaLineaACodigo(&codigoFinal, linea2);
																		linea *linea3 = crearLinea("syscall", NULL, NULL, NULL);
																		concatenaLineaACodigo(&codigoFinal, linea3); };

declarations : declarations VAR  identifier_list SEMICOLON { 	inicializaPadre(&$$);
																establecerTipo(1);
																concatenarCodigo(&$1,&$3);
																concatenarCodigo(&$$,&$1); }

	    	| declarations LET identifier_list SEMICOLON {	inicializaPadre(&$$);
															establecerTipo(2);
															concatenarCodigo(&$1,&$3);
															concatenarCodigo(&$$,&$1); }

	    	| /*lambda*/ { inicializaPadre(&$$); };

identifier_list : asig { 	inicializaPadre(&$$);
							concatenarCodigo(&$$,&$1); }

	    	| identifier_list COMMA asig  { inicializaPadre(&$$);
											concatenarCodigo(&$1,&$3);
											concatenarCodigo(&$$,&$1); };	
													
asig :	ID 	{ 	inicializaPadre(&$$);
				insertarID($1,yylineno); }

     		| ID ASSIGNOP expression { 	inicializaPadre(&$$);
										insertarID($1,yylineno);
										linea *linea1 = crearLinea("sw", $3.resultado, concatenarString("_",$1), NULL);
										concatenaLineaACodigo(&$3,linea1);
										concatenarCodigo(&$$,&$3);
										liberarRegistros($3.resultado); };

statement_list : statement_list statement { inicializaPadre(&$$);
											concatenarCodigo(&$1,&$2);
											concatenarCodigo(&$$,&$1); }

	       | /*lambda*/ { inicializaPadre(&$$); };

statement : ID ASSIGNOP expression SEMICOLON { inicializaPadre(&$$);
												existeID($1,yylineno);
									 	        if(esConstante($1))
													printf("Error en la linea: %d.No se puede modificar el valor de una constante %s \n", yylineno, $1);
												linea *linea1 = crearLinea("sw", $3.resultado, concatenarString("_",$1), NULL);
												concatenaLineaACodigo(&$3,linea1);
												concatenarCodigo(&$$,&$3);
												liberarRegistros($3.resultado); }

	       | LKEY statement_list RKEY { inicializaPadre(&$$);
										 concatenarCodigo(&$$,&$2); }

	       | IF LPAREN expression RPAREN  statement ELSE statement  { 	inicializaPadre(&$$);
																		char *etiq_else = crearTag();
																		linea *linea2 = crearLinea("beqz", $3.resultado, etiq_else,NULL);
																		concatenarCodigo(&$$,&$3);
																		concatenaLineaACodigo(&$$,linea2);
																		concatenarCodigo(&$$,&$5);
																		char *etiq_noElse = crearTag();
																		linea *linea3 = crearLinea("b", etiq_noElse, NULL, NULL);
																		concatenaLineaACodigo(&$$,linea3);
																		linea *linea1 = crearLinea("etiq", etiq_else, NULL, NULL);
																		concatenaLineaACodigo(&$$,linea1);
																		concatenarCodigo(&$$,&$7);
																		linea *linea4 = crearLinea("etiq", etiq_noElse, NULL, NULL);
																		concatenaLineaACodigo(&$$,linea4);
																		liberarRegistros($3.resultado);}

	       | IF LPAREN expression RPAREN statement { 	inicializaPadre(&$$);
														char *etiq_false = crearTag();
														linea *linea2 = crearLinea("beqz", $3.resultado, etiq_false, NULL);
														concatenarCodigo(&$$,&$3);
														concatenaLineaACodigo(&$$,linea2);
														linea *linea1 = crearLinea("etiq", etiq_false, NULL, NULL);
														concatenarCodigo(&$$,&$5);
														concatenaLineaACodigo(&$$,linea1);
														liberarRegistros($3.resultado); }

	       | WHILE LPAREN expression RPAREN  statement   { 	inicializaPadre(&$$);
															char *etiq_expr = crearTag();
															linea *linea1 = crearLinea("etiq", etiq_expr, NULL, NULL);
															concatenaLineaACodigo(&$$,linea1);
															concatenarCodigo(&$$,&$3);
															char *etiq_false = crearTag();
															linea *linea2 = crearLinea("beqz", $3.resultado, etiq_false, NULL);
															concatenaLineaACodigo(&$$,linea2);
															concatenarCodigo(&$$,&$5);
															linea *linea3 = crearLinea("b", etiq_expr, NULL, NULL);
															concatenaLineaACodigo(&$$,linea3);
															linea *linea4 = crearLinea("etiq", etiq_false, NULL, NULL);
															concatenaLineaACodigo(&$$,linea4);
															liberarRegistros($3.resultado);}

	       | DO statement WHILE LPAREN expression RPAREN SEMICOLON	{ 	inicializaPadre(&$$);
																		char *do_expr = crearTag();
																		linea *linea1 = crearLinea("etiq", do_expr, NULL, NULL);
																		concatenaLineaACodigo(&$$,linea1);
																		concatenarCodigo(&$$,&$2);
																		concatenarCodigo(&$$,&$5);
																		linea *linea2 = crearLinea("bnez", $5 .resultado, do_expr, NULL);
																		concatenaLineaACodigo(&$$,linea2);
																		liberarRegistros($5.resultado);}

		| FOR LPAREN expression RPAREN  statement   { 	inicializaPadre(&$$);
														char *etiq_expr = crearTag();
														linea *linea1 = crearLinea("etiq", etiq_expr, NULL, NULL);
														concatenaLineaACodigo(&$$,linea1);
														concatenarCodigo(&$$,&$3);
														char *etiq_false = crearTag();
														linea *linea2 = crearLinea("beqz", $3.resultado, etiq_false, NULL);
														concatenaLineaACodigo(&$$,linea2);
														concatenarCodigo(&$$,&$5);
														linea *linea3 = crearLinea("b", etiq_expr, NULL, NULL);
														concatenaLineaACodigo(&$$,linea3);
														linea *linea4 = crearLinea("etiq", etiq_false, NULL, NULL);
														concatenaLineaACodigo(&$$,linea4);
														liberarRegistros($3.resultado); }

	       | PRINT print_list SEMICOLON { inicializaPadre(&$$);
										  concatenarCodigo(&$$,&$2); }

	       | READ read_list SEMICOLON { inicializaPadre(&$$);	       
	       								concatenarCodigo(&$$,&$2); 
		};

print_list : print_item { inicializaPadre(&$$); 
						concatenarCodigo(&$$,&$1); }

	       | print_list COMMA print_item { 	inicializaPadre(&$$);
									 		concatenarCodigo(&$1,&$3);
											concatenarCodigo(&$$,&$1);};

print_item : expression { 	inicializaPadre(&$$);
							linea *linea1 = crearLinea("move", "$a0", $1.resultado, NULL);
							linea *linea2 = crearLinea("li", "$v0", "1", NULL);
							linea *linea3 = crearLinea("syscall", NULL, NULL, NULL);
							concatenaLineaACodigo(&$1,linea1);
							concatenaLineaACodigo(&$1,linea2);
							concatenaLineaACodigo(&$1,linea3);
							concatenarCodigo(&$$,&$1);
							liberarRegistros($1.resultado); }

	       | STRING { 	inicializaPadre(&$$);
						insertarString($1);
						linea *linea1 = crearLinea("la", "$a0", buscarString($1), NULL);
						linea *linea2 = crearLinea("li", "$v0", "4", NULL);
						linea *linea3 = crearLinea("syscall", NULL, NULL, NULL);
						concatenaLineaACodigo(&$$,linea2);
						concatenaLineaACodigo(&$$,linea1);
						concatenaLineaACodigo(&$$,linea3);};

read_list : ID { inicializaPadre(&$$);
	       		 if(esConstante($1))
					printf("Error en linea: %d.Una constante no se puede modificar %s \n", yylineno, $1);

				linea *linea1 = crearLinea("li", "$v0", "5", NULL);			
				linea *linea2 = crearLinea("syscall", NULL, NULL, NULL);
				linea *linea3 = crearLinea("sw", "$v0", concatenarString("_",$1), NULL);
				concatenaLineaACodigo(&$$,linea1);
				concatenaLineaACodigo(&$$,linea2); 
				concatenaLineaACodigo(&$$,linea3);}

	       | read_list COMMA ID {  inicializaPadre(&$$);
	       							if(esConstante($3))
										printf("Error en linea: %d.Una constante no se puede modificar %s \n", yylineno, $3);

								   	linea *linea1 = crearLinea("li", "$v0", "5", NULL);			
									linea *linea2 = crearLinea("syscall", NULL, NULL, NULL);
									linea *linea3 = crearLinea("sw", "$v0", concatenarString("_",$3), NULL);
									concatenaLineaACodigo(&$$,linea1);
									concatenaLineaACodigo(&$$,linea2); 
									concatenaLineaACodigo(&$$,linea3);};

expression : expression PLUSOP expression { inicializaPadre(&$$);
											linea *linea1 = crearLinea("add", $1.resultado, $1.resultado, $3.resultado);
											concatenarCodigo(&$$,&$1);
											concatenarCodigo(&$$,&$3);
											concatenaLineaACodigo(&$$,linea1);
											liberarRegistros($3.resultado); }

	       | expression MINUSOP expression { inicializaPadre(&$$);
											linea *linea1 = crearLinea("sub", $1.resultado, $1.resultado, $3.resultado);
											concatenarCodigo(&$$,&$1);
											concatenarCodigo(&$$,&$3);
											concatenaLineaACodigo(&$$,linea1);
											liberarRegistros($3.resultado); }

	       | expression ASTERISK expression { inicializaPadre(&$$);
											linea *linea1 = crearLinea("mul", $1.resultado, $1.resultado, $3.resultado);
											concatenarCodigo(&$$,&$1);
											concatenarCodigo(&$$,&$3);
											concatenaLineaACodigo(&$$,linea1);
											liberarRegistros($3.resultado); }

	       | expression BARRA expression { inicializaPadre(&$$);
											linea *linea1 = crearLinea("div", $1.resultado, $1.resultado, $3.resultado);
											concatenarCodigo(&$$,&$1);
											concatenarCodigo(&$$,&$3);
											concatenaLineaACodigo(&$$,linea1);
											liberarRegistros($3.resultado);}

	       | MINUSOP expression %prec UMENOS { inicializaPadre(&$$);
												linea *linea1 = crearLinea("neg", $2.resultado, $2.resultado, NULL);
												concatenaLineaACodigo(&$$,linea1); }

	       | LPAREN expression RPAREN { inicializaPadre(&$$);
										concatenarCodigo(&$$,&$2);/*subirCodigoAPadre(&$$,&$2);*/ }

	       | ID { 	inicializaPadre(&$$);
					existeID($1,yylineno);
					linea *linea1 = crearLinea("lw", registroLibre(), concatenarString("_", $1), NULL);	
					concatenaLineaACodigo(&$$,linea1);}

               | INTLITERAL { 	inicializaPadre(&$$);
								linea *linea1 = crearLinea("li", registroLibre(), $1, NULL);
								concatenaLineaACodigo(&$$,linea1);
								$$.resultado = linea1->argumento1;};

%%
/*Funciones auxiliares*/
void yyerror(const char *msg) {

	printf("Error en la linea %d: %s\n", yylineno, msg);
}

