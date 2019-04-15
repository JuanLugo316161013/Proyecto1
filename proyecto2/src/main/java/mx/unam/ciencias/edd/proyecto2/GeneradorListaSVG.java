package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
/**
 * Genera codigo SVG que representa a un Lista.
 */
public class GeneradorListaSVG implements GeneradorEstructuraSVG {

	private Lista<Integer> lista;
	/**
	 * Constructor vacío.
	 */
	private GeneradorListaSVG() {}

	/**
	 * Constructor que recibe una Lista de elementos de Lista.
	 * @throws ExcepcionFormatoEquivocado si algun elemento es caracter no imprimible.
	 * @throws NumberFormatException si algun elemento no es un numero entero.
	 */
	public GeneradorListaSVG(Lista<String> elementos) {
		lista = new Lista<Integer>();
		for(String numero : elementos)
			if (numero.isEmpty())
				throw new ExcepcionFormatoEquivocado();
			else
				lista.agrega(new Integer(numero));
	}

	/**
	 * Regresa un segmento de codigo que representa una flecha doble de 40px de longitud, y 20px de altura 
	 * en SVG, a partir de un punto en el plano (x,y).
	 * @param x punto en la recta x.
	 * @param y punto en la recta y.
	 * @return representacion en de una flecha doble en SVG.
	 */
	private String flecha(int x, int y) {
		String puntos = String.format("%d,%d %d,%d %d,%d %d,%d %d,%d %d,%d %d,%d %d,%d %d,%d %d,%d ",
		 x+5, y+15, x+15, y+7, x+15, y+13, x+35, y+13, x+35, y+7, x+45, y+15, x+35, y+23, x+35, y+17, x+15, y+17, x+15, y+23);

		return "<polygon points = ' " + puntos + " ' stroke-width = '3' stroke = 'white' fill = 'black' />" ;
	}

	/**
	 * Regresa un segmento de codigo que coloca un elemento en un casiila en SVG,
	 * a partir de la posicion de la casilla en el plano (x,y).
	 * @param x punto en la recta x.
	 * @param y punto en la recta y.
	 * @return representacion del elemento
	 */
	private String elemento(int x, int y, int elemento) {
		return  String.format("<text fill='black' font-family='sans-serif' font-size='20' x='%d' y='%d' text-anchor='middle'>%d</text>",
			x+25, (y+30)-7, elemento);
	}

	/**
	 * Regresa un un rectangulo de 50px por 30px en codigo SVG, a partir del punto el plano (x,y).
	 * @param x punto en la recta x.
	 * @param y punto en la recta y.
	 * @return rectangulo de 50px por 30px en codigo SVG.
	 */
	private String rectangulo(int x, int y) {
		return String.format("<rect x = '%d' y = '%d' width = '50' height = '30' stroke-width = '2' stroke = 'black' fill = 'white'/>", x, y);
	}

	/**
	 * Imprime el codigo SVG que representa a la Estructura de Datos.
	 */
	@Override public void imprimirCodigoSVG() {
		int x = 40, y = 40;
		System.out.printf("<svg width='%d' height='100' >\n\n", 100 + lista.getElementos()*50*2);
		System.out.println(rectangulo(x,y));
		System.out.println(elemento(x,y,lista.eliminaPrimero()));
		x += 50;

		while (!lista.esVacia()) {
			System.out.println(flecha(x,y));
			x += 50;
			System.out.println(rectangulo(x,y));
			System.out.println(elemento(x,y,lista.eliminaPrimero()));
			x += 50;
		}

		System.out.println("\n</svg");
	}
}