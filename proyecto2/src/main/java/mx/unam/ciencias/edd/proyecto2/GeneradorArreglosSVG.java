package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
/**
 * Genera codigo SVG que representa a un Arreglos.
 */
public class GeneradorArreglosSVG implements GeneradorEstructuraSVG {

	private int[] arreglo;
	/**
	 * Constructor vacío.
	 */
	private GeneradorArreglosSVG() {}

	/**
	 * Constructor que recibe una Lista de elementos del Arreglos.
	 * @throws ExcepcionFormatoEquivocado si algun elemento es caracter no imprimible.
	 * @throws NumberFormatException si algun elemento no es un numero entero.
	 */
	public GeneradorArreglosSVG(Lista<String> elementos) {
		Integer entero;
		arreglo = new int[elementos.getElementos()];
		int i = 0;
		for(String numero : elementos) {
			if (numero.isEmpty())
				continue;
				//throw new ExcepcionFormatoEquivocado();
			else{
				entero = new Integer(numero);
				arreglo[i++] = entero.intValue();
			}
		}
	}

	/**
	 * Regresa una celda de memoria en codigo SVG.
	 * @param x punto en la recta x.
	 * @param y punto en la recta y.
	 * @param i indice en el arreglo.
	 * @return celda de memoria con su elemento.
	 */
	private String celda(int x, int y, int i, int elemento) {
		String celda = String.format("<rect x = '%d' y = '%d' width = '50' height = '30' stroke-width = '2' stroke = 'black' fill = 'white'/>\n", x, y);
		celda += String.format("<text fill='black' font-family='sans-serif' font-size='20' x='%d' y='%d' text-anchor='middle'>%d</text>\n",
			x+25, (y+30)-7, elemento);
		celda += String.format("<text fill='black' font-family='sans-serif' font-size='13' x='%d' y='%d' text-anchor='middle'>%d</text>",
			x+25, y+45, i);
		return celda;
	}
	/**
	 * Imprime el codigo SVG que representa a la Estructura de Datos.
	 */
	@Override public void imprimirCodigoSVG() {
		int x = 40, y = 40, i = 0;
		System.out.printf("<svg width='%d' height='110' >\n\n", 80 + arreglo.length*50);
		for (Integer elemento: arreglo) {
			System.out.println(celda(x,y,i++,elemento));
			x += 50;
		}
		System.out.println("\n</svg>");
	}
}