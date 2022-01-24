CREATE OR REPLACE
TRIGGER comprobacion_coordinador
BEFORE
INSERT OR UPDATE OF num, coche, vendedor
ON VENTA
FOR EACH ROW
DECLARE
concesionario_coche coche.concesionario%type;
BEGIN

SELECT concesionario INTO concesionario_coche
FROM VENDEDOR
WHERE dni=:NEW.vendedor AND concesionario =( select concesionario
                                                from coche
                                                where numcoche = :NEW.coche);
EXCEPTION
WHEN NO_DATA_FOUND THEN
RAISE_APPLICATION_ERROR(-20003,'EL vendedor no pertenece al mismo concesionario que el coche.');
END;



CREATE OR REPLACE

TRIGGER cambio_concesionario
BEFORE
UPDATE OF concesionario
ON VENDEDOR
FOR EACH ROW
DECLARE
marcas1 marca.id%type;
marcas2 marca.id%type;
BEGIN


SELECT marca INTO marcas1
FROM Concesionario
WHERE cod=:NEW.concesionario;


SELECT marca INTO marcas2
FROM Concesionario
WHERE cod=:OLD.concesionario;


IF (marcas1<>marcas2)
then
RAISE_APPLICATION_ERROR(-20004,'El Vendedor no puede cambiar de concesionario si no es de la misma marca.');

END IF;

EXCEPTION
WHEN NO_DATA_FOUND THEN 
RAISE_APPLICATION_ERROR(-20003,'EL concesionari no existe.');
END;