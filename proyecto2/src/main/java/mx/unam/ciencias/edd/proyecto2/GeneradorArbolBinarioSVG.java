package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeArbolBinario;

/**
 * Genera codigo SVG para representar un ArbolBinario.
 */
public abstract class GeneradorArbolBinarioSVG implements GeneradorEstructuraSVG {

	/** ArbolBinario de Integer interno */
	protected ArbolBinario<Integer> arbolBinario; 

	/**
	 * Constructor vacío.
	 */
	private GeneradorArbolBinarioSVG() {}

	/**
	 * Constructor que recibe una Lista de elementos del ArbolBinario.
	 * @throws ExcepcionFormatoEquivocado si algun elemento es caracter no imprimible.
	 * @throws NumberFormatException si algun elemento no es un numero entero.
	 */
	public GeneradorArbolBinarioSVG(Lista<String> elementos) {
		arbolBinario = nuevoArbolBinario();
		for(String numero : elementos)
			if (numero.isEmpty())
				throw new ExcepcionFormatoEquivocado();
			else 
				arbolBinario.agrega(new Integer(numero));
	}

	/**
	 * Devuelve una instancia de un nuevo arbol binario, usando una instancia de {@link ArbolBinario}
	 */
	protected abstract ArbolBinario<Integer> nuevoArbolBinario();


	/**
	 * Regresa un vertice con su elemento y sus correspondientes aristas en codigo SVG.
	 * @return vertice de ArbolBinario
	 */
	protected String vertice(int x, int y, int radio, int distancia, VerticeArbolBinario<Integer> vertice) {
		double trazo = 3;
		double texto = 20;

		if (radio < 20) {
			trazo = 1.5;
			texto = 16.5;
		}

		String verticeSVG = "";
		if (vertice.hayIzquierdo())
			verticeSVG += String.format("<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='black' stroke-width='3' />\n",
				x, y, x - distancia, y + 120);

		if (vertice.hayDerecho())
			verticeSVG += String.format("<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='black' stroke-width='3' />\n",
				x, y, x + distancia, y + 120);

		verticeSVG += String.format("<circle cx='%d' cy='%d' r='%d' stroke='black' stroke-width='%.1f' fill='white' />\n",
			x, y, radio, trazo);

		return verticeSVG += String.format("<text fill='black' font-family='sans-serif' font-size='%.1f' x='%d' y='%d' text-anchor='middle'>%d</text>\n",
			texto, x, y + 5, vertice.get());
	}

	/**
	 * Recorre el arbolBinario en orden bfs imprimiendo cada vertice en codigo SVG.
	 */
	protected void bfsSVG(int x, int y, int radio, int distancia, VerticeArbolBinario<Integer> vertice) {
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
		System.out.println("\n</svg");
	}
}