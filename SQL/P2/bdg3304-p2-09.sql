--Rl1
--Las marcas por definicion sólo tienen un concesionario principal, se hizo al creal la tabla.

--R12
-- NO PODEMOS HACER ESTA REGLA DE INTEGRIDAD PUESTO QUE NO PODEMOS HACER UNA BUSQUEDA 
--Y SQL NO PERMITE CREAR ASERTOS.

--Rl3
-- NO PODEMOS HACER ESTA REGLA DE INTEGRIDAD PUESTO QUE NO PODEMOS HACER UNA BUSQUEDA 
--Y SQL NO PERMITE CREAR ASERTOS

--R14
--En coche se hace referencia al catalogo por lo tanto no puede haber un coche 
--en un concesionario que no trabaje con ese modelo.

--Rl5
ALTER TABLE VENTA ADD CONSTRAINT CHECK_UNIQUECOCHE UNIQUE(coche);