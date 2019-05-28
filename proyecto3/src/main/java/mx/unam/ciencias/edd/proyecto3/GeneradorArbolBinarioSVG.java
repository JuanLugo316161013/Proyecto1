package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeArbolBinario;

/**
 * Genera codigo SVG para representar un ArbolBinario.
 */
public abstract class GeneradorArbolBinarioSVG<T> extends GeneradorEstructuraSVG<T> {

	/** ArbolBinario generico interno. */
	protected ArbolBinario<T> arbolBinario;

	/**
	 * Constructor vacío.
	 */
	private GeneradorArbolBinarioSVG() {}

	/**
	 * Constructor que recibe una arreglo de elementos del ArbolBinario.
	 * @param elementos arreglo de elementos.
	 */
	public GeneradorArbolBinarioSVG(T[] elementos) {
		arbolBinario = nuevoArbolBinario();
		for (T elemento : elementos)
			arbolBinario.agrega(elemento);
	}

	/**
	 * Devuelve una instancia de un nuevo arbol binario, usando una instancia de {@link ArbolBinario}
	 */
	protected abstract ArbolBinario<T> nuevoArbolBinario();


	/**
	 * Regresa un vertice con su elemento y sus correspondientes aristas en codigo SVG.
	 * @return vertice de ArbolBinario
	 */
	protected String vertice(int x, int y, int radio, int distancia, VerticeArbolBinario<T> vertice) {
		double trazo = 3;
		double texto = 20;

		if (radio < 20) {
			trazo = 1.5;
			texto = 16.5;
		}

		String verticeSVG = "";
		if (vertice.hayIzquierdo())
			verticeSVG += String.format("<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='black' stroke-width='%.1f' />\n",
				x, y, x - distancia, y + 120, trazo);

		if (vertice.hayDerecho())
			verticeSVG += String.format("<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='black' stroke-width='%.1f' />\n",
				x, y, x + distancia, y + 120, trazo);

		verticeSVG += String.format("<circle cx='%d' cy='%d' r='%d' stroke='black' stroke-width='%.1f' fill='white' />\n",
			x, y, radio, trazo);

		return verticeSVG += String.format("<text fill='black' font-family='sans-serif' font-size='%.1f' x='%d' y='%d' text-anchor='middle'>%s</text>\n",
			texto, x, y + 6, vertice.get());
	}

	/**
	 * Recorre el arbolBinario en orden bfs imprimiendo cada vertice en codigo SVG.
	 */
	protected void bfsSVG(int x, int y, int radio, int distancia, VerticeArbolBinario<T> vertice) {
		System.out.println(vertice(x,y,radio,distancia,vertice));

		if (vertice.hayIzquierdo())
			bfsSVG(x - distancia, y + 120, radio, (int)distancia/2, vertice.izquierdo());


		if (vertice.hayDerecho())
			bfsSVG(x + distancia, y + 120, radio, (int)distancia/2, vertice.derecho());
	}

	/**
	 * Imprime el codigo SVG que representa a la Estructura de Datos.
	 */
	@Override public void imprimirCodigoSVG() {
		if (arbolBinario.esVacia()) {
			System.out.println(vacio());
			return;
		}

		int altura = arbolBinario.altura();
		int distancia = (int)Math.pow(2,altura+1)*10 + 20;
		int x = distancia * 2 + 40;
		int y = 40;
		int radio = 20;

		if (altura > 5) {
			radio = 16;
			distancia = (int)Math.pow(2,altura)*10;
			if (distancia < 0) {
				distancia = distancia*-1;
				distancia += (altura-5)*60*2 + (altura-5)*200;
			}
			x = distancia * 2 + 40;
		}

		System.out.printf("<svg width='%d' height='%d' >\n\n", distancia * 4 + 80, (altura + 1) * 120);
		bfsSVG(x,y,radio,distancia, arbolBinario.raiz());
		System.out.println("\n</svg>");
	}

	/**
	 * Recorre el arbolBinario en orden bfs agregando cada vertice en codigo SVG a una lista.
	 */
	protected void bfsSVG(int x, int y, int radio, int distancia, VerticeArbolBinario<T> vertice, Lista<String> codigoSVG) {
		codigoSVG.agrega(vertice(x,y,radio,distancia,vertice));

		if (vertice.hayIzquierdo())
			bfsSVG(x - distancia, y + 120, radio, (int)distancia/2, vertice.izquierdo(), codigoSVG);


		if (vertice.hayDerecho())
			bfsSVG(x + distancia, y + 120, radio, (int)distancia/2, vertice.derecho(), codigoSVG);
	}


	public Lista<String> codigoSVG() {
		Lista<String> codigoSVG = new Lista<String>();
		if (arbolBinario.esVacia()) {
			codigoSVG.agrega(vacio());
			return codigoSVG;
		}

		int altura = arbolBinario.altura();
		int distancia = (int)Math.pow(2,altura+1)*10 + 20;
		int x = distancia * 2 + 40;
		int y = 40;
		int radio = 20;

		if (altura > 5) {
			radio = 16;
			distancia = (int)Math.pow(2,altura)*10;
			if (distancia < 0) {
				distancia = distancia*-1;
				distancia += (altura-5)*60*2 + (altura-5)*200;
			}
			x = distancia * 2 + 40;
		}

		codigoSVG.agrega(String.format("<svg width='%d' height='%d' xmlns='http://www.w3.org/2000/svg'>\n\n", distancia * 4 + 80, (altura + 1) * 120));
		bfsSVG(x,y,radio,distancia, arbolBinario.raiz(),codigoSVG);
		codigoSVG.agrega("\n</svg>");
		for (String cadena : codigoSVG)
			System.out.print(cadena);
		return codigoSVG;
	}
}
