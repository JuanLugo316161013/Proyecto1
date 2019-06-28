package mx.unam.ciencias.edd.proyecto1;

import java.util.NoSuchElementException;
import java.io.File;
import mx.unam.ciencias.edd.Lista;
/**
 * Determina de si hay que leer de la entrada estandar o de una Lista de archivos.
 */
public class ProcesadorEntrada {

	/* Si modo es igual a True se tiene que leer una lista de archivos */ 
	private boolean modo;

	/* Argumentos del programa. */
	private Argumentos argumentos; 
	/**
	 * Constructor vacío.
	 */
	private ProcesadorEntrada() {}

	/**
	 * Constructor que recibe los argumentos del metodo main
	 * @param args argumentos del metodo main.
	 */
	public ProcesadorEntrada(String[] args) {
		argumentos = new Argumentos(args);

		if (args.length < 1 || argumentos.archivos() == null)
			modo = false;
		else
			modo = true;
	}

	/**
	 * Nos dice si hay que leer de la entrada estandar o de una lista de archivos.
	 * @return <code>true</code> si hay que leer de una lista de archivos
	 *         <code>false</code> si hay que leer de la entrada estandar.
	 */
	public boolean metodo() {return modo;}

	/**
	 * Regresa una instancia de {@link Argumentos}.
	 * @return una instancia de {@link Argumentos}.
	 */
	public Argumentos argumentos(){return argumentos;}
}