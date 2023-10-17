--  Script para insertar datos en la base de datos, se ejecuta al iniciar la aplicación porque está configurado en el archivo application.properties con la propiedad spring.jpa.hibernate.ddl-auto=create

INSERT INTO roles (id,nombre)VALUES (1,"Usuario");
INSERT INTO roles (id,nombre)VALUES (2,"Administrador");

INSERT INTO estados (id,nombre)VALUES (1,true);
INSERT INTO estados (id,nombre)VALUES (2,false);



