package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.ArbolBinarioOrdenado;
import mx.unam.ciencias.edd.Lista;
/**
 * Genera codigo SVG que representa a un ArbolBinarioOrdenado.
 */
public class GeneradorArbolBinarioOrdenadoSVG extends GeneradorArbolBinarioSVG {

	/**
	 * Constructor que recibe una Lista de elementos del ArbolBinarioOrdenado.
	 * @throws ExcepcionFormatoEquivocado si algun elemento es caracter no imprimible.
	 * @throws NumberFormatException si algun elemento no es un numero entero.
	 */
	public GeneradorArbolBinarioOrdenadoSVG(Lista<String> elementos) {	
		super(elementos);
	}

	/**
	 * Devuelve una instancia de un nuevo arbol binario, usando una instancia de {@link ArbolBinarioOrdenado}
	 */
	@Override protected ArbolBinario<Integer> nuevoArbolBinario() {
		return new ArbolBinarioOrdenado<Integer>();
	}
}