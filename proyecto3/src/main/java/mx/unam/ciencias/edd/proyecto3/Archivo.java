package mx.unam.ciencias.edd.proyecto3;

import java.io.File;
import java.text.Normalizer;
import java.util.Iterator;
import mx.unam.ciencias.edd.Arreglos;
import mx.unam.ciencias.edd.Conjunto;
import mx.unam.ciencias.edd.Lista;
/**
 * <p>Clase que asocia archivos de texto con achivos html.</p>
 * <p>A partir de un archivo de texto se crea un archivo html, con la siguiente información:
 * <ol>
 *  	<li>El número de veces que se repite una palabra en el archivo del texto.</li>
 *      <li>Una gráfica de pastel de las palabras más comunes en el archivo y qué
 *		porcentaje del total de palabras ocupan; el resto de las palabras se juntrán
 *		en una sola rebanada.</li>
 *		<li>Una gráfica de barras con la misma información.</li>
 * 		<li>Un árbol rojinegro con las 15 palabras más utilizadas en el archivo, donde el
 *			valor de cada palabra es el número de veces que aparece en el archivo.</li>
 *		<li>Un árbol AVL con la misma información.</li>
 *	</ol>
 *  </p>
 */
public class Archivo {

	/**
	 * Clase que contiene una palabra del Archivo
	 */
	protected class Palabra implements Comparable<Palabra>, DatoGraficable<String>{
		
		/* Palabra en el archivo de texto. */
		public String palabra;

		/* Palabra sin acentos y en minusculas. */
		public String palabraNormalizada;

		/* Número de veces que aparece la palabra en el archivo de texto */
		public int concurrencia;

		/**
		 * Constructor que recibe una palabra del archivo de texto, y el número de veces 
		 * que aparece en el archivo.
		 * @param palabra palabra en el archivo de texto.
		 * @param concurrencia numero de veces que aparece la palabra en el archivo.
		 */
		public Palabra(String palabra, int concurrencia) {
			this.palabra = palabra;
			palabraNormalizada = Normalizer.normalize(palabra,Normalizer.Form.NFKD);
			palabraNormalizada = palabraNormalizada.replaceAll("[^a-zA-Z0-9]", "");
			this.concurrencia = concurrencia;
		}

		/**
	     * Compara un Palabra con otro Palabra.
	     * @param palabra Palabra de la palabra.
	     * @return un valor menor que cero si el Palabra que llama el método
	     *         es menor que el parámetro; cero si son iguales; o mayor que cero
	     *         si es mayor.
	     */
		@Override public int compareTo(Palabra palabra) {return concurrencia - palabra.concurrencia;}

		/**
		 * Devuelve el elemento de un Dato Palabra.
		 * @return elemento del Dato Palabra.
		 */
		@Override public String get() {return palabra;}

		/**
		 * Devuelve la concurrencia del Dato Palabra.
		 * @return concurrencia del Dato Palabra.
		 */
		@Override public int concurrencia() {return concurrencia;}

		/**
		 * Nos dice si un objeto es igual a una Palabra.
		 * @param o objeto.
		 * @return true si su palabra normalizada es igual a la palabra normalizada del objeto
		 * 		   false en otro caso.
		 */
		@Override public boolean equals (Object o) {
			if (o == null || getClass() != o.getClass())
            	return false;
	        @SuppressWarnings("unchecked") Palabra palabra = (Palabra)o;
	        return palabraNormalizada.equals(palabra.palabraNormalizada);
		}

		/**
		 * Devuelve la dispersion del objeto Palabras.
		 * @return dispersion de la palabra normalizada.
		 */
		@Override public int hashCode() {return palabraNormalizada.hashCode();}
	}

	/* Archivo de texto asociado. */
	private File archivoTexto;

	/* Archivo html generao a partir del archivo de texto. */
	private File archivoHTML;

	/* Palabras en el archivo.*/
	private Diccionario<Palabra> palabras;

	/* Número de palabras ene le archivo. */
	private int noPalabras;

	/**
	 * Constructor que recibe un archivo de texto y la direccion en donde se creara su archivoHTML.
	 * @param archivoTexto archivo de texto asociado al Archivo.
	 * @param directorio directorio donde se creara el archivo html.
	 */
	public Archivo(File archivoTexto, File directorio) {
		this.archivoTexto = archivoTexto;
		LectorArchivo lector = new LectorArchivo(archivoTexto);
		palabras = new Diccionario<Palabra,Palabra>();
		Palabra cadena;
		String linea;
		String palabra = "";
		while ((linea = lector.leer()) != null) {
			for (int j = 0; j < linea.length(); j++) {
				if (linea.charAt(j) < 33) {
					if (!palabras.equals("")) {
						cadena = new Palabra(palabra,1);
						if (palabras.contiene(cadena)) {
							cadena.concurrencia	= palabras.get
							palabras.agrega(cadena);
						} else {
							palabras.agrega(cadena,i);
						}
						palabra = "";
					}
					continue;
				}
				palabra += linea.charAt(j);
			}
		}
		archivoHTML = new File(String.format("%s/%s(html).html",directorio.getPath(),archivoTexto.getName()));
		noPalabras = palabras.getElementos();
	}

	private Palabra[] ordenaPalabras() {
		int elementos = palabras.getElementos();
		Palabra[] palabrasOrdenadas = new Palabra[elementos];
		Iterator<Cadena> iterador = palabras.iteradorLlaves();

		Palabra palabra;
		Cadena cadena;

		for (int i = 0; iterador.hasNext(); i++) {
			cadena = iterador.next();
			palabra = new Palabra(cadena.getCadena(), palabras.get(cadena).intValue());
			palabrasOrdenadas[i] = palabra;
		}

		Arreglos.quickSort(palabrasOrdenadas);
		return palabrasOrdenadas;
	}

	/**
	 * Crea un archivo html correspondiente al Archivo con la siguiente informacion:
	 *  <ol>
	 *  	<li>El número de veces que se repite una palabra en el archivo del texto.</li>
	 *      <li>Una gráfica de pastel de las palabras más comunes en el archivo y qué
	 *		porcentaje del total de palabras ocupan; el resto de las palabras se juntrán
	 *		en una sola rebanada.</li>
	 *		<li>Una gráfica de barras con la misma información.</li>
	 * 		<li>Un árbol rojinegro con las 15 palabras más utilizadas en el archivo, donde el
	 *			valor de cada palabra es el número de veces que aparece en el archivo.</li>
	 *		<li>Un árbol AVL con los mismos datos del árbol de arriba.</li>
	 *	</ol>
	 */
	public void CreaHtml() {
		
	}
}