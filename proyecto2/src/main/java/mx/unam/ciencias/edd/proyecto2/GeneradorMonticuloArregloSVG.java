package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.MonticuloArreglo;
import mx.unam.ciencias.edd.ValorIndexable;
/**
 * Genera codigo SVG que representa a un MonticuloArreglo.
 */
public class GeneradorMonticuloArregloSVG implements GeneradorEstructuraSVG {

	private MonticuloArreglo<ValorIndexable<Integer>> monticuloArreglo;
	/**
	 * Constructor vacío.
	 */
	private GeneradorMonticuloArregloSVG() {}

	/**
	 * Constructor que recibe una Lista de elementos del MonticuloArreglo.
	 * @throws ExcepcionFormatoEquivocado si algun elemento es caracter no imprimible.
	 * @throws NumberFormatException si algun elemento no es un numero entero.
	 */
	public GeneradorMonticuloArregloSVG(Lista<String> elementos) {
		Lista<ValorIndexable<Integer>> valoresIndexables = new Lista<ValorIndexable<Integer>>();
		for (String elemento : elementos) {
			if (elemento.isEmpty()) {
				throw new ExcepcionFormatoEquivocado();
			} else { 
				Integer numero = new Integer(elemento);
				valoresIndexables.agrega(new ValorIndexable<Integer>(numero, numero.intValue()));
			}
		}
		monticuloArreglo = new MonticuloArreglo<ValorIndexable<Integer>>(valoresIndexables);
	}

	/**
	 * Regresa una celda de memoria en codigo SVG.
	 * @param x punto en la recta x.
	 * @param y punto en la recta y.
	 * @param i indice en el MonticuloArreglo.
	 * @return celda de memoria con su elemento.
	 */
	protected String celda(int x, int y, int i, int elemento) {
		String celda = String.format("<rect x = '%d' y = '%d' width = '50' height = '30' stroke-width = '2' stroke = 'black' fill = 'white'/>\n", x, y);
		celda += String.format("<text fill='black' font-family='sans-serif' font-size='20' x='%d' y='%d' text-anchor='middle'>%d</text>\n",
			x+25, (y+30)-7, elemento);
		celda += String.format("<text fill='black' font-family='sans-serif' font-size='13' x='%d' y='%d' text-anchor='middle'>%d</text>",
			x+25, y+45, i);
		return celda;
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
		if (monticuloArreglo.esVacia()) {
			vacio();
			return;
		}

		int x = 40, y = 40;
		System.out.printf("<svg width='%d' height='110' >\n\n", 80 + monticuloArreglo.getElementos()*50);
		for (int i = 0; i < monticuloArreglo.getElementos(); i++) {
			System.out.println(celda(x,y,i,monticuloArreglo.get(i).getElemento().intValue()));
			x += 50;
		}
		System.out.println("\n</svg>");
	}
}