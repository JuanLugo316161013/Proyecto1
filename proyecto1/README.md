Ordenador lexicográfico
=======================
Ordena lexicográficamente una lista de archivos ó la entrada recibida en la entrada estandar.

Ejecutar
--------
Para ejecutar el programa se tiene que ejecutar el archvio jar [proyecto1.jar](https://github.com/JuanLugoN/Proyectos/tree/master/proyecto1/target/proyecto1.jar), el programa recibe el texto a ordenar a traves de una lista de archivos desde los argumentos del programa ó de la entrada estandar.

### Ejemplo de como ejecutar el programa cuando recibe su entrada atravez de los argumentos del programa.
```
cd proyecto1/
java -jar target/proyecto1.jar archivo1.txt archivo2.txt ...
```

### Ejemplo de como ejecutar el programa cuando recibe su entrada de la entrada estandar.
```
cd proyecto1/
cat .../archivo.txt | java -jar target/proyecto1.jar
```

Banderas
--------
El programa puede recibir las banderas `-r` y `-o` atravez de los argumentos del programa.
* -r imprime las lineas en orden inverso.
* -o tiene que ir seguida de un indentificador que sera el archivo donde se escribira la salida del programa.

Documentación
-------------
Documentacion generada por JavaDoc.
[Documentación del primer proyecto de E.D.D.](https://github.com/JuanLugoN/Proyectos/tree/master/proyecto1/target/site/apidocs/index.html).
