package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeGrafica;
import java.util.Iterator;
/**
 * Genera codigo SVG que representa a un Grafica.
 */
public class GeneradorGraficaSVG<T> extends GeneradorEstructuraSVG<T>{

	/** Clase interna privada para VerticeSVG  */
	private class VerticeSVG {

		/** punto en la recta x*/
		public double x;

		/** punto en la recta y */
		public double y;

		/** vertice de la grafica */
		public VerticeGrafica<T> vertice;

		/**
		 * Constructor que recibe la posicion donde estará el vertice y el vertice.
		 * @param x punto en la recta x.
	 	 * @param y punto en la recta y.
	 	 * @param vertice vertice de la grafica.
		 */
		public VerticeSVG(double x, double y, VerticeGrafica<T> vertice) {
			this.x = x;
			this.y = y;
			this.vertice = vertice;
		}

	}

	/** grafica de T interna */
	private Grafica<T> grafica;

	/** Vertices SVG asociados de la grafica */
	private Lista<VerticeSVG> vertices;

	/** radio de la circunferencia más grande donde estaran los elementos(vertices, conjunto de vertices) */
	private double radio;

	/**
	 * Constructor vacío.
	 */
	private GeneradorGraficaSVG() {}

	/**
	 * Constructor que recibe una Lista de elementos del Grafica.
	 * @throws ExcepcionFormatoEquivocado si algun elemento es caracter no imprimible.
	 * @throws NumberFormatException si algun elemento no es un numero entero.
	 * @throws ExcepcionVerticesImpares si el numero de elementos son impares.
	 */
	public GeneradorGraficaSVG(Grafica<T> grafica) {
		this.grafica = grafica;
		vertices = new Lista<VerticeSVG>();
	}

	/**
	 * Regresa el VerticeSVG que contiene un elemento dado, de la liste de vertices.
	 */
	private VerticeSVG vertice(T elemento) {
		for (VerticeSVG vertice : vertices)
			if (vertice.vertice.get().equals(elemento))
				return vertice;

		return null;
	}

	/**
	 * Regresa el radio del circulo donde se colocaran n elementos(vertice o cunjunto se vertices) y que cada
	 * elemento vi, vi+1 estaran
	 * a una distancia dada, i = 1, 2, 3,..., n-1.
	 * @param n numero de elementos en el circulo.
	 * @param distancia distancia entre cada vi y vi+1 elementos.
	 */
	private double radio(int n, double distancia) {
		double apotema = distancia/(2*Math.tan(Math.toRadians((360/n)/2)));
		return Math.sqrt(Math.pow(apotema,2) + Math.pow(distancia/2,2)) + 10;
	}

	/**
	 * Posiciona los vertices de la grafica.
	 */
	private void posicionaVertices() {
		int elementos = grafica.getElementos();
		double angulo;
		double radio;
		double x;
		double y;

		if (elementos <= 15) {
			radio = radio(elementos,100);
			this.radio = radio;
			int i = -1;
			for (T elemento : grafica) {
				angulo = i++ * (360/elementos);
				x = 40 + radio + radio*Math.cos(Math.toRadians(angulo));
				y = 40 + radio + radio*Math.sin(Math.toRadians(angulo));
				vertices.agrega(new VerticeSVG(x,y,grafica.vertice(elemento)));
			}
		} else {
			radio = radio(10,100);
			int componentes = elementos%10 == 0? (int)(elementos/10) : (int)(elementos/10) + 1;
			double radioExterno = radio(componentes,4*radio);
			this.radio = radioExterno + radio;
			Iterator<T> it = grafica.iterator();
			double anguloExterno;
			double xExterno;
			double yExterno;
			int i = 1;

			while (i <= componentes) {
				anguloExterno = i++ * (360/componentes);
				xExterno = 40 + radioExterno + radioExterno*Math.cos(Math.toRadians(anguloExterno));
				yExterno = 40 + radioExterno + radioExterno*Math.sin(Math.toRadians(anguloExterno));
				for (int j = 1; it.hasNext() && j <= 10; j++){
					angulo = j * (360/10);
					x = xExterno + radio + radio*Math.cos(Math.toRadians(angulo));
					y = yExterno + radio + radio*Math.sin(Math.toRadians(angulo));
					vertices.agrega(new VerticeSVG(x,y,grafica.vertice(it.next())));
				}
			}
		}
	}

