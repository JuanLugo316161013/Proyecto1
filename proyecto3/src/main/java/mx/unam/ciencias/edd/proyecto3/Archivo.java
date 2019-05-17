package mx.unam.ciencias.edd.proyecto3;

import java.io.File;
import java.util.Iterator;
import mx.unam.ciencias.edd.Arreglos;
import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.Lista;
/**
 * <p>Clase que asocia archivos de texto con achivos html.</p>
 * <p>A partir de un archivo de texto se crea un archivo html, con la siguiente información:
 * <ol>
 *  	<li>El número de veces que se repite una palabra en el archivo del texto.</li>
 * 		<li>Un árbol rojinegro con las 15 palabras más utilizadas en el archivo, donde el
 *			valor de cada palabra es el número de veces que aparece en el archivo.</li>
 *		<li>Un árbol AVL con los mismos datos del árbol de arriba.</li>
 *	</ol>
 *  </p>
 */
public class Archivo {

	/**
	 * Clase que asigna valor a una palabra.
	 */
	protected class ValorPalabra implements Comparable<ValorPalabra>{
		/* Palabra en el archivo de texto. */
		public String palabra;

		/* Número de veces que aparece la palabra en el archivo de texto */
		public int valor;

		/**
		 * Constructor que recibe una palabra del archivo de texto, y el número de veces 
		 * que aparece en el archivo.
		 * @param palabra palabra en el archivo de texto.
		 * @param valor numero de veces que aparece la palabra en el archivo.
		 */
		public ValorPalabra(String palabra, int valor) {
			this.palabra = palabra;
			this.valor = valor;
		}

		@Override public int compareTo(ValorPalabra palabra) {
			return valor - palabra.valor;
		}
	}

	/* Archivo de texto asociado. */
	private File archivoTexto;

	/* Archivo html generao a partir del archivo de texto. */
	private File archivoHTML;

	/* Palabras en el archivo.*/
	private Diccionario<Cadena,Integer> palabras;

	/* Número de palabras ene le archivo. */
	private int noPalabras;

	/**
	 * Constructor que recibe un archivo de texto, su archivo html asociado, y un
	 * diccionario con las palabras y el numero de parabras
	 */
	public Archivo(File archivoTexto, File archivoHTML, Diccionario<Cadena,Integer> palabras) {
		this.archivoTexto = archivoTexto;
		this.archivoHTML = archivoHTML;
		this.palabras = palabras;
		for (Integer i : palabras)
			noPalabras += i.intValue();
	}

	private ValorPalabra[] ordenaPalabras() {
		int elementos = palabras.getElementos();
		if (elementos < 15)
			elementos = 15;
		ValorPalabra[] palabrasOrdenadas = new ValorPalabra[elementos];
		Iterator<Cadena> iterador = palabras.iteradorLlaves();

		ValorPalabra palabra;
		Cadena cadena;
		for (int i = 0; iterador.hasNext(); i++) {
			cadena = iterador.next();
			palabra = new ValorPalabra(cadena.getCadena(), palabras.get(cadena).intValue());
			palabrasOrdenadas[i] = palabra;
		}

		for (int i = 0; i < palabrasOrdenadas.length; i++)
			if (palabrasOrdenadas[i] == null)
				palabrasOrdenadas[i] = new ValorPalabra("ø",0);

		Arreglos.quickSort(palabrasOrdenadas);
		return palabrasOrdenadas;
	}

	public String[] toString() {
		return null;
	}
}