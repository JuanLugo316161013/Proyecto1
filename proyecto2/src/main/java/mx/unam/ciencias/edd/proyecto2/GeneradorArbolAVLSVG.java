package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeArbolBinario;
/**
 * Genera codigo SVG que representa a un ArbolAVL.
 */
public class GeneradorArbolAVLSVG extends GeneradorArbolBinarioSVG {

	/**
	 * Constructor que recibe una Lista de elementos del ArbolAVL.
	 * @throws ExcepcionFormatoEquivocado si algun elemento es caracter no imprimible.
	 * @throws NumberFormatException si algun elemento no es un numero entero.
	 */
	public GeneradorArbolAVLSVG(Lista<String> elementos) {
		super(elementos);
	}

	/**
	 * Devuelve una instancia de un nuevo arbol binario, usando una instancia de {@link ArbolAVL}
	 */
	@Override protected ArbolBinario<Integer> nuevoArbolBinario() {
		return new ArbolAVL<Integer>();
	}

	/**
	 * Regresa un vertice de con su elemento, altura, balance y sus correspondientes aristas en codigo SVG.
	 * @return vertice de ArbolAVL
	 */
	protected String vertice(int x, int y, int radio, int distancia, VerticeArbolBinario<Integer> vertice) {
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

		verticeSVG += String.format("<text fill='black' font-family='sans-serif' font-size='%.1f' x='%d' y='%d' text-anchor='middle'>%d</text>\n",
			texto, x, y + 6, vertice.get());

		if (distancia < 20)
			return verticeSVG += String.format("<text fill='blue' font-family='sans-serif' font-size='%.1f' x='%d' y='%d' text-anchor='middle'>{%d/%d}</text>",
					texto - 2.5, x - radio - 3, y - radio - 5, vertice.altura(), izquierdo - derecho);


		if (vertice.hayPadre()) {
			VerticeArbolBinario<Integer> padre = vertice.padre();
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
}
