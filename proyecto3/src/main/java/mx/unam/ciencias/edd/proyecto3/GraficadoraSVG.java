package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;
/**
 * Clase que grafica una Lista de datos.
 */
public class GraficadoraSVG<T extends DatoGraficable<T>>{

	private class Dato<T extends DatoGraficable<T>> {

		/* Elemento del dato a graficar. */
		public T elemento;

		/* Color asociado del dato a graficar. */
		public ColorSVG color;

		/**
		 * Constructor que recibe el elemento, y el color de un dato a graficar.
		 * @param elemento elemento del dato.
		 * @param color color asociado al dato.
		 */
		public Dato(T elemento, ColorSVG color) {
			this.elemento = elemento;
			this.color = color;
		}

	}

	/** Colores de los datos a graficas. */
	private ColorSVG[] colores = {ColorSVG.BURLYWOOD, ColorSVG.MARRON, ColorSVG.PURPLE, ColorSVG.LEMONCHIFFON,
	ColorSVG.MEDIUMBLUE, ColorSVG.MEDIUMSEAGREEN, ColorSVG.YELLOW, ColorSVG.ORANGE, ColorSVG.ORANGERED, 
	ColorSVG.DARKRED, ColorSVG.TOMATO, ColorSVG.DARKSLATEBLUE, ColorSVG.SEAGREEN, ColorSVG.DARKBLUE,
	ColorSVG.LIGTHSALMON, ColorSVG.KHAKI, ColorSVG.MEDIUMPURPLE, ColorSVG.PINK};

	/* Elementos a graficar */
	private Lista<T> datos;

	/*To tal de frecuencias en la grafica*/
	private int valorTotal;

	/**
	 * Constructor que recibe una lista de datos a graficar, y el total de las frecuencias en la grafica
	 * @param datos datos de la grafica.
	 * @param valorTotal valor total de lo datos.
	 */
	private GraficadoraSVG(Lista<T> datos, int valorTotal) {
		this.datos = datos;
		this.valorTotal = valorTotal;
	}

	/**
	 * Devuleve una grafica de pastel en codigo svg de los datos.
	 * @return grafica de pastel en codigo svg.
	 */
	public String graficaPastel() {
		int i = 0, noElementos = datos.getElementos();
		Dato[] elementos = new Dato[noElementos];
		String grafica = String.format("<svg width='500' height='%d' xmlns='http://www.w3.org/2000/svg'>\n", noElementos*30 + 540);
		ColorSVG fondo = colores[noElementos];
		grafica += String.format("\t<circle cx='240' cy='240' r='200' fill='%s'/>\n",fondo.toString());
		int radio = 200; 
		int dy = 500;
		double angulo = 0;
		double xi, yi, xf, yf;
		for (T elemento : datos) {
			elementos[i] = new Dato(elemento,colores[i]);
			xi = 240 + radio*Math.cos(Math.toRadians(angulo));
			yi = 240 + radio*Math.sin(Math.toRadians(angulo));
			angulo += 360/(	(double)valorTotal/(double)elemento.frecuencia());
			xf = 240 + radio*Math.cos(Math.toRadians(angulo));
			yf = 240 + radio*Math.sin(Math.toRadians(angulo));
			grafica += String.format("\t<path d='M 240 240 L %.4f %.4f A 200 200 0 0 1 %.4f %.4f z' fill='%s' stroke-width='2' stroke='white'/>\n",
			xi, yi, xf, yf, colores[i].toString());
			grafica += String.format("<rect x='40' y='%d' width='20' height='20' fill='%s'/>\n",dy,elementos[i].color.toString());
			grafica += String.format("<text fill='black' font-family='sans-serif' font-size='18' x='240' dy='%d' text-anchor='middle'>%s</text>",dy + 16,elementos[i].elemento.get());
			dy += 30;
			i++;
		}

		grafica += String.format("<rect x='40' y='%d' width='20' height='20' fill='%s'/>\n",dy,fondo.toString());
		grafica += String.format("<text fill='black' font-family='sans-serif' font-size='18' x='240' dy='%d' text-anchor='middle'>%s</text>",dy + 16,"otros");
		return grafica += "</svg>";
	}

	private ColorSVG[] revuelveColores() {
		int[] numeros = new int[18];
		ColorSVG[] c = new ColorSVG[18];
		boolean repetido;
		int j;
		for (int i = 0; i < 18; i++) {
			do {
				j = (int)(Math.random()*18);
				repetido = false;
					for (int k : numeros)
						if (k == j)
							repetido = false;
			} while (repetido);
			numeros[i] = j;
			c[i] = colores[j];
		}
		return c;
	}

	/**
	 * Devuleve una grafica de barras en codigo svg de los datos.
	 * @param datos datos de la grafica.
	 * @param valorTotal valor total de lo datos.
	 * @return grafica de pastel en codigo svg.
	 */
	public String graficaBarras() {
		return null;
	}
}