package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.ArbolBinarioOrdenado;
import mx.unam.ciencias.edd.Lista;
/**
 * Clase que genera codigo svg que representa un Arbol Binario Ordenado.
 */
public class GeneradorArbolBinarioOrdenadoSVG<T> extends GeneradorArbolBinarioSVG<T> {

	/**
	 * Constructor que recibe una lista con los elementos de un Arbol Binario Ordenado.
	 * @param elementos elementos del Arbol Binario Ordenado.
	 */	
	public GeneradorArbolBinarioOrdenadoSVG(Lista<T> elementos) {
		T[] elementosArreglo = new T[lista.getElementos()];
		int i = 0;
		for (T elemento : elementos)
			elementosArreglo[i++] = elemento;
		arbolBinario = new nuevoArbolBinario();
		llenaArbolOrdenado(elementosArreglo);
	}

	/**
	 * Devuelve una instancia de un Arbol Binario que extiende de {@link ArbolBinario}.
	 * @return instancia de {@link ArbolBinarioOrdenado}.
	 */
	@Override protected ArbolBinario<T> nuevoArbolBinario() {
		return new ArbolBinarioOrdenado<T>();
	}

	/**
	 * Llena un Arbol Binario Ordenado, pero revolviendo los elementos dados.
	 * @param elementos elementos dados para el Arbol Binario Ordenado.
	 */
	private void llenaArbolOrdenado(T[] elementos) {
		Conjunto<Integer> numeros = new Conjunto<Integer>(elementos.length);
		int j;
		for (int i = 0; i < elementos.length; i++) {
			j = (int) (Math.random()*18);
			while (numeros.contiene(j))
				j = (int) (Math.random()*18);
			numeros.agrega(j);
			arbolBinario.agrega(elemento[j]);
		}
	}
}