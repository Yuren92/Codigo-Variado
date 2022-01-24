CREATE VIEW VENTAS_MARCA AS 
(SELECT C.marca, to_char(V.fecha_venta,'yyyy') año, to_char(V.fecha_venta,'mm') mes, COUNT(*)cuantos_coches, SUM(V.pvp) total_venta
FROM venta V, COCHE C
where C.numcoche=V.coche
GROUP BY marca,to_char(V.fecha_venta,'mm'), to_char(V.fecha_venta,'yyyy')
);

SELECT *
FROM VENTAS_MARCA
ORDER BY marca, año, mes;

--Una vista definida sobre varias tablas mediante reuniones no se puede modificar
--Se puede hacer modificando directamente la tabla o la definicion de la vista.
DROP VIEW VENTAS_MARCA;
CREATE VIEW VENTAS_MARCA AS 
(SELECT C.marca, to_char(V.fecha_venta,'yyyy') año, to_char(V.fecha_venta,'mm') mes, COUNT(*)cuantos_coches, SUM(V.pvp)*1.21 total_venta
FROM venta V, COCHE C
where C.numcoche=V.coche
GROUP BY marca,to_char(V.fecha_venta,'mm'), to_char(V.fecha_venta,'yyyy')
);


DROP VIEW VENTAS_MARCA;

CREATE VIEW VENTAS_MARCA AS 
(SELECT  C.marca, to_char(V.fecha_venta,'yyyy') año, to_char(V.fecha_venta,'mm') mes,
COUNT(*) cuantos_coches, SUM(V.pvp)*1.21 total_venta, m.concesionario_principal concesionario
FROM venta V, COCHE C, marca M
where C.numcoche=V.coche and C.marca=M.id
GROUP BY C.marca, to_char(V.fecha_venta,'mm'), to_char(V.fecha_venta,'yyyy'), m.concesionario_principal
);

SELECT *
FROM VENTAS_MARCA;

