INSERT INTO modelo(marca, modelo, precio_recomendado)
 VALUES (3, 'X3' ,40000);

UPDATE catalogo SET modelo='X1' WHERE modelo='X3';
UPDATE coche SET modelo='X1' WHERE modelo='X3';

DELETE FROM modelo WHERE modelo='X3';