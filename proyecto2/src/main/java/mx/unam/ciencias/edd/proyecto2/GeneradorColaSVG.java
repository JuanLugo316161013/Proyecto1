package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Cola;
import mx.unam.ciencias.edd.Lista;
/**
 * Genera codigo SVG que representa a un Cola.
 */
public class GeneradorColaSVG implements GeneradorEstructuraSVG {

	/** Cola de Integer interna. */
	private Cola<Integer> cola;

	/** elementos en la estructura */
	private int elementos;

	/**
	 * Constructor vacío.
	 */
	private GeneradorColaSVG() {}

	/**
	 * Constructor que recibe una Lista de elementos del Cola.
	 * @throws ExcepcionFormatoEquivocado si algun elemento es caracter no imprimible.
	 * @throws NumberFormatException si algun elemento no es un numero entero.
	 */
	public GeneradorColaSVG(Lista<String> elementos) {
		cola = new Cola<Integer>();
		for(String numero : elementos)
			if (numero.isEmpty()) {
				throw new ExcepcionFormatoEquivocado();
			} else {
				cola.mete(new Integer(numero));
				this.elementos++;
			}
	}

	/**
	 * Regresa un nodo con su elemento en codigo SVG.
	 * @param x punto en la recta x.
	 * @param y punto en la recta y.
	 * @return nodo de la Cola con su elemento.
	 */
	private String nodo(int x, int y, int elemento) {
		String nodo = String.format("<rect x = '%d' y = '%d' width = '50' height = '30' stroke-width = '2' stroke = 'black' fill = 'white'/>\n", x, y);
		nodo += String.format("<text fill='black' font-family='sans-serif' font-size='20' x='%d' y='%d' text-anchor='middle'>%d</text>",
			x+25, (y+30)-7, elemento);
		return nodo;
	}

	/**
	 * Imprime una estructura vacía.
	 */
	private void vacio() {
		System.out.println("<svg width='200' height='200' >\n");
		System.out.println("<circle cx='100' cy='100' r='50' stroke='black' stroke-width='3' fill='white' />");
		System.out.println("<line x1='160' y1='40' x2='40' y2='160' stroke='black' stroke-width='3' />");
		System.out.println("\n</svg>");
	}

	/**
	 * Imprime el codigo SVG que representa a la Estructura de Datos.
	 */
	@Override public void imprimirCodigoSVG() {
		if (cola.esVacia()) {
			vacio();
			return;
		}
		int x = 40, y = 40;
		System.out.printf("<svg width='%d' height='110' >\n\n", 80 + elementos*50);

		while (!cola.esVacia()) {
			System.out.println(nodo(x,y,cola.saca()));
			x += 50;
		}
		System.out.println("\n</svg>");
	}
}