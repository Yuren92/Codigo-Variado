SET SERVEROUTPUT ON;
CREATE OR REPLACE
PACKAGE P_CON IS
  FUNCTION compras_clientes (unDNI IN venta.cliente%type) 
    RETURN VARCHAR2;
  PROCEDURE ventas_vendedor (dniv IN vendedor.dni%type); 
END P_CON;
/

create or replace
PACKAGE BODY P_CON IS
  FUNCTION compras_clientes (unDNI IN venta.cliente%type)
  RETURN VARCHAR2 IS
  dniClienteV venta.cliente%type;
  matriculaC venta.matricula%type;
  dniClienteC cliente.dni%type;
  numeroCochesVendidos NUMBER;
  
  BEGIN
  
  select matricula INTO matriculaC
  from venta
  where unDNI=cliente;
  RETURN (matriculaC);
  EXCEPTION 
  WHEN TOO_MANY_ROWS THEN
    BEGIN
        select count(cliente) INTO NumeroCochesVendidos
                                  from venta
                                  where unDni=cliente
                                  group by cliente;
          RETURN NumeroCochesVendidos;
    END;
   WHEN NO_DATA_FOUND THEN
      BEGIN
        select dni into dniClienteC
        from cliente 
        where unDni=dni;
      RETURN ('Cliente sin coches');
      EXCEPTION WHEN NO_DATA_FOUND THEN RETURN ('Cliente inexistente');
      END;
  END;
 
PROCEDURE ventas_vendedor (dniv IN vendedor.dni%type)
IS
nombrevendedor vendedor.nombre%type;
concevendedor concesionario.nombre%type;
marcaconce marca.nombre%type;
contador number;
dnia vendedor.dni%type;
CURSOR curs IS
 select fecha_venta, pvp, modelo
 from venta, coche
 where dniv=vendedor and numcoche=coche;
 ult curs%rowtype;
 
begin
  
  select dni into dnia
  from vendedor
  where dni=dniv;
  if (dnia<>dniv) then
    DBMS_OUTPUT.PUT_LINE('Ese vendedor no existe.');
  else
  begin
  select nombre into nombrevendedor 
  from vendedor 
  where dni=dniv;
  

  
  select nombre into concevendedor
  from concesionario
  where cod in (select concesionario
                from vendedor
                where dni=dniv);
                
  select nombre into marcaconce
  from marca
  where id in (select marca
              from concesionario
              where nombre=concevendedor);
  
    select count (*) into contador
    from venta
    where vendedor=dniv
    group by vendedor;
     dbms_output.put_line ('VENDEDOR '|| '    ' || nombrevendedor);
     dbms_output.put_line ('CONCESIONARIO  ' || concevendedor);
     dbms_output.put_line ('MARCA  '|| marcaconce);
     dbms_output.put_line ('    '||contador || '    '||'VENTAS : ');
     

     
     if contador>0 then
     begin
      open curs;
      loop
      fetch curs into ult;
      exit when curs%NOTFOUND;
       dbms_output.put_line ('--- FECHA : '|| ult.fecha_venta ||'; MODELO : '|| ult.modelo ||'. PVP : '|| ult.pvp || '? .');
      end loop;
      close curs;
     end;
     else dbms_output.put_line('ningunaventa');
     end if;
end;
  
end if; 
end;

  END  P_CON;