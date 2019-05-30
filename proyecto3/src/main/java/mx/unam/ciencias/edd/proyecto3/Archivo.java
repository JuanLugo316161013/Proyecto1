package mx.unam.ciencias.edd.proyecto3;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import mx.unam.ciencias.edd.Arreglos;
import mx.unam.ciencias.edd.Diccionario;
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
	 * Clase que contiene una palabra del Archivo, y el número de veces que aparece en el Archivo.
	 */
	protected class Palabra implements Comparable<Palabra>, DatoGraficable<Palabra>{
		
		/* Palabra en el archivo de texto. */
		public String palabra;

		/* Número de veces que aparece la palabra en el archivo de texto */
		public int frecuencia;

		/**
		 * Constructor que recibe una palabra del archivo de texto, y el número de veces 
		 * que aparece en el archivo.
		 * @param palabra palabra en el archivo de texto.
		 * @param frecuencia numero de veces que aparece la palabra en el archivo.
		 */
		public Palabra(String palabra, int frecuencia) {
			this.palabra = palabra;
			this.frecuencia = frecuencia;
		}

		/**
	     * Compara un Palabra con otro Palabra.
	     * @param palabra Palabra de la palabra.
	     * @return un valor menor que cero si el Palabra que llama el método
	     *         es menor que el parámetro; cero si son iguales; o mayor que cero
	     *         si es mayor.
	     */
		@Override public int compareTo(Palabra palabra) {return frecuencia - palabra.frecuencia;}

		/**
		 * Devuelve el elemento de un Dato Palabra.
		 * @return elemento del Dato Palabra.
		 */
		@Override public String get() {return palabra;}

		/**
		 * Devuelve el número de veces que aparece la palabra en el Archivo.
		 * @return número de veces que aparece la palabra en el Archivo.
		 */
		@Override public int frecuencia() {return frecuencia;}
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
	 * Constructor que recibe un archivo de texto y la direccion en donde se creara su archivoHTML.
	 * @param archivoTexto archivo de texto asociado al Archivo.
	 * @param directorio directorio donde se creara el archivo html.
	 */
	public Archivo(File archivoTexto, File directorio) {
		this.archivoTexto = archivoTexto;
		LectorArchivo lector = new LectorArchivo(archivoTexto);
		palabras = new Diccionario<Cadena,Integer>();
		Cadena cadena;
		String linea;
		String palabra = "";
		Integer i;
		while ((linea = lector.leer()) != null) {
			for (int j = 0; j < linea.length(); j++) {
				if (linea.charAt(j) < 33) {
					if (!palabras.equals("")) {
						cadena = new Cadena(palabra);
						if (palabras.contiene(cadena)) {
							i = new Integer(palabras.get(cadena).intValue());
							palabras.agrega(cadena,i);
						} else {
							i = new Integer(1);
							palabras.agrega(cadena,i);
						}
						palabra = "";
					}
					continue;
				}
				palabra += linea.charAt(j);
			}
		}
		lector.cerrar();
		archivoHTML = new File(String.format("%s/%s(html).html",directorio.getAbsolutePath(),archivoTexto.getName()));
		noPalabras = palabras.getElementos();
	}

	/**
	 * Regresa las 15 palaras que más se repiten en el archivo en caso de existir;
	 * @return Las 15 palabras que más se repiten en el archivo.
	 */
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

		if (elementos > 15) {
			Palabra[] aux = new Palabra[15];
			int k = 0;
			for (int j = elementos - 1; j > (elementos-16); j--)
				aux[k++] = palabrasOrdenadas[j];
			palabrasOrdenadas = aux;
		}


		Arreglos.quickSort(palabrasOrdenadas);
		return palabrasOrdenadas;
	}

	/**
	 * Nos dice si un archivo tiene una cadena (palabra).
	 * @param cadena cadena a buscar en el archivo.
	 * @return true si el archivo contiene la cadena, 
	 *         false en otro caso.
	 */
	public boolean contiene(Cadena cadena) {
		return palabras.contiene(cadena);
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
	public void creaHtml() {
		if (!VerificadorArchivo.verificaNuevoArchivo(archivoHTML))
			throw new ExcepcionDirectorioInvalido();

		Palabra[] palabrasRepetidas = ordenaPalabras();
		creaGraficas(palabrasRepetidas);
		
	}

	/**
	 * Crea las graficas correspondientes al archivo.
	 * @param palabrasRepetidas palabras que se van a graficar.
	 * @return true si las graficas se crearon con exito
	 *         false en otro caso.
	 */
	private void creaGraficas(Palabra[] palabrasRepetidas) {
		File arbolAVL = new File(String.format("%s/%s_arbolAVL.svg",archivoHTML.getAbsolutePath(),archivoTexto.getName()));
		File arbolRojinegro = new File(String.format("%s/%s_arbolRojinegro.svg",archivoHTML.getAbsolutePath(),archivoTexto.getName()));
		File graficaPastel = new File(String.format("%s/%s_graficaPastel.svg",archivoHTML.getAbsolutePath(),archivoTexto.getName()));
		File graficaBarras = new File(String.format("%s/%s_graficaBarras.svg",archivoHTML.getAbsolutePath(),archivoTexto.getName()));
		
		if (!VerificadorArchivo.verificaNuevoArchivo(arbolAVL))
			throw new ExcepcionDirectorioInvalido();

		if (!VerificadorArchivo.verificaNuevoArchivo(arbolRojinegro))
			throw new ExcepcionDirectorioInvalido();

		if (!VerificadorArchivo.verificaNuevoArchivo(graficaPastel))
			throw new ExcepcionDirectorioInvalido();

		if (!VerificadorArchivo.verificaNuevoArchivo(graficaBarras))
			throw new ExcepcionDirectorioInvalido();

		GeneradorArbolAVLSVG<Palabra> generadorArbolAVLSVG = new GeneradorArbolAVLSVG<Palabra>(palabrasRepetidas);
		GeneradorArbolRojinegroSVG<Palabra> generadorArbolRojinegroSVG = new GeneradorArbolRojinegroSVG<Palabra>(llenaArbolRojonegro(palabrasRepetidas));
		EscritorArchivo escritor = new EscritorArchivo(arbolAVL);
		Lista<String> codigoSVG = generadorArbolAVLSVG.codigoSVG();
		for (String codigo : codigoSVG)
			escritor.escribe(codigo);
		escritor.cerrar();
		escritor = new EscritorArchivo(arbolRojinegro);
		codigoSVG = generadorArbolRojinegroSVG.codigoSVG();
		for (String codigo : codigoSVG)
			escritor.escribe(codigo);
		escritor.cerrar();
	}

	/**
	 * Devuelve un arreglo ordenado para que el ArbolRojinegro que se forme de dicho arreglo, tenga la menor altura posible
	 * @param palabras elementos en el ArbolRojinegro.
	 */
	private Palabra[] llenaArbolRojonegro(Palabra[] palabras) {
		Palabra[] arbolRojinegro = null;
		switch (arbolRojinegro.length) {
			case 10 :
				arbolRojinegro = new Palabra[palabras.length]; 
				arbolRojinegro[0] = palabras[3];
				arbolRojinegro[1] = palabras[1];
				arbolRojinegro[2] = palabras[7];
				arbolRojinegro[3] = palabras[0];
				arbolRojinegro[4] = palabras[2];
				arbolRojinegro[5] = palabras[5];
				arbolRojinegro[6] = palabras[8];
				arbolRojinegro[7] = palabras[4];
				arbolRojinegro[8] = palabras[6];
				arbolRojinegro[9] = palabras[9];
			break;

			case 11:
				arbolRojinegro = new Palabra[palabras.length];
				arbolRojinegro[0] = palabras[3];
				arbolRojinegro[1] = palabras[1];
				arbolRojinegro[2] = palabras[7];
				arbolRojinegro[3] = palabras[0];
				arbolRojinegro[4] = palabras[2];
				arbolRojinegro[5] = palabras[5];
				arbolRojinegro[6] = palabras[9];
				arbolRojinegro[7] = palabras[4];
				arbolRojinegro[8] = palabras[6];
				arbolRojinegro[9] = palabras[8];
				arbolRojinegro[10] = palabras[10];
			break;

			case 12:
				arbolRojinegro = new Palabra[palabras.length]; 
				arbolRojinegro[0] = palabras[7];
				arbolRojinegro[1] = palabras[3];
				arbolRojinegro[2] = palabras[9];
				arbolRojinegro[3] = palabras[1];
				arbolRojinegro[4] = palabras[5];
				arbolRojinegro[5] = palabras[8];
				arbolRojinegro[6] = palabras[10];
				arbolRojinegro[7] = palabras[0];
				arbolRojinegro[8] = palabras[2];
				arbolRojinegro[9] = palabras[4];
				arbolRojinegro[10] = palabras[6];
				arbolRojinegro[11] = palabras[11];
			break;

			case 13: 
				arbolRojinegro = new Palabra[palabras.length];
				arbolRojinegro[0] = palabras[7];
				arbolRojinegro[1] = palabras[3];
				arbolRojinegro[2] = palabras[9];
				arbolRojinegro[3] = palabras[1];
				arbolRojinegro[4] = palabras[5];
				arbolRojinegro[5] = palabras[8];
				arbolRojinegro[6] = palabras[11];
				arbolRojinegro[7] = palabras[0];
				arbolRojinegro[8] = palabras[2];
				arbolRojinegro[9] = palabras[4];
				arbolRojinegro[10] = palabras[6];
				arbolRojinegro[11] = palabras[10];
				arbolRojinegro[12] = palabras[12];
			break;
			case 14: 
				arbolRojinegro = new Palabra[palabras.length];
				arbolRojinegro[0] = palabras[7];
				arbolRojinegro[1] = palabras[3];
				arbolRojinegro[2] = palabras[11];
				arbolRojinegro[3] = palabras[1];
				arbolRojinegro[4] = palabras[5];
				arbolRojinegro[5] = palabras[9];
				arbolRojinegro[6] = palabras[13];
				arbolRojinegro[7] = palabras[0];
				arbolRojinegro[8] = palabras[2];
				arbolRojinegro[9] = palabras[4];
				arbolRojinegro[10] = palabras[6];
				arbolRojinegro[11] = palabras[8];
				arbolRojinegro[12] = palabras[10];
				arbolRojinegro[13] = palabras[12];
				arbolRojinegro[14] = palabras[14];
			break;

			default:
				arbolRojinegro = palabras;
			break;
		}
		return arbolRojinegro;
	} 
}