package mx.unam.ciencias.edd.proyecto1;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import java.lang.SecurityException;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

/**
 * Lee y codifica la entrada ya leida en "UTF-8"
 */
public class LectorArchivo {

	/** Lector de archivos */
	private BufferedReader lector;
	
	/**
	 * Constructor vacio.
	 */
	private LectorArchivo () {}

	/**
	 * Constructor que recibe un archivo File para leer.
	 * @param archivo archivo File.
	 * @throws IllegalArgumentException si el archivo es null.
	 */
	public LectorArchivo(File archivo) {
		try {
			lector = new BufferedReader(new InputStreamReader
			    	(new FileInputStream(archivo), StandardCharsets.UTF_8));
		} catch (FileNotFoundException fne) {
			System.err.printf("Error en la lectura de archivos.");
			System.exit(1);
		}
	}

	/**
	 * Constructor que recibe un archivo InputStream para leer.
	 * @param entrada archivo InputStream.
	 * @throws IllegalArgumentException si la entrada es null.
	 */
	public LectorArchivo(InputStream entrada) {
		lector = new BufferedReader(new InputStreamReader(entrada,StandardCharsets.UTF_8));
	} 
	
	/**
	 * Devuelve un objeto {@link Cadena}
	 * @return renglon actual del archivo.
	 * @throws NoSuchElementException si lector es null.
	 */
	public Cadena leer() {
		try { 
			String linea = lector.readLine();
			return linea == null ? null : new Cadena(linea); 
		} catch (IOException ioe) {
			System.err.printf("Error en la lectura de archivos.");
			System.exit(1);
		}
		return null;
	}

	/**
	 * Cierra el archivo, y el flujo de entrada del archivo actual.
	 * @throws NoSuchElementException si lector es null.
	 */
	public void cerrar() {
		try {lector.close();}
		catch (IOException ioe) {
			System.err.printf("Error en la lectura de archivos.");
			System.exit(1);
		}
	}
}