DELETE FROM venta where coche in (SELECT numcoche
                                  FROM coche
                                  WHERE marca=2);
DELETE FROM coche where marca = 2;

DELETE FROM catalogo where marca = 2;
DELETE FROM vendedor where concesionario in (SELECT cod
                                            FROM concesionario
                                            WHERE marca =2);


Alter table marca disable constraint FK_MARCA_CONCESIONARIO;
Delete from concesionario where marca=2;

DELETE FROM modelo where marca =2;
DELETE FROM marca where id =2;
Alter table marca enable constraint FK_MARCA_CONCESIONARIO;