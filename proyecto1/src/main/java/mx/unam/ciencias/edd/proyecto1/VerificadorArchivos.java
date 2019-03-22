package mx.unam.ciencias.edd.proyecto1;

import java.lang.SecurityException;
import java.io.IOException;
import java.io.File;

public class VerificadorArchivos {

	public static void verificaArchivo_Leer(File archivo) {
		try {
			if (!archivo.exists()){
				System.err.printf("No existe:%3s\n",archivo.getName());
	   			System.exit(1);
			}

			if (archivo.isDirectory()) {
				System.err.printf("No se puede leer:%3s: Es un directorio\n",archivo.getName());
	   			System.exit(1);
			}

			if (!archivo.isFile()) {
				System.err.printf("No se encontro archivo:%3s\n",archivo.getName());
	   			System.exit(1);
	   		}

	   		if (!archivo.canRead()) {
	   			System.err.printf("No se puede leer el archivo:%3s\n",archivo.getName());
	   			System.exit(1);
	   		}
	   	} catch (SecurityException se) {
	   		System.err.printf("No se puede acceder al archivo:%3s",archivo.getName());
	   		System.exit(1);
	   	}
   	}

   	public static void verificaArchivo_Escribir(File archivo) {
   		try {
   			if (archivo.exists()) {
   				if (archivo.isDirectory()) {
					System.err.printf("No se puede escribir:%3s: Es un directorio\n",archivo.getName());
		   			System.exit(1);
				}

   				if (!archivo.canWrite()) {
	   				System.err.printf("No se puede escribir en el archivo:%3s\n",archivo.getName());
	   				System.exit(1);
	   			}
	   		}

	   		if (archivo.isDirectory()) {
				System.err.printf("No se puede escribir:%3s: Es un directorio\n",archivo.getName());
		   		System.exit(1);
			}

	   		archivo.createNewFile();
	   	} catch (SecurityException se) {
	   		System.err.printf("No se puede acceder al archivo:%3s",archivo.getName());
	   		System.exit(1);
	   	} catch (IOException e) {
	   		System.err.printf("%3s: No existe el archivo o el directorio\n",archivo.getName());
	   		System.exit(1);
	   	}
   	}
}