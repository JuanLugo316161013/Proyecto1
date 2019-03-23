package mx.unam.ciencias.edd.proyecto1;

import java.lang.SecurityException;
import java.io.IOException;
import java.io.File;
/**
 * Clase que valida archvivos.
 * Verifica si pueden leerse, ecribirse, o crearse archivos.
 */
public class VerificadorArchivos {

	/**
	 * Verifica si se puede leer de un archivo dado.
	 * Si no se puede termina el programa en el que se este ejecutando termina.
	 * @param archivo archivo a verificar.
	 */
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

   	/**
	 * Verifica si se puede escribir o crear un archvo para escribir,
	 * Si no se puede termina el programa en el que se este ejecutando termina.
	 * @param archivo archivo a verificar.
	 */
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

	   			archivo.delete();
	   		}

	   		if (archivo.isDirectory()) {
				System.err.printf("No se puede escribir:%3s: Es un directorio.\n",archivo.getName());
		   		System.exit(1);
			}

	   	} catch (SecurityException se) {
	   		System.err.printf("No se puede acceder al archivo:%3s\n",archivo.getName());
	   		System.exit(1);
	   	}
   	}
   	/**
   	 * Verifica si se puede crear un archivo.
   	 * Si no se puede termina el programa en el que se este ejecutando termina.
   	 * @param archivo archivo a verificar.
   	 */
   	public void verificaArchivo_Crear(File archivo) {
   		try {
   			if (archivo.exists()) {
   				System.err.printf("El archivo:%3s ya existe.\n",archivo.getName());
	   			System.exit(1);
   			}

   			if (!archivo.createNewFile()) {
	   			System.err.printf("No se puede crear el archivo:%3s",archivo.getName());
	   			System.exit(1);
	   		}

	   		archivo.delete();

	   	} catch (SecurityException se) {
	   		System.err.printf("No se puede acceder al archivo:%3s\n",archivo.getName());
	   		System.exit(1);
	   	} catch (IOException ioe) { 
	   		System.err.printf("Error al verificar el archivo:%3s\n",archivo.getName());
	   		System.exit(1);
	   	}
   	}
}