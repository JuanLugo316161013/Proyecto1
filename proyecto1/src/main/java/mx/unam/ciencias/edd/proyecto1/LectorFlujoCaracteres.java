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
 * Lee y codifica un flujo de bytes en una entrada, en formato 
 * "UTF-8"
 */
public class LectorFlujoCaracteres {

	/** Objeto que permite leer flujo de caracteres en una entrada dada */
	private BufferedReader lector;

	/**
	 * Constructor vacio.
	 * No se puede inicializar un lector de flujo de caracteres
	 * si no hay una entrada valida. 
	 */
	private LectorFlujoCaracteres () {}

	/**
	 * Constructort que recibe un archivo que sera la entrada
	 * leeran las secuancias de caracteres.
	 * @param archivo entrada del lector.
	 * @throws IllegalAccessException si el archivo es null.
	 */
	public LectorFlujoCaracteres(File archivo) {
		if (archivo == null)
			throw new IllegalArgumentException();

		try {
			lector = new BufferedReader(new InputStreamReader
			        (new FileInputStream(archivo), StandardCharsets.UTF_8));
		} 
		catch (FileNotFoundException fnfe) {lector = null;} 
		catch (SecurityException se) {lector = null;}
	}

	/**
	 * Constructor que recibe una entrada donde se leeran las 
	 * secuancias de caracteres.
	 * @param entrada entrada del leector.
	 * @throws IllegalAccessException si la salida es null.
	 */
	public LectorFlujoCaracteres(InputStream entrada) {
		if (entrada == null)
			throw new IllegalArgumentException();

		lector = new BufferedReader(new InputStreamReader(entrada,StandardCharsets.UTF_8));
	} 

	/**
	 * Devuelve un renglon de caracteres de la salida leida por el lector
	 * @return renglon actual donde esta leyendo el lector
	 * @throws NoSuchElementException si lector es null.
	 */
	public String leer() {
		if (lector == null)
			throw new NoSuchElementException();

		try {
			return lector.readLine();
		} catch (IOException ioe) {
			lector = null;
			return null;
		}
	}

	/**
	 * Cierra el fluyo de lectura de caracteres
	 * @throws NoSuchElementException si lector es null.
	 */
	public void cerrar() {
		if (lector == null)
			throw new NoSuchElementException();

		try {lector.close();}
		catch (IOException ioe) {lector = null;}
	}
}