--CONCESIONARIO
INSERT INTO CONCESIONARIO (cod, nombre, marca, direccion, telefono, fecha_ina)
 VALUES(1,'MERCEDES MURCIA',1,'C/DeLaMercedes Nº22', '+34 968 665522', TO_DATE('20/02/2010', 'dd/mm/yyyy') );

INSERT INTO CONCESIONARIO (cod, nombre, marca, direccion, telefono, fecha_ina)
 VALUES(2,'PEPE AUTOS',1,'C/LACASADEPEPE Nº1', '+34 968 127664', TO_DATE('11/02/2010', 'dd/mm/yyyy') );

INSERT INTO CONCESIONARIO (cod, nombre, marca, direccion, telefono, fecha_ina)
 VALUES(3,'BMW MURCIA',3,'C/PETIRROJO Nº7', '+34 968 780100', TO_DATE('05/03/2005', 'dd/mm/yyyy') );

INSERT INTO CONCESIONARIO (cod, nombre, marca, direccion, telefono, fecha_ina)
 VALUES(4,'AUDI MURCIA',2,'C/PINZON Nº9', '+34 968 132546', TO_DATE('09/02/2003', 'dd/mm/yyyy') );

INSERT INTO CONCESIONARIO (cod, nombre, marca, direccion, telefono, fecha_ina)
 VALUES(5,'TAPADERA DROGA',4,'C/VERTEDEROITALIANO Nº0', '+34 968 111555', TO_DATE('14/04/2010', 'dd/mm/yyyy') );
 
 --MARCA

INSERT INTO MARCA (id, nombre, concesionario_principal)
 VALUES(1,'MERCEDES', 1);

INSERT INTO MARCA (id, nombre, concesionario_principal)
 VALUES(2,'AUDI', 4);

INSERT INTO MARCA (id, nombre, concesionario_principal)
 VALUES(3,'BMW', 3);

INSERT INTO MARCA (id, nombre, concesionario_principal)
 VALUES(4,'FANTASICO', 5);
 
 --MODELO
 
INSERT INTO MODELO (marca, modelo, precio_recomendado) 
 VALUES(1,'Clase S',200000);

INSERT INTO MODELO (marca, modelo, precio_recomendado) 
 VALUES(1,'Clase E',150000);

INSERT INTO MODELO (marca, modelo, precio_recomendado) 
 VALUES(1,'Clase A',100000);

INSERT INTO MODELO (marca, modelo, precio_recomendado) 
 VALUES(2,'a3',30000);

INSERT INTO MODELO (marca, modelo, precio_recomendado) 
 VALUES(2,'a5',50000);

INSERT INTO MODELO (marca, modelo, precio_recomendado) 
 VALUES(3,'X1',40000);

INSERT INTO MODELO (marca, modelo, precio_recomendado) 
 VALUES(3,'X6',70000);

INSERT INTO MODELO (marca, modelo, precio_recomendado) 
 VALUES(4,'Batmovil',2000000);

INSERT INTO MODELO (marca, modelo, precio_recomendado) 
 VALUES(4,'Kitt',1000000);

INSERT INTO MODELO (marca, modelo, precio_recomendado) 
 VALUES(4,'Gadchetomovil',1500000);

INSERT INTO MODELO (marca, modelo, precio_recomendado) 
 VALUES(4,'Barbie',900000);
 
 --CLIENTE
 
 INSERT INTO CLIENTE (dni, nombre, direccion, telefono, cuenta_bancaria) 
 VALUES('11111111L','Juan','c/ falsa1',999333555,  21030166321234567890);

INSERT INTO CLIENTE (dni, nombre, direccion, telefono, cuenta_bancaria) 
 VALUES('11111122T','Pepe','c/ falsa2',999365556,  21030166321234567891);

INSERT INTO CLIENTE (dni, nombre, direccion, telefono, cuenta_bancaria) 
 VALUES('11111133F','Pedro','c/ falsa3',999333565,  21030166321234567892);

INSERT INTO CLIENTE (dni, nombre, direccion, telefono, cuenta_bancaria) 
 VALUES('11111444J','Sergio','c/ falsa5',999333243,  21030166321234567893);

INSERT INTO CLIENTE (dni, nombre, direccion, telefono, cuenta_bancaria) 
 VALUES('11111543S','Fernano','c/ falsa6',999333567,  21030166321234567894);

INSERT INTO CLIENTE (dni, nombre, direccion, telefono, cuenta_bancaria) 
 VALUES('11111563S','Julio','c/ falsa4',999333567,  21030166321234567895);

