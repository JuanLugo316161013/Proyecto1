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
	protected class Palabra implements DatoGraficable<Palabra>{
		
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

	/** Si el archivo esta marcado*/
	protected boolean marcado;

	/** Palabras en el archivo con el numero de veces que esta en el archivo */
	private Lista<Palabra> palabrasContadas;

	/** Graficas asociadas al archivo */
	File arbolAVL, arbolRojinegro, graficaPastel, graficaBarras;

	/**
	 * Constructor que recibe un archivo de texto y la direccion en donde se creara su archivoHTML.
	 * @param archivoTexto archivo de texto asociado al Archivo.
	 * @param directorio directorio donde se creara el archivo html.
	 */
	public Archivo(File archivoTexto, File directorio) {
		this.archivoTexto = archivoTexto;
		LectorArchivo lector = new LectorArchivo(archivoTexto);
		palabras = new Diccionario<Cadena,Integer>();
		palabrasContadas = new Lista<Palabra>();
		Iterator<Cadena> iterador;
		Cadena cadena;
		String linea;
		Integer i;
		while ((linea = lector.leer()) != null) {
			for (String palabra : linea.split("\\P{L}+")) {
				cadena = new Cadena(palabra);
				if (palabras.contiene(cadena)) {
					i = new Integer(palabras.get(cadena).intValue()+1);
					palabras.agrega(cadena,i);
				} else {
					i = new Integer(1);
					palabras.agrega(cadena,i);
				}
			}
		}
		lector.cerrar();
		iterador = palabras.iteradorLlaves();
		int j;
		while (iterador.hasNext()) {
			cadena = iterador.next();
			j = palabras.get(cadena).intValue();
			palabrasContadas.agrega(new Palabra(cadena.getCadena(),j));
			noPalabras += j;
		}
		archivoHTML = new File(String.format("%s/%s(html).html",directorio.getAbsolutePath(),archivoTexto.getName()));
		arbolAVL = new File(String.format("%s/%s_arbolAVL.svg",directorio.getAbsolutePath(),archivoTexto.getName()));
		arbolRojinegro = new File(String.format("%s/%s_arbolRojinegro.svg",directorio.getAbsolutePath(),archivoTexto.getName()));
		graficaPastel = new File(String.format("%s/%s_graficaPastel.svg",directorio.getAbsolutePath(),archivoTexto.getName()));
		graficaBarras = new File(String.format("%s/%s_graficaBarras.svg",directorio.getAbsolutePath(),archivoTexto.getName()));
	}

	/**
	 * Regresa las 15 palabras que más se repiten en el archivo en caso de existir;
	 * @return Las 15 palabras que más se repiten en el archivo.
	 */
	private Palabra[] ordenaPalabras() {
		int elementos = palabras.getElementos();
		Palabra[] palabrasOrdenadas;
		Palabra p;
		if (elementos > 15) 
			palabrasOrdenadas = new Palabra[15];
		else
			palabrasOrdenadas = new Palabra[elementos];

		elementos = palabrasOrdenadas.length;
		for (Palabra palabra : palabrasContadas) {
			for (int i = 0; i < elementos; i++) {
				if (palabrasOrdenadas[i] == null) {
					palabrasOrdenadas[i] = palabra;
				} else {
					if (palabra.frecuencia > palabrasOrdenadas[i].frecuencia) {
						try {
							for (int j = elementos-1; j > i; j++) {
								palabrasOrdenadas[j] = palabrasOrdenadas[j-1];
							}
						} catch (ArrayIndexOutOfBoundsException aiobe) {}
						palabrasOrdenadas[i] = palabra;
					}
				}
			}
		}
		return palabrasOrdenadas;
	}

	private void recorrerArreglo(Palabra[] arreglo, Palabra elemento, int i) {
		Palabra palabra = arreglo[i];
		arreglo[i] = elemento;
		if (i == arreglo.length - 1)
			return;
		recorrerArreglo(arreglo,palabra,i+1);
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
	 * @throws ExcepcionDirectorioInvalido si algun archivo no se pudo crear en el directorio seleccionado.
	 */
	public void creaHtml() {
		if (!VerificadorArchivo.verificaNuevoArchivo(archivoHTML))
			throw new ExcepcionDirectorioInvalido();
		Iterator<Cadena> iterador = palabras.iteradorLlaves();
		Cadena llave;
		Palabra[] palabrasRepetidas = ordenaPalabras();
		creaGraficas(palabrasRepetidas);
		EscritorArchivo escritor = new EscritorArchivo(archivoHTML);
		escritor.escribe("<!DOCTYPE HTML>\n");
		escritor.escribe("<html>\n");
		escritor.escribe("<head>\n");
		escritor.escribe("<meta charset='UTF-8'>\n");
		escritor.escribe(String.format("<title>%s</title>\n",archivoTexto.getName()));
		escritor.escribe("<link rel='stylesheet' href='/home/lugo/Proyectos/proyecto3/resources/archivo.css'>"); 
		escritor.escribe("</head>\n");
		escritor.escribe("<body>\n");
		escritor.escribe("<header>\n");
		escritor.escribe(String.format("<h1>%s</h1>\n",archivoTexto.getName()));
		escritor.escribe("</header>\n");
		escritor.escribe("<h2>Conteo de palabrás en el archivo.</h2>\n");
		escritor.escribe("<ul>\n");
		while (iterador.hasNext()) {
			llave = iterador.next();
			escritor.escribe(String.format("<li>%s : %d</li>\n",llave.getCadena(),palabras.get(llave).intValue()));
		}
		escritor.escribe("</ul>\n");
		escritor.escribe("<figure>\n");
		escritor.escribe(String.format("<img src = '%s' alt='Grafica de pastel con las palabras más comunes en el archivo'\ntitle= 'Grafica de pastel con las palabras más comunes en el archivo '\n",
			graficaPastel.getAbsolutePath()));
		escritor.escribe("<figcaption>Grafica de pastel con las palabras más comunes en el archivo</figcaption>\n");
		escritor.escribe("</figure>\n");
		escritor.escribe("<figure>\n");
		escritor.escribe(String.format("<img src = '%s' alt='Grafica de pastel con las palabras más comunes en el archivo'\ntitle= 'Grafica de pastel con las palabras más comunes en el archivo '\n",
			graficaBarras.getAbsolutePath()));
		escritor.escribe("<figcaption>Grafica de pastel con las palabras más comunes en el archivo</figcaption>\n");
		escritor.escribe("</figure>\n");
		escritor.escribe("<h2>Palabras más frecuentes en el archivo, ordenadas de menor a mayor.</h2>\n");
		escritor.escribe("<ol>\n");
		for (Palabra palabra : palabrasRepetidas)
			escritor.escribe(String.format("<li>%s : %d</li>\n",palabra.palabra, palabra.frecuencia));
		escritor.escribe("</ol>\n");
		escritor.escribe("<figure>\n");
		escritor.escribe(String.format("<img src = '%s' alt='ArbolRojinegro con las 15 palabras más utilizadas en el archivo'\ntitle= 'ArbolRojinegro con las 15 palabras más utilizadas en el archivo '\n",
			arbolRojinegro.getAbsolutePath()));
		escritor.escribe("<figcaption>ArbolRojinegro con las 15 palabras más utilizadas en el archivo</figcaption>\n");
		escritor.escribe("</figure>\n");
		escritor.escribe("<figure>\n");
		escritor.escribe(String.format("<img src = '%s' alt='ArbolAVL con las 15 palabras más utilizadas en el archivo'\ntitle= 'ArbolAVL con las 15 palabras más utilizadas en el archivo '\n",
			arbolAVL.getAbsolutePath()));
		escritor.escribe("<figcaption>ArbolAVL con las 15 palabras más utilizadas en el archivo</figcaption>\n");
		escritor.escribe("</figure>\n");
		escritor.escribe("</body>\n");
		escritor.escribe("</html>");
		escritor.cerrar();
	}

	/**
	 * Crea las graficas correspondientes al archivo.
	 * @param palabrasRepetidas palabras que se van a graficar.
	 * @return true si las graficas se crearon con exito
	 *         false en otro caso.
	 * @throws ExcepcionDirectorioInvalido si algun archivo no se pudo crear en el directorio seleccionado.
	 */
	private void creaGraficas(Palabra[] palabrasRepetidas) {
		if (!VerificadorArchivo.verificaNuevoArchivo(arbolAVL))
			throw new ExcepcionDirectorioInvalido();

		if (!VerificadorArchivo.verificaNuevoArchivo(arbolRojinegro))
			throw new ExcepcionDirectorioInvalido();

		if (!VerificadorArchivo.verificaNuevoArchivo(graficaPastel))
			throw new ExcepcionDirectorioInvalido();

		if (!VerificadorArchivo.verificaNuevoArchivo(graficaBarras))
			throw new ExcepcionDirectorioInvalido();

		Integer[] arbolBalanceado = llenaArbolBalaceado(palabrasRepetidas);
		GraficadoraSVG<Palabra> graficadora = new GraficadoraSVG<Palabra>(palabrasRepetidas,noPalabras);
		GeneradorArbolAVLSVG<Integer> generadorArbolAVLSVG = new GeneradorArbolAVLSVG<Integer>(arbolBalanceado);
		GeneradorArbolRojinegroSVG<Integer> generadorArbolRojinegroSVG = new GeneradorArbolRojinegroSVG<Integer>(arbolBalanceado);
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
		escritor = new EscritorArchivo(graficaPastel);
		escritor.escribe(graficadora.graficaPastel());
		escritor.cerrar();
		escritor = new EscritorArchivo(graficaBarras);
		escritor.escribe(graficadora.graficaBarras());
		escritor.cerrar();
	}

	/**
	 * Devuelve un arreglo ordenado para que el ArbolRojinegro que se forme de dicho arreglo, tenga la menor altura posible
	 * @param palabras elementos en el ArbolRojinegro.
	 */
	private Integer[] llenaArbolBalaceado(Palabra[] palabras) {
		Integer[] arbolRojinegro = null;
		switch (palabras.length) {
			case 10 :
				arbolRojinegro = new Integer[palabras.length]; 
				arbolRojinegro[0] = 3;
				arbolRojinegro[1] = 1;
				arbolRojinegro[2] = 7;
				arbolRojinegro[3] = 0;
				arbolRojinegro[4] = 2;
				arbolRojinegro[5] = 5;
				arbolRojinegro[6] = 8;
				arbolRojinegro[7] = 4;
				arbolRojinegro[8] = 6;
				arbolRojinegro[9] = 9;
			break;

			case 11:
				arbolRojinegro = new Integer[palabras.length];
				arbolRojinegro[0] = 3;
				arbolRojinegro[1] = 1;
				arbolRojinegro[2] = 7;
				arbolRojinegro[3] = 0;
				arbolRojinegro[4] = 2;
				arbolRojinegro[5] = 5;
				arbolRojinegro[6] = 9;
				arbolRojinegro[7] = 4;
				arbolRojinegro[8] = 6;
				arbolRojinegro[9] = 8;
				arbolRojinegro[10] = 10;
			break;

			case 12:
				arbolRojinegro = new Integer[palabras.length]; 
				arbolRojinegro[0] = 7;
				arbolRojinegro[1] = 3;
				arbolRojinegro[2] = 9;
				arbolRojinegro[3] = 1;
				arbolRojinegro[4] = 5;
				arbolRojinegro[5] = 8;
				arbolRojinegro[6] = 10;
				arbolRojinegro[7] = 0;
				arbolRojinegro[8] = 2;
				arbolRojinegro[9] = 4;
				arbolRojinegro[10] = 6;
				arbolRojinegro[11] = 11;
			break;

			case 13: 
				arbolRojinegro = new Integer[palabras.length];
				arbolRojinegro[0] = 7;
				arbolRojinegro[1] = 3;
				arbolRojinegro[2] = 9;
				arbolRojinegro[3] = 1;
				arbolRojinegro[4] = 5;
				arbolRojinegro[5] = 8;
				arbolRojinegro[6] = 11;
				arbolRojinegro[7] = 0;
				arbolRojinegro[8] = 2;
				arbolRojinegro[9] = 4;
				arbolRojinegro[10] = 6;
				arbolRojinegro[11] = 10;
				arbolRojinegro[12] = 12;
			break;
			
			case 14: 
				arbolRojinegro = new Integer[palabras.length];
				arbolRojinegro[0] = 7;
				arbolRojinegro[1] = 3;
				arbolRojinegro[2] = 11;
				arbolRojinegro[3] = 1;
				arbolRojinegro[4] = 5;
				arbolRojinegro[5] = 9;
				arbolRojinegro[6] = 13;
				arbolRojinegro[7] = 0;
				arbolRojinegro[8] = 2;
				arbolRojinegro[9] = 4;
				arbolRojinegro[10] = 6;
				arbolRojinegro[11] = 8;
				arbolRojinegro[12] = 10;
				arbolRojinegro[13] = 12;
				arbolRojinegro[14] = 14;
			break;

			default:
				arbolRojinegro = new Integer[palabras.length];
				for (int i = 0; i < palabras.length; i++)
					arbolRojinegro[i] = i+1;

			break;
		}
		return arbolRojinegro;
	}
}