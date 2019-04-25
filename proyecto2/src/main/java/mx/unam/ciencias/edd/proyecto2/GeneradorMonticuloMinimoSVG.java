package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ValorIndexable;
import mx.unam.ciencias.edd.MonticuloMinimo;
/**
 * Genera codigo SVG que representa a un MonticuloMinimo.
 */
public class GeneradorMonticuloMinimoSVG implements GeneradorEstructuraSVG {

	/** Estructura Interna */
	private MonticuloMinimo<ValorIndexable<Integer>> monticuloMinimo;

	/**NO.elementos en la Estructura */
	private int elementos;

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
		this.elementos = monticuloMinimo.getElementos();
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
     * Regresa el hijo izquerdo de un Vertice.
     * @param vertice vertice del que queremos su hijo izquerdo.
	 * @return hijo izquerdo del vertice.
     */
    private ValorIndexable<Integer> izquierdo(ValorIndexable<Integer> vertice) {
        return monticuloMinimo.get(2*vertice.getIndice()+1);
    }

	/**
     * Regresa el hijo derecho de un Vertice.
     * @param vertice vertice del que queremos su hijo derecho.
	 * @return hijo derecho del vertice.
     */
    private ValorIndexable<Integer> derecho(ValorIndexable<Integer> vertice) {
        return monticuloMinimo.get(2*vertice.getIndice()+2);
    }

	/**
     * Regresa el padre de un vertice.
     * @param vertice vertice del que queremos su padre.
	 * @return padre del vertice.
     */
    private ValorIndexable<Integer> padre(ValorIndexable<Integer> vertice) {
        return monticuloMinimo.get((int)((vertice.getIndice()-1)/2));
    }

	/**
     * Nos dice si un vertice en el MonticuloMinimo tiene hijo izquierdo.
     * @param vertice vertice del que queremos su padre.
	 * @return true si tiene hijo derecho, si no false.
     */
    private boolean hayIzquierdo(ValorIndexable<Integer> vertice) {
        int izquierdo = 2*vertice.getIndice()+1;
        return izquierdo < elementos;
    }

	/**
     * Nos dice si un vertice en el MonticuloMinimo tiene hijo derecho.
     * @param vertice vertice del que queremos su padre.
	 * @return true si tiene hijo derecho, si no false.
     */
    private boolean hayDerecho(ValorIndexable<Integer> vertice) {
        int derecho = 2*vertice.getIndice()+2;
        return derecho < elementos;
    }

    /**
     * Nos dice si un vertice en el MonticuloMinimo tiene padre.
     * @param vertice vertice del que queremos su padre.
	 * @return true si tiene padre, si no false.
     */
    private boolean hayPadre(ValorIndexable<Integer> vertice) {
        return (int)((vertice.getIndice()-1)/2) >= 0;
    }

	/**
	 * Regresa un vertice con su elemento y sus correspondientes aristas en codigo SVG.
	 * @return vertice de ArbolBinario
	 */
	protected String vertice(int x, int y, int radio, int distancia, ValorIndexable<Integer> vertice) {
		double trazo = 3;
		double texto = 20;

		if (radio < 20) {
			trazo = 1.5;
			texto = 16.5;
		}

		String verticeSVG = "";
		if (hayIzquierdo(vertice))
			verticeSVG += String.format("<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='black' stroke-width='%.1f' />\n",
				x, y, x - distancia, y + 120, trazo);

		if (hayDerecho(vertice))
			verticeSVG += String.format("<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='black' stroke-width='%.1f' />\n",
				x, y, x + distancia, y + 120, trazo);

		verticeSVG += String.format("<circle cx='%d' cy='%d' r='%d' stroke='black' stroke-width='%.1f' fill='white' />\n",
			x, y, radio, trazo);

		return verticeSVG += String.format("<text fill='black' font-family='sans-serif' font-size='%.1f' x='%d' y='%d' text-anchor='middle'>%d</text>\n",
			texto, x, y + 5, vertice.getElemento());
	}

	/**
	 * Recorre el Monticulo Minimo en orden bfs imprimiendo cada vertice en codigo SVG.
	 */
	protected void bfsSVG(int x, int y, int radio, int distancia, ValorIndexable<Integer> vertice) {
		System.out.println(vertice(x,y,radio,distancia,vertice));

		if (hayIzquierdo(vertice))
			bfsSVG(x - distancia, y + 120, radio, (int)distancia/2, izquierdo(vertice));


		if (hayDerecho(vertice))
			bfsSVG(x + distancia, y + 120, radio, (int)distancia/2, derecho(vertice));
	}


	/**
	 * Imprime el codigo SVG que representa a la Estructura de Datos.
	 */
	@Override public void imprimirCodigoSVG() {
		int altura = altura();
		int distancia = (int)Math.pow(2,altura+1)*10 + 20;
		int x = distancia * 2 + 40;
		int y = 40;
		int radio = 20;

		if (altura > 5) {
			radio = 16;
			distancia = (int)Math.pow(2,altura)*10;
			x = distancia * 2 + 40;
		}

		System.out.printf("<svg width='%d' height='%d' >\n\n", distancia * 4 + 80, (altura + 1) * 120);
		bfsSVG(x,y,radio,distancia,monticuloMinimo.get(0));
		System.out.println("\n</svg");
	}
}
