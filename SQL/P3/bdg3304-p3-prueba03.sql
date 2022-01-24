SET SERVEROUTPUT ON;
DECLARE
mensaje VARCHAR2(50);
BEGIN

mensaje:=p_con.compras_clientes('11111122T');
DBMS_OUTPUT.PUT_LINE(mensaje);
p_con.ventas_vendedor('11144477Q');

END;