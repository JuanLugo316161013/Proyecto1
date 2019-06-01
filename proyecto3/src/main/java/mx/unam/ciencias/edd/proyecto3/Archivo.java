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
public class Archivo implements Iterable<Cadena> {

	private class Iterador implements Iterator<Cadena> {
		Iterator<Cadena> iterador;

		/* Construye un nuevo iterador */
		public Iterador() {
			iterador = palabras.iteradorLlaves();
		}

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override public Cadena next() {
            // Aquí va su código.
            return iterador.next();
        }

	}

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
		 * Devuelve el elemento de un Dato Palabra.
		 * @return elemento del Dato Palabra.
		 */
		@Override public String get() {return palabra;}

		/**
		 * Devuelve el número de veces que aparece la palabra en el Archivo.
		 * @return número de veces que aparece la palabra en el Archivo.
		 */
		@Override public int frecuencia() {return frecuencia;}

		/**
		 * Compara dos palabras segun el numero de repeticiones en el archivo.
		 * @param palabra palabra a comparar.
		 * @return número mayor a cero si su frecuencias es mayor a la del objeto
		 *         número menor a cero si su frecuencia es menor, cero si son iguales.
		 */
		@Override public int compareTo(Palabra palabra) {return frecuencia - palabra.frecuencia;}
	}

	/* Archivo de texto asociado. */
	private File archivoTexto;

	/* Archivo html generao a partir del archivo de texto. */
	private File archivoHTML;

	/* Palabras en el archivo.*/
	private Diccionario<Cadena,Integer> palabras;

	/* Número de palabras ene le archivo. */
	private int noPalabras;

	/** Palabras en el archivo con el numero de veces que esta en el archivo */
	private Lista<Palabra> palabrasContadas;

	/** Graficas asociadas al archivo */
	private File arbolAVL, arbolRojinegro, graficaPastel, graficaBarras, css;

	/** numero de archivo */
	private int numeroArchivo;

	/**
	 * Constructor que recibe un archivo de texto y la direccion en donde se creara su archivoHTML.
	 * @param archivoTexto archivo de texto asociado al Archivo.
	 * @param directorio directorio donde se creara el archivo html.
	 */
	public Archivo(File archivoTexto, File directorio, int numeroArchivo) {
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
				if (palabra.isEmpty())
					continue;
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
		css = new File(String.format("%s/archivo.css",directorio.getAbsolutePath()));
		archivoHTML = new File(String.format("%s/%s(html).html",directorio.getAbsolutePath(),archivoTexto.getName()));
		arbolAVL = new File(String.format("%s/%s_arbolAVL.svg",directorio.getAbsolutePath(),archivoTexto.getName()));
		arbolRojinegro = new File(String.format("%s/%s_arbolRojinegro.svg",directorio.getAbsolutePath(),archivoTexto.getName()));
		graficaPastel = new File(String.format("%s/%s_graficaPastel.svg",directorio.getAbsolutePath(),archivoTexto.getName()));
		graficaBarras = new File(String.format("%s/%s_graficaBarras.svg",directorio.getAbsolutePath(),archivoTexto.getName()));
		this.numeroArchivo = numeroArchivo;
	}

	/**
	 * Regresa las 15 palabras que más se repiten en el archivo en caso de existir;
	 * @return Las 15 palabras que más se repiten en el archivo.
	 */
	private Palabra[] ordenaPalabras() {
		int elementos = palabrasContadas.getElementos();
		Palabra[] palabrasOrdenadas;
		if (elementos > 15) 
			palabrasOrdenadas = new Palabra[15];
		else
			palabrasOrdenadas = new Palabra[elementos];
		/* AUNQUE SE APLIQUE MERGESORT AUN SE MANTIENE LA COMPLEJIDAD EN EL CONTEO DE PALABRAS YA QUE SE LE APLICA A UNA
		   LISTA QUE CONTIENE LAS PALABRAS Y LAS VECES QUE APARECE EN EL ARCHIVO */
		Lista<Palabra> palabrasRepetidas = Lista.mergeSort(palabrasContadas).reversa();
		int i = 0;
		elementos = palabrasOrdenadas.length;
		for (Palabra palabra : palabrasRepetidas)
			if (i < elementos)
				palabrasOrdenadas[i++] = palabra;

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
		escritor.escribe(String.format("<link rel='stylesheet' href='%s'>",css.getAbsolutePath())); 
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
		escritor.escribe(String.format("<img src = '%s' alt='Grafica de pastel con las palabras más comunes en el archivo'\ntitle= 'Grafica de pastel con las palabras más comunes en el archivo '>\n",
			graficaPastel.getAbsolutePath()));
		escritor.escribe("<figcaption>Grafica de pastel con las palabras más comunes en el archivo</figcaption>\n");
		escritor.escribe("</figure>\n");
		escritor.escribe("<figure>\n");
		escritor.escribe(String.format("<img src = '%s' %s alt='Grafica de barras con las palabras más comunes en el archivo'\ntitle= 'Grafica de barras con las palabras más comunes en el archivo '>\n",
			graficaBarras.getAbsolutePath(),palabrasRepetidas.length >= 13 ? "id = \"barras\" " : ""));
		escritor.escribe("<figcaption>Grafica de barras con las palabras más comunes en el archivo</figcaption>\n");
		escritor.escribe("</figure>\n");
		escritor.escribe("<h2>Palabras más frecuentes en el archivo, ordenadas de menor a mayor.</h2>\n");
		escritor.escribe("<ol>\n");
		for (Palabra palabra : palabrasRepetidas)
			escritor.escribe(String.format("<li>%s : %d</li>\n",palabra.palabra, palabra.frecuencia));
		escritor.escribe("</ol>\n");
		escritor.escribe("<figure>\n");
		escritor.escribe(String.format("<img src = '%s' alt='ArbolRojinegro con las 15 palabras más utilizadas en el archivo'\ntitle= 'ArbolRojinegro con las 15 palabras más utilizadas en el archivo ''>\n",
			arbolRojinegro.getAbsolutePath()));
		escritor.escribe("<figcaption>ArbolRojinegro con las 15 palabras más utilizadas en el archivo</figcaption>\n");
		escritor.escribe("</figure>\n");
		escritor.escribe("<figure>\n");
		escritor.escribe(String.format("<img src = '%s' alt='ArbolAVL con las 15 palabras más utilizadas en el archivo'\ntitle= 'ArbolAVL con las 15 palabras más utilizadas en el archivo ''>\n",
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
				arbolRojinegro[0] = 4;
				arbolRojinegro[1] = 2;
				arbolRojinegro[2] = 8;
				arbolRojinegro[3] = 1;
				arbolRojinegro[4] = 3;
				arbolRojinegro[5] = 6;
				arbolRojinegro[6] = 9;
				arbolRojinegro[7] = 5;
				arbolRojinegro[8] = 7;
				arbolRojinegro[9] = 10;
			break;

			case 11:
				arbolRojinegro = new Integer[palabras.length];
				arbolRojinegro[0] = 4;
				arbolRojinegro[1] = 2;
				arbolRojinegro[2] = 8;
				arbolRojinegro[3] = 1;
				arbolRojinegro[4] = 3;
				arbolRojinegro[5] = 6;
				arbolRojinegro[6] = 10;
				arbolRojinegro[7] = 5;
				arbolRojinegro[8] = 7;
				arbolRojinegro[9] = 9;
				arbolRojinegro[10] = 11;
			break;

			case 12:
				arbolRojinegro = new Integer[palabras.length]; 
				arbolRojinegro[0] = 8;
				arbolRojinegro[1] = 4;
				arbolRojinegro[2] = 10;
				arbolRojinegro[3] = 2;
				arbolRojinegro[4] = 6;
				arbolRojinegro[5] = 9;
				arbolRojinegro[6] = 11;
				arbolRojinegro[7] = 1;
				arbolRojinegro[8] = 3;
				arbolRojinegro[9] = 5;
				arbolRojinegro[10] = 7;
				arbolRojinegro[11] = 12;
			break;

			case 13: 
				arbolRojinegro = new Integer[palabras.length];
				arbolRojinegro[0] = 8;
				arbolRojinegro[1] = 4;
				arbolRojinegro[2] = 10;
				arbolRojinegro[3] = 2;
				arbolRojinegro[4] = 6;
				arbolRojinegro[5] = 9;
				arbolRojinegro[6] = 12;
				arbolRojinegro[7] = 1;
				arbolRojinegro[8] = 3;
				arbolRojinegro[9] = 5;
				arbolRojinegro[10] = 7;
				arbolRojinegro[11] = 11;
				arbolRojinegro[12] = 13;
			break;
			
			case 14: 
				arbolRojinegro = new Integer[palabras.length];
				arbolRojinegro[0] = 8;
				arbolRojinegro[1] = 4;
				arbolRojinegro[2] = 12;
				arbolRojinegro[3] = 2;
				arbolRojinegro[4] = 6;
				arbolRojinegro[5] = 10;
				arbolRojinegro[6] = 13;
				arbolRojinegro[7] = 1;
				arbolRojinegro[8] = 3;
				arbolRojinegro[9] = 5;
				arbolRojinegro[10] = 7;
				arbolRojinegro[11] = 9;
				arbolRojinegro[12] = 11;
				arbolRojinegro[13] = 14;
			break;

			case 15: 
				arbolRojinegro = new Integer[palabras.length];
				arbolRojinegro[0] = 8;
				arbolRojinegro[1] = 4;
				arbolRojinegro[2] = 12;
				arbolRojinegro[3] = 2;
				arbolRojinegro[4] = 6;
				arbolRojinegro[5] = 10;
				arbolRojinegro[6] = 14;
				arbolRojinegro[7] = 1;
				arbolRojinegro[8] = 3;
				arbolRojinegro[9] = 5;
				arbolRojinegro[10] = 7;
				arbolRojinegro[11] = 9;
				arbolRojinegro[12] = 11;
				arbolRojinegro[13] = 13;
				arbolRojinegro[14] = 15;
			break;

			default:
				arbolRojinegro = new Integer[palabras.length];
				for (int i = 0; i < palabras.length; i++)
					arbolRojinegro[i] = i+1;
			break;
		}
		return arbolRojinegro;
	}

	 /**
     * Regresa un iterador para iterar las palabras de los archivos
     * @return un iterador de Cadenas.
     */
    @Override public Iterator<Cadena> iterator() {
        return new Iterador();
    }

    /**
     * Regresa el numero de archivo.
     * @return numero del archivo.
     */
    public int numeroArchivo() {return numeroArchivo;}

    /**
     * Regresa el nombre del archivo
     * @return nombre del archivo.
     */
    public String nombre() {return archivoTexto.getName();}

    /**
     * Regresa el nombre del archivo
     * @return nombre del archivo.
     */
    public String nombreCompleto() {return archivoHTML.getAbsolutePath();}

    /**
     * Regresa el numero de palabras en el archivo.
     * @return numero de palabras en el archivo.
     */
    public int palabras() {return noPalabras;}
}