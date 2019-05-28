package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;
/**
 * Clase que grafica una Lista de datos.
 */
public class GraficadoraSVG{

	public class Dato<T extends DatoGraficable<T>> {

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
	private final static ColorSVG[] colores = 
	{ColorSVG.BURLYWOOD, ColorSVG.MARRON, ColorSVG.PURPLE, ColorSVG.LEMONCHIFFON,
	ColorSVG.MEDIUMBLUE, ColorSVG.MEDIUMSEAGREEN, ColorSVG.YELLOW, ColorSVG.ORANGE, ColorSVG.ORANGERED, 
	ColorSVG.DARKRED, ColorSVG.TOMATO, ColorSVG.DARKSLATEBLUE, ColorSVG.SEAGREEN, ColorSVG.DARKBLUE,
	ColorSVG.LIGTHSALMON, ColorSVG.KHAKI, ColorSVG.MEDIUMPURPLE, ColorSVG.PINK};

	/**
	 * Constructor vacío.
	 */
	private GraficadoraSVG() {}

	/**
	 * Devuleve una grafica de pastel en codigo svg de los datos.
	 * @param datos datos de la grafica.
	 * @param valorTotal valor total de lo datos.
	 * @return grafica de pastel en codigo svg.
	 */
	public static <T extends DatoGraficable<T>> String graficaPastel(Lista<T> datos, int valorTotal) {
		ColoresSVG[] colores = revuelveColores();
		int i = 0, noElementos = datos.getElementos();
		Dato[] elementos = new Datos[noElementos+1];
		String grafica = "<svg width='500' height='500' xmlns='http://www.w3.org/2000/svg'>\n";
		elementos[noElementos] = new (null, colores[noElementos]);
		grafica += String.format("\t<circle cx='240' cy='240' r='200' fill='%s'/>\n",elementos[noElementos].color.toString());
		int radio = 200, 
		double angulo = 0;
		double xi, yi, xf, yf;
		for (T elemento : datos) {
			elementos[i] = new (elemento,colores[i]);
			xi = 240 + radio*Math.cos(Math.toRadians(angulo));
			yi = 240 + radio*Math.sin(Math.toRadians(angulo));
			angulo += 360/((double)valorTotal/(double)elemento.frecuencia());
			xf = 240 + radio*Math.cos(Math.toRadians(angulo));
			yf = 240 + radio*Math.sin(Math.toRadians(angulo));
			grafica += String.format("\t<path d='M 240 240 L %.4f %.4f A 200 200 0 0 1 %.4f %.4f z' fill='%s' stroke-width='2' stroke='white'/>\n",
			xi, yi, xf, yf, color[i].toString());
		}
		
		grafica += "\t<circle cx='240' cy='240' r='115' fill='white'/>\n";
		return grafica += "</svg>";
	}

	private static ColorSVG[] revuelveColores() {
		int[] numeros = new int[18];
		ColoresSVG[] c = new ColoresSVG[18];
		for (int i = 0; i < 19; i++) {
			int j = (int)(Math.random()*18);
			do {
			boolean repetido = false;
				for (int k : numeros)
					if (k == j)
						repetido = true;
			} while (repetido);
			int[i] = j;
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
	public static <T extends DatoGraficable<T>> String graficaBarras(Lista<T> datos, int valorTotal) {
		return null;
	}
}