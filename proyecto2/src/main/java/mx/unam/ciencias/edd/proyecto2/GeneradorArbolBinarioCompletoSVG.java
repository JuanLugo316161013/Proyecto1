package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.ArbolBinarioCompleto;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeArbolBinario;
/**
 * Clase que genera codigo svg que representa un Arbol Binario Completo.
 */
public class GeneradorArbolBinarioCompletoSVG<T> extends GeneradorArbolBinarioSVG<T> {

	/**
	 * Constructor que recibe una lista con los elementos de un Arbol Binario Completo.
	 * @param elementos elementos del Arbol Binario Completo.
	 */
	public GeneradorArbolBinarioCompletoSVG(Lista<T> elementos) {	
		super(elementos);
	}

	/**
	 * Devuelve una instancia de un Arbol Binario que extiende de {@link ArbolBinario}.
	 * @return instancia de {@link ArbolBinarioCompleto}.
	 */
	@Override protected ArbolBinario<T> nuevoArbolBinario() {
		return new ArbolBinarioCompleto<T>();
	}
}