INSERT INTO CLIENTE (dni, nombre, direccion, telefono, cuenta_bancaria) 
 VALUES('11111154Y','Eduardo','c/ falsa7',999333521,  21030166321234567896);
 
 --CATALOGO
 
 INSERT INTO CATALOGO (concesionario, marca, modelo, precio_orientativo) 
 VALUES(1,1,'Clase S',190000);

INSERT INTO CATALOGO (concesionario, marca, modelo, precio_orientativo) 
 VALUES(1,1,'Clase E',150000);

INSERT INTO CATALOGO (concesionario, marca, modelo, precio_orientativo) 
 VALUES(1,1,'Clase A',100000);

INSERT INTO CATALOGO (concesionario, marca, modelo, precio_orientativo) 
 VALUES(4,2,'a3',190000);

INSERT INTO CATALOGO (concesionario, marca, modelo, precio_orientativo) 
 VALUES(4,2,'X1',190000);

INSERT INTO CATALOGO (concesionario, marca, modelo, precio_orientativo) 
 VALUES(3,3,'X6',200000);

INSERT INTO CATALOGO (concesionario, marca, modelo, precio_orientativo) 
 VALUES(1,1,'Clase E',180000);

INSERT INTO CATALOGO (concesionario, marca, modelo, precio_orientativo) 
 VALUES(4,2,'a5',70000);



INSERT INTO CATALOGO (concesionario, marca, modelo, precio_orientativo) 
 VALUES(5,4,'Kitt',1900000);

INSERT INTO CATALOGO (concesionario, marca, modelo, precio_orientativo) 
 VALUES(5,4,'Barbie',1900000);


INSERT INTO CATALOGO (concesionario, marca, modelo, precio_orientativo) 
 VALUES(5,4,'Gadchetomovil',1900000);

INSERT INTO CATALOGO (concesionario, marca, modelo, precio_orientativo) 
 VALUES(5,4,'Batmovil',1900000);

INSERT INTO CATALOGO (concesionario, marca, modelo, precio_orientativo) 
 VALUES(5,1,'Clase A',120000);

INSERT INTO CATALOGO (concesionario, marca, modelo, precio_orientativo) 
 VALUES(5,2,'a3',192000);
 
 
 -- COCHE
 
 INSERT INTO COCHE (concesionario, marca, modelo, numcoche, color, puertas, cilindrada)
 VALUES(1,1,'Clase S', 1, 'Blanco' , 5, 2000);

INSERT INTO COCHE (concesionario, marca, modelo, numcoche, color, puertas, cilindrada)
 VALUES(1,1,'Clase S', 2, 'Negro' , 5, 2000);

INSERT INTO COCHE (concesionario, marca, modelo, numcoche, color, puertas, cilindrada)
 VALUES(1,1,'Clase E', 3, 'Azul' , 5,  1500);

INSERT INTO COCHE (concesionario, marca, modelo, numcoche, color, puertas, cilindrada)
 VALUES(2,1,'Clase S', 4, 'Rojo' , 5, 2000);

INSERT INTO COCHE (concesionario, marca, modelo, numcoche, color, puertas, cilindrada)
 VALUES(2,1,'Clase E', 5, 'Blanco' , 5, 1800);

INSERT INTO COCHE (concesionario, marca, modelo, numcoche, color, puertas, cilindrada)
 VALUES(3,2,'a3', 6, 'Azul' , 3, 1600);

INSERT INTO COCHE (concesionario, marca, modelo, numcoche, color, puertas, cilindrada)
 VALUES(3,2,'a5', 7, 'Blanco' , 5, 2000);

INSERT INTO COCHE (concesionario, marca, modelo, numcoche, color, puertas, cilindrada)
 VALUES(4,3,'X1', 8, 'Blanco' , 3, 2000);

INSERT INTO COCHE (concesionario, marca, modelo, numcoche, color, puertas, cilindrada)
 VALUES(4,3,'X6', 9, 'Plata' , 5, 2000);

INSERT INTO COCHE (concesionario, marca, modelo, numcoche, color, puertas, cilindrada)
 VALUES(1,1,'Clase S', 10, 'Blanco' , 5, 2000);


INSERT INTO COCHE (concesionario, marca, modelo, numcoche, color, puertas, cilindrada)
 VALUES(5,4,'Batmovil', 12, 'Negro' , 3, 2000);

INSERT INTO COCHE (concesionario, marca, modelo, numcoche, color, puertas, cilindrada)
 VALUES(5,4,'Gadchetomovil', 13, 'negro' , 5, 2000);

