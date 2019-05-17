package mx.unam.ciencias.edd.proyecto3;

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
	 * @throws IllegalAccessException si el archivo es null.
	 */
	public LectorArchivo(File archivo) {
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
	 * Constructor que recibe un archivo InputStream para leer.
	 * @param entrada archivo InputStream.
	 * @throws IllegalAccessException si la salida es null.
	 */
	public LectorArchivo(InputStream entrada) {
		if (entrada == null)
			throw new IllegalArgumentException();

		lector = new BufferedReader(new InputStreamReader(entrada,StandardCharsets.UTF_8));
	} 

	/**
	 * Nos dice si el archivo esta listo para leerse.
	 * @return <code>true</code> si esta listo,
	 *		   <code>false</code> si no lo esta.
	 */
	public boolean estaListo() {
		try {return lector.ready();} 
		catch (IOException ioe) {return false;}
	}

	/**
	 * Devuelve una cadena que es el renglon del archivo.
	 * @return renglon actual del archivo.
	 * @throws NoSuchElementException si lector es null.
	 */
	public String leer() {
		if (lector == null)
			throw new NoSuchElementException();

		try {return lector.readLine();} 
		catch (IOException ioe) {lector = null; return null;}
	}

	/**
	 * Cierra el archivo, y el lecto deja de leer de dicho archivo.
	 * @throws NoSuchElementException si lector es null.
	 */
	public void cerrar() {
		if (lector == null)
			throw new NoSuchElementException();

		try {lector.close();}
		catch (IOException ioe) {lector = null;}
	}
}