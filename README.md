# Spring Boot Clients Management System

## Ejecucion de la solucion

Primero ejecutamos el comando desde la consola `maven clean compile install` para realizar la limpieza compilacion e
instalacion del proyecto

#

Luego ejecutamos el comando desde la consola `mvn springboot:run` para iniciar el backend de la solucion

## Swagger Documentation

La documentacion swagger se encuentra disponible en la url `http://localhost:8080/swagger-ui/`

## H2 Console

La consola de administracion para la DB "H2" disponible en la url `http://localhost:8080/h2-ui`

## Implementacion Solucion

## Autenticacion JWT

Se realiza la implementacion de autentuicacion por medio de token
mediante el cual en base a un endpoint expuesto usando POST "http://localhost:8080/auth/login?username="Juan Carlos" "
se realiza el consumo de este mismo haciendo uso de un parametro "username" : "nombre de usuario"

![img.png](img.png)

Mediante el cual nos permite el acceso a los demas recursos , con una configuracion
de 24H

![img_2.png](img_2.png)

## Diseño Arquitectura Solucion

![img_1.png](img_1.png)

## Diagrama BD

![Diagrama BD.png](Diagrama%20BD.png)

## Test Aplicacion

## Consumo Token JWT

![img_4.png](img_4.png)

## Persistencia Cliente

![img_3.png](img_3.png)

## Persistencia Telefonos

![img_6.png](img_6.png)

## Creacion De Cliente

![img_5.png](img_5.png)

## Consulta Cliente Por ID

![img_7.png](img_7.png)

## Eliminacion De Cliente Por ID

![img_8.png](img_8.png)

## Actualizacion De Cliente

![img_9.png](img_9.png)

##Test Unitario Con Coverage