	/**
	 * Imprime las aristas de la grafica en codigo SVG.
	 */
	private void dibujaAristas() {
		int trazo = 2;

		if (grafica.getElementos() > 10)
			trazo = 1;

		VerticeSVG vecinoSVG;
		for (VerticeSVG vertice : vertices)
			for (VerticeGrafica<T> vecino : vertice.vertice.vecinos())
				if ((vecinoSVG = vertice(vecino.get())) != null)
					System.out.printf("<line x1='%.6f' y1='%.6f' x2='%.6f' y2='%.6f' stroke='black' stroke-width='%d' />\n",
					vecinoSVG.x, vecinoSVG.y, vertice.x, vertice.y, trazo);
	}

	private void dibujaVertices() {
		for (VerticeSVG vertice : vertices) {
			System.out.printf("<circle cx='%.6f' cy='%.6f' r='15' stroke='red' stroke-width='2.5' fill='white' />\n",
				vertice.x, vertice.y);
			System.out.printf("<text fill='black' font-family='sans-serif' font-size='20' x='%.6f' y='%.6f' text-anchor='middle'>%d</text>\n",
			vertice.x, vertice.y + 6, vertice.vertice.get());
		}
	}

	/**
	 * Imprime el codigo SVG que representa a la Estructura de Datos.
	 */
	@Override public void imprimirCodigoSVG() {
		if (grafica.esVacia()) {
			System.out.println(vacio());
			return;
		}

		if (grafica.getElementos() == 1) {
			System.out.printf("<svg width='130' height='130' >\n\n");
			for (T elemento : grafica) {
				VerticeSVG vertice = new VerticeSVG(65,65,grafica.vertice(elemento));
				System.out.printf("<circle cx='%.6f' cy='%.6f' r='25' stroke='red' stroke-width='2.5' fill='white' />\n",
					vertice.x, vertice.y);
				System.out.printf("<text fill='black' font-family='sans-serif' font-size='20' x='%.6f' y='%.6f' text-anchor='middle'>%d</text>\n",
					vertice.x - 1, vertice.y + 6, vertice.vertice.get());
			}
		}

		posicionaVertices();
		System.out.printf("<svg width='%.2f' height='%.2f' >\n", 2*radio + 80, 2*radio + 80);
		dibujaAristas();
		dibujaVertices();
		System.out.println("\n</svg>");
	}

	public Lista<String> codigoSVG() {
		Lista<String> codigoSVG = new Lista<String>();
		if (grafica.esVacia()) {
			codigoSVG.agrega(vacio());
			return codigoSVG;
		}

		if (grafica.getElementos() == 1) {
			codigoSVG.agrega(String.format("<svg width='130' height='130' >\n\n"));
			for (T elemento : grafica) {
				VerticeSVG vertice = new VerticeSVG(65,65,grafica.vertice(elemento));
				codigoSVG.agrega(String.format("<circle cx='%.6f' cy='%.6f' r='25' stroke='red' stroke-width='2.5' fill='white' />\n",
					vertice.x, vertice.y));
				codigoSVG.agrega(String.format("<text fill='black' font-family='sans-serif' font-size='20' x='%.6f' y='%.6f' text-anchor='middle'>%s</text>\n",
					vertice.x - 1, vertice.y + 6, vertice.vertice.get()));
				codigoSVG.agrega(String.format("</svg>"));
				return codigoSVG;
			}
		}

		posicionaVertices();
		codigoSVG.agrega(String.format("<svg width='%.2f' height='%.2f' xmlns='http://www.w3.org/2000/svg'>\n", 2*radio + 80, 2*radio + 80));
		dibujaAristas(codigoSVG);
		dibujaVertices(codigoSVG);
		codigoSVG.agrega(String.format("</svg>"));
		return codigoSVG;
	}

	private void dibujaAristas(Lista<String> codigoSVG) {
		int trazo = 2;

		if (grafica.getElementos() > 10)
			trazo = 1;

		VerticeSVG vecinoSVG;
		for (VerticeSVG vertice : vertices)
			for (VerticeGrafica<T> vecino : vertice.vertice.vecinos())
				if ((vecinoSVG = vertice(vecino.get())) != null)
					codigoSVG.agrega(String.format("<line x1='%.6f' y1='%.6f' x2='%.6f' y2='%.6f' stroke='black' stroke-width='%d' />\n",
					vecinoSVG.x, vecinoSVG.y, vertice.x, vertice.y, trazo));
	}

	private void dibujaVertices(Lista<String> codigoSVG) {
		for (VerticeSVG vertice : vertices) {
			codigoSVG.agrega(String.format("<circle cx='%.6f' cy='%.6f' r='15' stroke='red' stroke-width='2.5' fill='white' />\n",
				vertice.x, vertice.y));
			codigoSVG.agrega(String.format("<text fill='black' font-family='sans-serif' font-size='20' x='%.6f' y='%.6f' text-anchor='middle'>%s</text>\n",
			vertice.x, vertice.y + 6, vertice.vertice.get()));
		}
	}
}