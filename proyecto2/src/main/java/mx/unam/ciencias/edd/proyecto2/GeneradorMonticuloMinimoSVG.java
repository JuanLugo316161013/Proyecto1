package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ValorIndexable;
import mx.unam.ciencias.edd.MonticuloMinimo;
/**
 * Genera codigo SVG que representa a un MonticuloMinimo.
 */
public class GeneradorMonticuloMinimoSVG implements GeneradorEstructuraSVG {

	/** Estructura Interna */
	private MonticuloMinimo<ValorIndexable<Integer>> monticuloMinimo

	/** 
	 * Constructor vacío.
	 */
	private GeneradorMonticuloMinimoSVG() {}

	/**
	 * Constructor que recibe una Lista de elementos del MonticuloMinimo.
	 * @throws ExcepcionFormatoEquivocado si algun elemento es caracter no imprimible.
	 * @throws NumberFormatException si algun elemento no es un numero entero.
	 */
	public GeneradorMonticuloMinimoSVG(Lista<String> elementos) {
		Lista<ValorIndexable<Integer>> lista = new Lista<ValorIndexable<Integer>>();
		String elemento;
		while (!elementos.esVacia()) {
			elemento = elementos.eliminaPrimero();
			if (elemento.isEmpty())
				throw new ExcepcionFormatoEquivocado();
			Integer numero = new Integer(elemento);
			lista.agrega(new ValorIndexable<Integer>(numero,numero.intValue()));
		}
		monticuloMinimo = new MonticuloMinimo<ValorIndexable<Integer>>(lista);
	}

	/**
	 * Devuelve la altura del Monticulo Minimo
	 * @return altura del Monticulo Minimo
	 */
	private int altura() {
		int elementos = monticuloMinimo.getElementos();
		if (elementos == 0)
            return -1;
        return (int)(Math.floor(Math.log(elementos) / Math.log(2)));
	}

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
}