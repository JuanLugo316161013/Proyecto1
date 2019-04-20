package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeGrafica;
import java.util.Iterator;
/**
 * Genera codigo SVG que representa a un Grafica.
 */
public class GeneradorGraficaSVG implements GeneradorEstructuraSVG {

	/** Clase interna GeneradorGraficaSVG que contiene  */ 
	private class VerticeSVG {

		/** punto en la recta x*/
		public double x;

		/** punto en la recta y */
		public double y;

		/** vertice de la grafica */
		public VerticeGrafica<Integer> vertice;

		/** 
		 * Constructor que recibe la posicion donde estará el vertice y el vertice.
		 * @param x punto en la recta x.
	 	 * @param y punto en la recta y.
	 	 * @param vertice vertice de la grafica.
		 */
		public VerticeSVG(double x, double y, VerticeGrafica<Integer> vertice) {
			this.x = x;
			this.y = y;
			this.vertice = vertice;
		}

	}

	/** grafica de Integer interna */
	private Grafica<Integer> grafica;

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
	public GeneradorGraficaSVG(Lista<String> elementos) {
		if (elementos.getElementos()%2 != 0)
				throw new ExcepcionVerticesImpares();

		grafica = new Grafica<Integer>();
		vertices = new Lista<VerticeSVG>();

		int i = 0;
		Integer[] parVertices = new Integer[2];
		for(String numero : elementos) {
			if (numero.isEmpty())
				throw new ExcepcionFormatoEquivocado();

			parVertices[i] = new Integer(numero);
			
			if (i == 1) {
				if (parVertices[0].equals(parVertices[1])) {
					grafica.agrega(parVertices[0]);
				} else {
					try {
						grafica.agrega(parVertices[0]);
				    } catch (IllegalArgumentException iae) {}
				    try {
				    	grafica.agrega(parVertices[1]);
				    } catch (IllegalArgumentException iae) {}
				    try {
						grafica.conecta(parVertices[0],parVertices[1]);
				    } catch (IllegalArgumentException iae) {}
				}
				i = -1;
			}
			i++;
		}
	}
	
	private VerticeSVG vertice(Integer elemento) {
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

	private void posicionaVertices() {
		int elementos = grafica.getElementos();
		double forma = Math.random();
		double angulo;
		double radio;
		double x;
		double y;

		
		if (forma > 0.5 || elementos <= 10) {
			radio = radio(elementos,100);
			this.radio = radio;
			int i = -1;
			for (Integer elemento : grafica) {
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
			Iterator<Integer> it = grafica.iterator();
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

	private void dibujaAristas() {
		int trazo = 2;

		if (grafica.getElementos() > 10)
			trazo = 1;

		VerticeSVG vecinoSVG;
		for (VerticeSVG vertice : vertices)
			for (VerticeGrafica<Integer> vecino : vertice.vertice.vecinos())
				if ((vecinoSVG = vertice(vecino.get())) != null)
					System.out.printf("<line x1='%.6f' y1='%.6f' x2='%.6f' y2='%.6f' stroke='black' stroke-width='%d' />\n",
					vecinoSVG.x, vecinoSVG.y, vertice.x, vertice.y, trazo);
	}

	/**
	 * Imprime el codigo SVG que representa a la Estructura de Datos.
	 */
	@Override public void imprimirCodigoSVG() {
		posicionaVertices();
		System.out.printf("<svg width='%.2f' height='%.2f' >\n\n", 2*radio + 80, 2*radio + 80);
		dibujaAristas();
		for (VerticeSVG vertice : vertices) {
			System.out.printf("<circle cx='%.6f' cy='%.6f' r='20' stroke='black' stroke-width='2.5' fill='white' />\n",
				vertice.x, vertice.y);
			System.out.printf("<text fill='black' font-family='sans-serif' font-size='20' x='%.6f' y='%.6f' text-anchor='middle'>%d</text>\n",
			vertice.x, vertice.y + 5, vertice.vertice.get());
		}
		System.out.println("\n</svg>");
	}
}