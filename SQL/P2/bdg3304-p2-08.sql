SELECT *
FROM vendedor;

CREATE TABLE RESUMEN_VENDEDOR AS 
(SELECT V.dni, V.nombre,V.concesionario ,M.marca, COUNT(DISTINCT(modelo))cuantos_modelos
FROM vendedor V, coche M
WHERE V.concesionario=M.concesionario and M.numcoche in (SELECT coche
                                                          FROM venta
                                                          WHERE V.dni=vendedor)
HAVING (SELECT COUNT(*)numventas
        FROM VENTA
        WHERE V.dni=vendedor
        GROUP BY vendedor)>=3
GROUP BY v.dni,v.nombre, v.concesionario, m.marca
);

SELECT *
FROM RESUMEN_VENDEDOR;

ALTER TABLE RESUMEN_VENDEDOR ADD Nombre_Jefe VARCHAR(20) default 'SIN JEFE';

UPDATE RESUMEN_VENDEDOR SET Nombre_Jefe = (SELECT vendedor_jefe
                                           FROM vendedor
                                           WHERE RESUMEN_VENDEDOR.dni= vendedor.dni)
WHERE  (SELECT vendedor_jefe
       FROM vendedor
       WHERE RESUMEN_VENDEDOR.dni= vendedor.dni) is not null;
       

SELECT *
FROM RESUMEN_VENDEDOR;

