package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Color;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeArbolBinario;
/**
 * Genera codigo SVG que representa a un ArbolRojinegro.
 */
public class GeneradorArbolRojinegroSVG extends GeneradorArbolBinarioSVG {

	/** ArbolRojinegro de Integer interno */
	private ArbolRojinegro<Integer> arbolRojinegro;

	/**
	 * Constructor que recibe una Lista de elementos del ArbolRojinegro.
	 * @throws ExcepcionFormatoEquivocado si algun elemento es caracter no imprimible.
	 * @throws NumberFormatException si algun elemento no es un numero entero.
	 */
	public GeneradorArbolRojinegroSVG(Lista<String> elementos) {
		super(elementos);
		arbolRojinegro = (ArbolRojinegro<Integer>) arbolBinario;
	}

	/**
	 * Devuelve una instancia de un nuevo arbol binario, usando una instancia de {@link ArbolRojinegro}
	 */
	@Override protected ArbolBinario<Integer> nuevoArbolBinario() {
		return new ArbolRojinegro<Integer>();
	}

	/**
	 * Regresa un vertice con su elemento y sus correspondientes aristas en codigo SVG.
	 * @return vertice de ArbolRojinegro
	 */
	@Override protected String vertice(int x, int y, int radio, int distancia, VerticeArbolBinario<Integer> vertice) {
		double trazo = 3;
		double texto = 20;

		if (radio < 20) {
			trazo = 1.5;
			texto = 16.5;
		}

		String verticeSVG = "";

		if (vertice.hayIzquierdo())
			verticeSVG += String.format("<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='blue' stroke-width='%.1f' />\n",
				x, y, x - distancia, y + 120, trazo);

		if (vertice.hayDerecho())
			verticeSVG += String.format("<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='blue' stroke-width='%.1f' />\n",
				x, y, x + distancia, y + 120, trazo);

		switch (arbolRojinegro.getColor(vertice)) {

			case NEGRO:
				verticeSVG += String.format("<circle cx='%d' cy='%d' r='%d' stroke='black' stroke-width='%.1f' fill='black' />\n",
			x, y, radio, trazo);
				break;
			case ROJO:
				verticeSVG += String.format("<circle cx='%d' cy='%d' r='%d' stroke='red' stroke-width='%.1f' fill='red' />\n",
			x, y, radio, trazo);
				break;

		}

		return verticeSVG += String.format("<text fill='white' font-family='sans-serif' font-size='%.1f' x='%d' y='%d' text-anchor='middle'>%d</text>\n",
			texto, x, y + 5, vertice.get());
	}
}
