package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Color;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeArbolBinario;
/**
 * Clase que genera codigo svg que representa un ArbolRojinegro.
 */
public class GeneradorArbolRojinegroSVG<T> extends GeneradorArbolBinarioOrdenadoSVG<T> {

	/** ArbolRojinegro generico interno */
	private ArbolRojinegro<T> arbolRojinegro;

	/**
	 * Constructor que recibe una lista con los elementos de un ArbolRojinegro.
	 * @param elementos elementos del ArbolRojinegro.
	 */	
	public GeneradorArbolRojinegroSVG(Lista<T> elementos) {
		super(elementos);
		arbolRojinegro = (ArbolRojinegro<T>) arbolBinario;
	}

	/**
	 * Devuelve una instancia de un Arbol Binario que extiende de {@link ArbolBinario}.
	 * @return instancia de {@link ArbolRojinegro}.
	 */
	@Override protected ArbolBinario<T> nuevoArbolBinario() {
		return new ArbolRojinegro<T>();
	}

	/**
	 * Devuelve codigo svg que representa un vertice del ArbolRojinegro, y sus aristas, si es que tiene.
	 * @param x posicion del centro del vertice con respecto al eje 'x'.
	 * @param y posicion del centro del vertice con respecto al eje 'y'.
	 * @param radio radio del vertice.
	 * @param distancia distancia con respecto al eje 'x' con sus hijos.
	 * @param vertice un vertice del ArbolRojinegro.
	 * @return codigo svg que representa un vertice del ArbolRojinegro, y sus arista, si es que tiene.
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
			texto, x, y + 6, vertice.get());
	}
}
