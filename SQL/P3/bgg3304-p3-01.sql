SET SERVEROUTPUT ON;
DECLARE
CURSOR vendedores IS
SELECT nombre
FROM VENDEDOR 
ORDER BY nombre;
CURSOR clientes IS
SELECT nombre
FROM CLIENTE
ORDER BY nombre;
vender VENDEDOR.nombre%TYPE;
client CLIENTE.nombre%TYPE;
BEGIN
DBMS_OUTPUT.PUT_LINE('Vendedores           Clientes');
DBMS_OUTPUT.PUT_LINE('--------------       --------------');
OPEN vendedores;
OPEN clientes;

LOOP
FETCH vendedores INTO vender;
FETCH clientes INTO client;

EXIT WHEN (vendedores%NOTFOUND OR clientes%NOTFOUND);
DBMS_OUTPUT.PUT_LINE(RPAD(vender, 35) || client);
END LOOP;
IF (vendedores%NOTFOUND) THEN

IF (clientes%FOUND) THEN
LOOP
DBMS_OUTPUT.PUT_LINE(RPAD(' ', 35) || client);
FETCH clientes INTO client;
EXIT WHEN (clientes%NOTFOUND);
END LOOP;
END IF;

ELSE
LOOP
DBMS_OUTPUT.PUT_LINE(vender);
FETCH vendedores INTO vender;
EXIT WHEN (vendedores%NOTFOUND);
END LOOP;
END IF;
DBMS_OUTPUT.PUT_LINE('--------------------------------------------------');
DBMS_OUTPUT.PUT_LINE('TOTAL: ' ||
vendedores%ROWCOUNT || ' Vendedores Y ' ||
clientes%ROWCOUNT || ' Clientes');
CLOSE vendedores;
CLOSE clientes;
END;
