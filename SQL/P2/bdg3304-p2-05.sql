Select numcoche, puertas
From Coche
Where numcoche not in (Select coche
			From venta);      

ALTER TABLE coche DISABLE CONSTRAINT NPUERTAS;

UPDATE coche SET puertas = 0 WHERE puertas =3 and  numcoche not in (Select coche
			From venta);  
UPDATE coche SET puertas = 3 WHERE puertas=5 and  numcoche not in (Select coche
			From venta); 
UPDATE coche SET puertas = 5 WHERE puertas =0 and  numcoche not in (Select coche
			From venta); 

Select numcoche, puertas
From Coche
Where numcoche not in (Select coche
			From venta);

ALTER TABLE coche Enable CONSTRAINT NPUERTAS;

ROLLBACK; 

Select numcoche, puertas
From Coche
Where numcoche not in (Select coche
			From venta);






