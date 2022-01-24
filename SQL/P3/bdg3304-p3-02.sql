SET SERVEROUTPUT ON;
DECLARE

CURSOR concesionarios (marc MARCA.id%type) IS
SELECT nombre, cod
FROM  CONCESIONARIO
WHERE marca=marc;

CURSOR modelos (conc CONCESIONARIO.cod%type) IS
SELECT modelo
FROM CATALOGO
WHERE concesionario=conc;

CURSOR marcas IS
SELECT id, nombre, concesionario_principal
FROM MARCA;
numModelos NUMBER:=0;
numConcesionarios NUMBER:=0;

BEGIN

FOR m IN marcas LOOP

SELECT COUNT(*) INTO numConcesionarios
FROM CONCESIONARIO
WHERE marca=m.id;

DBMS_OUTPUT.PUT_LINE('MARCA ' || m.nombre);
DBMS_OUTPUT.PUT_LINE('-----' || numConcesionarios || ' CONCESIONARIOS:');
FOR co IN CONCESIONARIOS (m.id) LOOP

SELECT COUNT(*) INTO numModelos
FROM CATALOGO
WHERE co.cod=concesionario;

DBMS_OUTPUT.PUT('----------' || co.nombre);
IF (co.cod=m.concesionario_principal) THEN
DBMS_OUTPUT.PUT_LINE(' ES EL PRINCIPAL');

ELSE DBMS_OUTPUT.NEW_LINE;
END IF;
DBMS_OUTPUT.PUT_LINE('------------' || numModelos || ' MODELOS:');
FOR mo IN modelos(co.cod) LOOP
DBMS_OUTPUT.PUT_LINE('----------------' || RPAD(mo.modelo, 15));
END LOOP;
END LOOP;
DBMS_OUTPUT.NEW_LINE;
END LOOP;
END;