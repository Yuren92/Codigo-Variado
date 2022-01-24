compiladorMiniC : lex.yy.c miniC.tab.c miniC.tab.h main.c id.h id.c string.h string.c codigo.c codigo.h
	gcc main.c lex.yy.c miniC.tab.c id.c string.c codigo.c -lfl -o compiladorMiniC

lex.yy.c : miniC.l miniC.tab.h 
	flex --yylineno miniC.l

miniC.tab.c miniC.tab.h : miniC.y id.h string.h
	bison -v -t -d miniC.y

clean :
	rm -f compiladorMiniC lex.yy.c miniC.tab.* miniC.output