INSERT INTO COCHE (concesionario, marca, modelo, numcoche, color, puertas, cilindrada)
 VALUES(5,4,'Kitt', 14, 'negro' , 5, 2000);

INSERT INTO COCHE (concesionario, marca, modelo, numcoche, color, puertas, cilindrada)
 VALUES(5,4,'Barbie', 15, 'Rosa' , 3, 2000);

INSERT INTO COCHE (concesionario, marca, modelo, numcoche, color, puertas, cilindrada)
 VALUES(1,1,'Clase S', 16, 'Azul' , 5, 2000);
 
 --Vendedores
 
 INSERT INTO VENDEDOR (dni, nombre, telefono, vendedor_jefe, concesionario)
 VALUES('88844477R','Boss Mercedes', 985664112, null , 1);

INSERT INTO VENDEDOR (dni, nombre, telefono, vendedor_jefe, concesionario)
 VALUES('11144477Q','Chico Mercedes', 985685712, '88844477R' , 1);

INSERT INTO VENDEDOR (dni, nombre, telefono, vendedor_jefe, concesionario)
 VALUES('88844477E','chica Mercedes', 982164112, '88844477R' , 1);

INSERT INTO VENDEDOR (dni, nombre, telefono, vendedor_jefe, concesionario)
 VALUES(88889477P,'Er Pepe', 988968968, null , 2);

INSERT INTO VENDEDOR (dni, nombre, telefono, vendedor_jefe, concesionario)
 VALUES(88841777R,'Boss Audi', 985664562, null , 3);

INSERT INTO VENDEDOR (dni, nombre, telefono, vendedor_jefe, concesionario)
 VALUES('88844477Y','chico Audi', 980364112, 88841777R , 1);

INSERT INTO VENDEDOR (dni, nombre, telefono, vendedor_jefe, concesionario)
 VALUES('98785423A','Al Capone', 985946112, null , 5);

INSERT INTO VENDEDOR (dni, nombre, telefono, vendedor_jefe, concesionario)
 VALUES('88846537R','Boss BMW', 985524112, null , 4);

INSERT INTO VENDEDOR (dni, nombre, telefono, vendedor_jefe, concesionario)
 VALUES('8851477R','chico BMW', 985129872, '88846537R' , 4);

INSERT INTO VENDEDOR (dni, nombre, telefono, vendedor_jefe, concesionario)
 VALUES('88835564R','Vito Corleone', 985668322, null , 5);

--VENTA

INSERT INTO VENTA (num, coche, cliente, vendedor, fecha_venta, pvp, matricula)
 VALUES(1,2,'11111111L','11144477Q',TO_DATE('11/06/2013', 'dd/mm/yyyy'),150000,'3495BFC');

INSERT INTO VENTA (num, coche, cliente, vendedor, fecha_venta, pvp, matricula)
 VALUES(2,1,'11111133F','11144477Q',TO_DATE('21/09/2011', 'dd/mm/yyyy'),200000,'3489BTR');
 
 INSERT INTO VENTA (num, coche, cliente, vendedor, fecha_venta, pvp, matricula)
 VALUES(3,3,'11111122T','11144477Q',TO_DATE('12/01/2012', 'dd/mm/yyyy'),100000,'9659BIY');
 
 INSERT INTO VENTA (num, coche, cliente, vendedor, fecha_venta, pvp, matricula)
 VALUES(4,13,'11111154Y','98785423A',TO_DATE('24/06/2013', 'dd/mm/yyyy'),2000000,'4706GTR');
 
  INSERT INTO VENTA (num, coche, cliente, vendedor, fecha_venta, pvp, matricula)
 VALUES(5,14,'11111444J','98785423A',TO_DATE('14/07/2013', 'dd/mm/yyyy'),1800000,'4357DGR');
 
  INSERT INTO VENTA (num, coche, cliente, vendedor, fecha_venta, pvp, matricula)
 VALUES(6,15,'11111563S','98785423A',TO_DATE('23/02/2011', 'dd/mm/yyyy'),2300000,'2576MQW');
 
  INSERT INTO VENTA (num, coche, cliente, vendedor, fecha_venta, pvp, matricula)
 VALUES(7,8,'11111133F','88531477R',TO_DATE('21/03/2011', 'dd/mm/yyyy'),30000,'4526DOT');
 
  INSERT INTO VENTA (num, coche, cliente, vendedor, fecha_venta, pvp, matricula)
 VALUES(8,9,'11111444J','88531477R',TO_DATE('25/06/2011', 'dd/mm/yyyy'),70000,'7135HAP');

