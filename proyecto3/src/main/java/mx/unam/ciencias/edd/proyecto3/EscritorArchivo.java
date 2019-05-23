package mx.unam.ciencias.edd.proyecto3;

import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.IOException;
import java.io.File;
import java.lang.SecurityException;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

/**
 * Codifica y escribe cadenas de caracteres en un archivo en "UTF-8"
 */
public class EscritorArchivo {

	/** Objeto que escribe secuencias de caracteres en una salidad dada*/
	BufferedWriter escritor;

	/**
	 * Constructor vacio.
	 * No se puede iniciar un escritor de flujo de caracteres
	 * si no hay una salida valida.
	 */
	private EscritorArchivo() {}

	/**
	 * Constructor que recibe un archivo, que sera la salida donde
	 * se escribiran las secuencias de caracteres.
	 * @param archivo salida del escritor
	 * @throws IllegalArgumentException si el archivo es null.
	 */
	public EscritorArchivo(File archivo) {
		if (archivo == null)
			throw new IllegalArgumentException();

		try {
			escritor = new BufferedWriter(new OutputStreamWriter
			          (new FileOutputStream(archivo,true),StandardCharsets.UTF_8));
		} 
		catch (FileNotFoundException fnfe) {escritor = null;} 
		catch (SecurityException se) {escritor = null;}
	}

	/**
	 * Constructor que recibe una salida donde se escribiran
	 * las secuencias de caracteres.
	 * @param salida salida del escritor. 
	 * @throws IllegalArgumentException si la salida es null.
	 */
	public EscritorArchivo(OutputStream salida) {
		if (salida == null)
			throw new IllegalArgumentException();

		escritor = new BufferedWriter(new OutputStreamWriter(salida,StandardCharsets.UTF_8));
	} 

	/**
	 * Escribe una secuencia de caracteres en una salida dada
	 * si no hay una salida valida no se realizara ninguna accion.
	 * @param cadena cadena de caracteres a escribir.
	 * @throws NoSuchElementException si el escritor es null.
	 */
	public void escribe(String cadena) {
		if (escritor == null)
			throw new NoSuchElementException();

		try {
			escritor.write(cadena);
			escritor.newLine();
		} catch(IOException ioe) {escritor = null;}
	}

	/**
	 * Cierra el fluyo de salida, para liberar recuersos.
	 */
	public void cerrar() {
		if (escritor == null)
			return;

		try {escritor.close();} 
		catch (IOException ioe) {escritor = null;}
	}
}
