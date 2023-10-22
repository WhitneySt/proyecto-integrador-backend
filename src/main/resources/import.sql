--  Script para insertar datos en la base de datos, se ejecuta al iniciar la aplicación porque está configurado en el archivo application.properties con la propiedad spring.jpa.hibernate.ddl-auto=create

INSERT INTO roles (id,nombre)VALUES (1,"Usuario");
INSERT INTO roles (id,nombre)VALUES (2,"Administrador");

INSERT INTO estados (id,nombre)VALUES (1,true);
INSERT INTO estados (id,nombre)VALUES (2,false);

INSERT INTO tipo_transaccion (id, nombre) values (1, 'Retiro');
INSERT INTO tipo_transaccion (id, nombre) values (2, 'Transferencia');
INSERT INTO tipo_transaccion (id, nombre) values (3, 'Deposito');
INSERT INTO tipo_transaccion (id, nombre) values (4, 'Devolucion');

INSERT INTO tipo_movimiento (id, codigo_destino, codigo_origen, descripcion) VALUES ('1', 'CU', 'CU', 'Cuenta a Cuenta'), ('2', 'BO', 'CU', 'Cuenta a Bolsillo'), ('3', 'CU', 'BO', 'Bolsillo a Cuenta'), ('4', 'BO', 'BO', 'Bolsillo a Bolsillo');



