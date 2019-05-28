package mx.unam.ciencias.edd.proyecto3;

import java.lang.SecurityException;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
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
		try{
			if (!archivo.exists())
				throw new FileNotFoundException();
			return archivo.isFile() && archivo.canRead();}
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
		try {
			directorio = new File(directorio.getCanonicalPath());
			if (directorio.exists())
				directorio.delete();

			return directorio.mkdirs() && directorio.isDirectory() && directorio.canWrite();
		} catch (SecurityException se) {return false;}
	}

	/**
	 * Verifica que exista un nuevo archivo, si no existe verifica si puede crearlo.
	 * @param archivo a verificar.
	 * @return true si el archivo existe o se puede crear 
	 *         false en otro caso.
	 */
	public static boolean verificaNuevoArchivo (File archivo) {
		try {
			return archivo.exists() || archivo.createNewFile();
		} catch(SecurityException se) {return false;}
		catch (IOException io) {return false;}
	}
}