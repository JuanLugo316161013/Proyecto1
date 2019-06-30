package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeArbolBinario;

/**
 * <p>Clase abstracta que genera codigo svg que representa un Arbol Binario. En toda la clase el "Arbol Binario"
 * al que se hace referencia es el arbol binario que se ah creado con la lista de elementos del constructor.</p>
 * <p>Las clases que heradan directa o indirectamente de esta clase son : </p>
 * <ul>
 *     <li>{@link GeneradorArbolBinarioCompletoSVG}</li>
 *     <li>{@link GeneradorArbolBinarioOrdenadoSVG}</li>
 *     <li>{@link GeneradorArbolAVLSVG}</li>
 *     <li>{@link GeneradorArbolRojinegroSVG}</li>
 * </ul>
 */
public abstract class GeneradorArbolBinarioSVG<T> extends GeneradorEstructuraSVG<T> {

	/** ArbolBinario generico interno */
	protected ArbolBinario<T> arbolBinario;

	/**
	 * Constructor que recibe una lista con los elementos de un Arbol Binario.
	 * @param elementos elementos del Arbol Binario.
	 */
	public GeneradorArbolBinarioSVG(Lista<T> elementos) {
		arbolBinario = nuevoArbolBinario();
		for (T elemento : elementos)
			arbolBinario.agrega(elemento);
	}

	/**
	 * Devuelve una instancia de un Arbol Binario que extiende de {@link ArbolBinario}.
	 * @return instancia de un Arbol Binario que extiende de {@link ArbolBinario}.
	 */
	protected abstract ArbolBinario<T> nuevoArbolBinario();


	/**
	 * Devuelve codigo svg que representa un vertice del Arbol Binario, y sus aristas, si es que tiene.
	 * @param x posicion del centro del vertice con respecto al eje 'x'.
	 * @param y posicion del centro del vertice con respecto al eje 'y'.
	 * @param radio radio del vertice.
	 * @param distancia distancia con respecto al eje 'x' con sus hijos.
	 * @param vertice un vertice del Arbol Binario.
	 * @return codigo svg que representa un vertice del Arbol Binario, y sus arista, si es que tiene.
	 */
	protected String vertice(int x, int y, int radio, int distancia, VerticeArbolBinario<T> vertice) {
		double trazo = 3;
		double texto = 20;

		if (radio < 20) {
			trazo = 1.5;
			texto = 16.5;
		}

		String verticeSVG = "";
		if (vertice.hayIzquierdo())
			verticeSVG += String.format("<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='black' stroke-width='%.1f' />\n",
				x, y, x - distancia, y + 120, trazo);

		if (vertice.hayDerecho())
			verticeSVG += String.format("<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='black' stroke-width='%.1f' />\n",
				x, y, x + distancia, y + 120, trazo);

		verticeSVG += String.format("<circle cx='%d' cy='%d' r='%d' stroke='black' stroke-width='%.1f' fill='white' />\n",
			x, y, radio, trazo);

		return verticeSVG += String.format("<text fill='black' font-family='sans-serif' font-size='%.1f' x='%d' y='%d' text-anchor='middle'>%s</text>\n",
			texto, x, y + 6, vertice.get());
	}

	/**
	 * Recorre el Arbol Binario en orden bfs imprimiendo el codigo de cada vertice que devuelve el metodo 
	 * {@link #vertice}
	 * @param x posicion del centro del vertice con respecto al eje 'x'.
	 * @param y posicion del centro del vertice con respecto al eje 'y'.
	 * @param radio radio del vertice.
	 * @param distancia distancia con respecto al eje 'x' con sus hijos.
	 * @param vertice un vertice del Arbol Binario.
	 */
	protected void bfsSVG(int x, int y, int radio, int distancia, VerticeArbolBinario<Integer> vertice) {
		System.out.println(vertice(x,y,radio,distancia,vertice));

		if (vertice.hayIzquierdo())
			bfsSVG(x - distancia, y + 120, radio, (int)distancia/2, vertice.izquierdo());


		if (vertice.hayDerecho())
			bfsSVG(x + distancia, y + 120, radio, (int)distancia/2, vertice.derecho());
	}

	/**
	 * Imprime el codigo svg que representa al Arbol Binario.
	 */
	@Override public void imprimirCodigoSVG() {
		if (arbolBinario.esVacia()) {
			vacio();
			return;
		}

		int altura = arbolBinario.altura();
		int distancia = (int)Math.pow(2,altura+1)*10 + 20;
		int x = distancia * 2 + 40;
		int y = 40;
		int radio = 20;

		if (altura > 5) {
			radio = 16;
			distancia = (int)Math.pow(2,altura)*10;
			if (distancia < 0) {
				distancia = distancia*-1;
				distancia += (altura-5)*60*2 + (altura-5)*200;
			}
			x = distancia * 2 + 40;
		}

		System.out.printf("<svg width='%d' height='%d' >\n\n", distancia * 4 + 80, (altura + 1) * 120);
		bfsSVG(x,y,radio,distancia, arbolBinario.raiz());
		System.out.println("</svg>");
	}

	/**
	 * Recorre el Arbol Binario en orden bfs agregando a una lista el codigo de cada vertice que devuelve 
	 * el metodo {@link #vertice}
	 * @param x posicion del centro del vertice con respecto al eje 'x'.
	 * @param y posicion del centro del vertice con respecto al eje 'y'.
	 * @param radio radio del vertice.
	 * @param distancia distancia con respecto al eje 'x' con sus hijos.
	 * @param vertice un vertice del Arbol Binario.
	 * @param codigo lista que agrega el codigo svg de cada vertice.
	 */
	protected void bfsSVG(int x, int y, int radio, int distancia, VerticeArbolBinario<Integer> vertice, Lista<T> codigo) {
		codigo.agrega(vertice(x,y,radio,distancia,vertice));

		if (vertice.hayIzquierdo())
			bfsSVG(x - distancia, y + 120, radio, (int)distancia/2, vertice.izquierdo());


		if (vertice.hayDerecho())
			bfsSVG(x + distancia, y + 120, radio, (int)distancia/2, vertice.derecho());
	}

	/**
	 * Regresa una lista con el orden que debe tener el codigo svg para representar al Arbol Binario.
	 * @return lista con el orden que debe tener el codigo svg para representar al Arbol Binario.
	 */
	@Override public abstract Lista<String> codigoSVG(){
		Lista<T> arbol = new Lista<T>();

		if (arbolBinario.esVacia()) {
			arbol.agrega(vacio());
			return arbol;		
		}

		int altura = arbolBinario.altura();
		int distancia = (int)Math.pow(2,altura+1)*10 + 20;
		int x = distancia * 2 + 40;
		int y = 40;
		int radio = 20;

		if (altura > 5) {
			radio = 16;
			distancia = (int)Math.pow(2,altura)*10;
			if (distancia < 0) {
				distancia = distancia*-1;
				distancia += (altura-5)*60*2 + (altura-5)*200;
			}
			x = distancia * 2 + 40;
		}

		arbol.agrega(String.format("<svg width='%d' height='%d' xmlns='http://www.w3.org/2000/svg'>\n", distancia * 4 + 80, (altura + 1) * 120));
		bfsSVG(x,y,radio,distancia, arbolBinario.raiz(), arbol);
		arbol.agrega(String.format("</svg>"));
		return arbol;
	}
}
