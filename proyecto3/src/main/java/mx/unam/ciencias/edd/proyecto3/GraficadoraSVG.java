package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Conjunto;
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
	private ColorSVG[] colores = {ColorSVG.DARKSLATEGRAY, ColorSVG.BROWN, ColorSVG.PURPLE, ColorSVG.LEMONCHIFFON,
	ColorSVG.MEDIUMBLUE, ColorSVG.MEDIUMSEAGREEN, ColorSVG.YELLOW, ColorSVG.ORANGE, ColorSVG.ORANGERED, 
	ColorSVG.DARKRED, ColorSVG.TOMATO, ColorSVG.DARKSLATEBLUE, ColorSVG.SEAGREEN, ColorSVG.DARKBLUE,
	ColorSVG.LIGTHSALMON, ColorSVG.KHAKI, ColorSVG.MEDIUMPURPLE, ColorSVG.PINK};

	/* Elementos a graficar */
	private T[] datos;

	/*To tal de frecuencias en la grafica*/
	private int valorTotal;

	/**
	 * Constructor que recibe una lista de datos a graficar, y el total de las frecuencias en la grafica
	 * @param datos datos de la grafica.
	 * @param valorTotal valor total de lo datos.
	 */
	private GraficadoraSVG(T[] datos, int valorTotal) {
		this.datos = datos;
		this.valorTotal = valorTotal;
	}

	/**
	 * Devuleve una grafica de pastel en codigo svg de los datos.
	 * @return grafica de pastel en codigo svg.
	 */
	public String graficaPastel() {
		ColorSVG[] colores = revuelveColores();
		int i = 0, noElementos = datos.length, radio = 200, dy = 500, frecuenciaAcumulada = 0;
		double angulo = 0,xi, yi, xf, yf;
		ColorSVG fondo = colores[noElementos];
		String grafica = String.format("<svg width='500' height='%d' xmlns='http://www.w3.org/2000/svg'>\n", noElementos*30 + 540);
		
		if (noElementos == 0){
			grafica += String.format("<circle cx='240' cy='240' r='200' fill='%s' />",fondo.toString());
			grafica += String.format("<rect x='40' y='%d' width='20' height='20' fill='%s'/>\n",500,fondo.toString());
			grafica += String.format("<text fill='black' font-family='sans-serif' font-size='18' x='240' dy='%d' text-anchor='middle'>%s</text>\n",500 + 16,"otros");
			return grafica += "</svg>";
		}

		if (noElementos == 1 && datos[0].frecuencia() == valorTotal) {
			grafica += String.format("<circle cx='240' cy='240' r='200' fill='%s' />",fondo.toString());
			grafica += String.format("<rect x='40' y='%d' width='20' height='20' fill='%s'/>\n",500,fondo.toString());
			grafica += String.format("<text fill='black' font-family='sans-serif' font-size='18' x='240' dy='%d' text-anchor='middle'>%s</text>\n",500 + 16,datos[0].get());
			return grafica += "</svg>";
		}

		for (T elemento : datos) {
			xi = 240 + radio*Math.cos(Math.toRadians(angulo));
			yi = 240 + radio*Math.sin(Math.toRadians(angulo));
			angulo += 360/(	(double)valorTotal/(double)elemento.frecuencia());
			xf = 240 + radio*Math.cos(Math.toRadians(angulo));
			yf = 240 + radio*Math.sin(Math.toRadians(angulo));
			grafica += String.format("<path d='M 240 240 L %.4f %.4f A 200 200 0 %d 1 %.4f %.4f z' fill='%s' stroke-width='2' stroke='white'/>\n",
			xi, yi, elemento.frecuencia() > valorTotal/2? 1 : 0 , xf, yf, colores[i].toString());
			grafica += String.format("<rect x='40' y='%d' width='20' height='20' fill='%s'/>\n",dy,colores[i].toString());
			grafica += String.format("<text fill='black' font-family='sans-serif' font-size='18' x='240' y='%d' text-anchor='middle'>%s</text>\n",dy + 16,elemento.get());
			dy += 30;
			i++;
			frecuenciaAcumulada += elemento.frecuencia();
		}
		xi = 240 + radio*Math.cos(Math.toRadians(angulo));
		yi = 240 + radio*Math.sin(Math.toRadians(angulo));
		angulo += 360/(	(double)valorTotal/(double)(valorTotal - frecuenciaAcumulada));
		xf = 240 + radio*Math.cos(Math.toRadians(angulo));
		yf = 240 + radio*Math.sin(Math.toRadians(angulo));
		grafica += String.format("<path d='M 240 240 L %.4f %.4f A 200 200 0 %d 1 %.4f %.4f z' fill='%s' stroke-width='2' stroke='white'/>\n",
		xi, yi, frecuenciaAcumulada > valorTotal/2 ? 0 : 1, xf, yf, fondo.toString());
		grafica += String.format("<rect x='40' y='%d' width='20' height='20' fill='%s'/>\n",dy,fondo.toString());
		grafica += String.format("<text fill='black' font-family='sans-serif' font-size='18' x='240' y='%d' text-anchor='middle'>%s</text>\n",dy + 16,"otros");
		return grafica += "</svg>";

	}

	/**
	 * Revuelve el arreglo de colores en svg.
	 * @return un arreglo con colores aleatorios.
	 */
	private ColorSVG[] revuelveColores() {
		Conjunto<Integer> numeros = new Conjunto<Integer>(18);
		ColorSVG[] c = new ColorSVG[18];
		int j;
		for (int i = 0;i < 18; i++) {
			j = (int) (Math.random()*18);
			while (numeros.contiene(j))
				j = (int) (Math.random()*18);
			numeros.agrega(j);
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
		ColorSVG[] colores = revuelveColores();
		int noElementos = datos.length, ancho = (noElementos + 1)*120, dy = 480, dx = 80, i = 0, frecuenciaAcumulada = 0;
		double altura;
		ColorSVG fondo = colores[noElementos];
		Lista<Dato<T>> elementos = new Lista<Dato<T>>();
		String grafica = String.format("<svg width='%d' height='%d' xmlns='http://www.w3.org/2000/svg'>\n", 120 + ancho, 480 + (noElementos + 1) * 30);
		grafica += String.format("<line x1='40' x2='%d' y1='40' y2='40' stroke = 'black' stroke-width = '1'/>\n", ancho + 80);
		grafica += String.format("<line x1='40' x2='%d' y1='140' y2='140' stroke = 'black' stroke-width = '1'/>\n", ancho + 80);
		grafica += String.format("<line x1='40' x2='%d' y1='240' y2='240' stroke = 'black' stroke-width = '1'/>\n", ancho + 80);
		grafica += String.format("<line x1='40' x2='%d' y1='340' y2='340' stroke = 'black' stroke-width = '1'/>\n", ancho + 80);
		grafica += String.format("<line x1='40' x2='%d' y1='440' y2='440' stroke = 'black' stroke-width = '1'/>\n", ancho + 80);
		grafica += String.format("<text fill='black' font-family='sans-serif' font-size='16' x='40' y='30' text-anchor='middle'>%d</text>",valorTotal);
		grafica += String.format("<text fill='black' font-family='sans-serif' font-size='16' x='40' y='130' text-anchor='middle'>%.1f</text>",(double)((valorTotal/4)*3));
		grafica += String.format("<text fill='black' font-family='sans-serif' font-size='16' x='40' y='230' text-anchor='middle'>%.1f</text>",(double)((valorTotal/4)*2));
		grafica += String.format("<text fill='black' font-family='sans-serif' font-size='16' x='40' y='330' text-anchor='middle'>%.1f</text>",(double)((valorTotal/4)*1));
		grafica += String.format("<text fill='black' font-family='sans-serif' font-size='16' x='40' y='430' text-anchor='middle'>%d</text>",0);
		for (T elemento : datos) {
			frecuenciaAcumulada += elemento.frecuencia();
			altura = (elemento.frecuencia()*400)/valorTotal;
			grafica += String.format("<rect x='%d' y='%.4f' width='90' height='%.4f' fill='%s'/>", dx, 440-altura, altura, colores[i].toString());
			grafica += String.format("<rect x='40' y='%d' width='20' height='20' fill='%s'/>\n",dy,colores[i].toString());
			grafica += String.format("<text fill='black' font-family='sans-serif' font-size='18' x='240' y='%d' text-anchor='middle'>%s</text>\n",dy + 16,elemento.get());
			dy += 30;
			dx += 120;
			i++;
		}
		altura = ((valorTotal-frecuenciaAcumulada)*400)/valorTotal;
		grafica += String.format("<rect x='%d' y='%.4f' width='90' height='%.4f' fill='%s'/>", dx, 440-altura, altura, fondo.toString());
		grafica += String.format("<rect x='40' y='%d' width='20' height='20' fill='%s'/>\n",dy,fondo.toString()); 
		grafica += String.format("<text fill='black' font-family='sans-serif' font-size='18' x='240' y='%d' text-anchor='middle'>%s</text>\n",dy + 16,"otros");
		return grafica += "</svg>";
	}
}
