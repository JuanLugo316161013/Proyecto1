package mx.unam.ciencias.edd.proyecto3;

import java.util.NoSuchElementException;
import java.io.File;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Conjunto;
/**
 * <p>Clase que procesa la entrada entra de un programa desde la entrada estandar.</p>
 * <p>Dada la entrada identifica los archivos que se utilizaran en el programa, y el directorio en donde
 *	se crearan los nuevos archivos, lee los archivos y cuenta el numero de palabras que hay en cada archivo
 *  crea una lista de archivos procesados que tienen un diccionario con el numero de palabras, el archivo 
 *  de texto correspondiente y el archivo html al que esta 'enlazado'</p>
 */
public class ProcesadorEntrada {

	/** Lista de archivos especificados en la entrada estandar. */
	private Conjunto<File> archivos;

	/** Lista de archivos procesados*/
	private Lista<Archivo> archivosProcesados;

	/** Directorio selecionado. */
	private File directorio;

	/**
	 * Constructor vacío.
	 */
	private ProcesadorEntrada() {}

	/**
	 * Constructor que recibe los argumentos del metodo main
	 * @param args argumentos del metodo main.
	 * @throws IllegalArgumentException si los argumentos no son suficientes.
	 */
	public ProcesadorEntrada(String[] args) {
		if (args.length < 1)
			throw new IllegalArgumentException();
		
		Argumentos argumentos = new Argumentos(args);
		archivos = argumentos.archivos();
		directorio = argumentos.directorio();
		procesaArchivos(directorio);
	}

	/**
	 * Procesa una lista de archivos.
	 * @throws NoSuchElementException si el archivo no existe.
	 */
	private void procesaArchivos(File directorio) {
		int i = 1;
		archivosProcesados = new Lista<Archivo>();
		for (File archivo : archivos)
			archivosProcesados.agrega(new Archivo(archivo,directorio,i++));
		// Limpia el directorio.
		File[] archivosDirectorio = directorio.listFiles();
		for (File archivo : archivosDirectorio)
			archivo.delete();
	}

	/**
	 * Regresa una lista con la entrada procesada.
	 * @return lista de elementos en la entrada.
	 * @throws NoSuchElementException si la lista es vacía;
	 */
	public Lista<Archivo> archivosProcesados() {
		return archivosProcesados;
	}

	/**
     * Regresa el directorio.
     * @return directorio.
     */
	public File directorio() {return directorio;}
}
