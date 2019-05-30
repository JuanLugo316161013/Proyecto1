package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeArbolBinario;
/**
 * Genera codigo SVG que representa a un ArbolAVL.
 */
public class GeneradorArbolAVLSVG<T extends Comparable<T>> extends GeneradorArbolBinarioSVG<T> {

	/**
	 * Constructor que recibe una arreglo de elementos del ArbolBinarioAVL.
	 * @param elementos arreglo de elementos.
	 */
	public GeneradorArbolAVLSVG(T[] elementos) {super(elementos);}

	/**
	 * Devuelve una instancia de un nuevo arbol binario, usando una instancia de {@link ArbolAVL}
	 */
	@Override protected ArbolBinario<T> nuevoArbolBinario() {return new ArbolAVL<T>();}

	/**
	 * Regresa un vertice de con su elemento, altura, balance y sus correspondientes aristas en codigo SVG.
	 * @return vertice de ArbolAVL
	 */
	@Override protected String vertice(int x, int y, int radio, int distancia, VerticeArbolBinario<T> vertice) {
		double trazo = 3;
		double texto = 20;
		int izquierdo = -1;
		int derecho = -1;
		boolean hayIzquierdo = false;
		boolean hayDerecho = false;

		if (radio < 20) {
			trazo = 1.5;
			texto = 16.5;
		}

		String verticeSVG = "";
		if (vertice.hayIzquierdo()) {
			verticeSVG += String.format("<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='black' stroke-width='%.1f' />\n",
				x, y, x - distancia, y + 120, trazo);
			izquierdo = vertice.izquierdo().altura();
			hayIzquierdo = true;
		}

		if (vertice.hayDerecho()) {
			verticeSVG += String.format("<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='black' stroke-width='%.1f' />\n",
				x, y, x + distancia, y + 120, trazo);
			derecho = vertice.derecho().altura();
			hayDerecho = true;
		}

		verticeSVG += String.format("<circle cx='%d' cy='%d' r='%d' stroke='black' stroke-width='%.1f' fill='white' />\n",
			x, y, radio, trazo);

		verticeSVG += String.format("<text fill='black' font-family='sans-serif' font-size='%.1f' x='%d' y='%d' text-anchor='middle'>%s</text>\n",
			texto, x, y + 6, vertice.get());

		if (distancia < 20)
			return verticeSVG += String.format("<text fill='blue' font-family='sans-serif' font-size='%.1f' x='%d' y='%d' text-anchor='middle'>{%d/%d}</text>",
					texto - 2.5, x - radio - 3, y - radio - 5, vertice.altura(), izquierdo - derecho);


		if (vertice.hayPadre()) {
			VerticeArbolBinario<T> padre = vertice.padre();
			if (padre.hayDerecho() && padre.derecho() == vertice)
				return verticeSVG += String.format("<text fill='blue' font-family='sans-serif' font-size='%.1f' x='%d' y='%d' text-anchor='middle'>{%d/%d}</text>",
					texto - 2.5, x + radio + 3, y - radio - 5, vertice.altura(), izquierdo - derecho);
			else
				return verticeSVG += String.format("<text fill='blue' font-family='sans-serif' font-size='%.1f' x='%d' y='%d' text-anchor='middle'>{%d/%d}</text>",
					texto - 2.5, x - radio - 3, y - radio - 5, vertice.altura(), izquierdo - derecho);
		}

		return verticeSVG += String.format("<text fill='blue' font-family='sans-serif' font-size='%.1f' x='%d' y='%d' text-anchor='middle'>{%d/%d}</text>",
					texto - 2.5, x + radio + 3, y - radio - 5, vertice.altura(), izquierdo - derecho);
	}

	public static void main(String[] args) {
		Integer[] arbolRojinegro = new Integer[15];
				arbolRojinegro[0] = new Integer (7);
				arbolRojinegro[1] = new Integer (3);
				arbolRojinegro[2] = new Integer (11);
				arbolRojinegro[3] = new Integer (1);
				arbolRojinegro[4] = new Integer (5);
				arbolRojinegro[5] = new Integer (9);
				arbolRojinegro[6] = new Integer (13);
				arbolRojinegro[7] = new Integer (0);
				arbolRojinegro[8] = new Integer (2);
				arbolRojinegro[9] = new Integer (4);
				arbolRojinegro[10] = new Integer (6);
				arbolRojinegro[11] = new Integer (8);
				arbolRojinegro[12] = new Integer (10);
				arbolRojinegro[13] = new Integer (12);
				arbolRojinegro[14] = new Integer (14);
		GeneradorArbolAVLSVG<Integer> generador = new GeneradorArbolAVLSVG<Integer>(arbolRojinegro);
		for (String codigo : generador.codigoSVG())
			System.out.print(codigo); 
	}
}
