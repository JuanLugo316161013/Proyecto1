package mx.unam.ciencias.edd.proyecto3;

import java.lang.SecurityException;
import java.io.IOException;
import java.io.File;
/**
 * Clase que valida archivos.
 * Verifica si pueden leerse, ecribirse, acceder o crearse archivos.
 */
public class VerificadorArchivo {

	/**
	 * Constructor vacío.
	 */
	private VerificadorArchivo() {}

	/**
	 * Verifica que el archivo cumpla las siguientes condiciones.
	 *   - El archivo es un archivo normal.
	 *   - El archivo existe.
	 *   - Se puede leer del archivo.
	 * @param archivo archivo a verificar.
	 * @return true si es cumple las tres condiciones
	 *         false en otro caso.
	 */
	public static boolean verificaArchivo(File archivo) {
		if (archivo == null)
			return false;
		try{return archivo.exists() && archivo.isFile() && archivo.canRead();}
		catch (SecurityException se) {return false;}
	}
 	
 	/**
	 * Verifica que el archivo cumpla las siguientes condiciones.
	 *   - El archivo es un directorio.
	 *   - El directorio no se puede crear en caso de no existir.
	 *   - Se puede escribir en el directorio.
	 * @param directorio directorio a verificar.
	 * @return true si es cumple las tres condiciones
	 *         false en otro caso.
	 */
	public static boolean verificaDirectorio(File directorio) {
		if (directorio == null)
			return false;
		try {return archivo.exists() && archivo.isDirectory() && archivo.canWriter();}
		catch (SecurityException se) {return false;}
	}
}