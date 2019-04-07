package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.ArbolBinarioOrdenado;
import mx.unam.ciencias.edd.Lista;
/**
 * Genera codigo SVG que representa a un ArbolBinarioOrdenado.
 */
public class GeneradorArbolBinarioOrdenadoSVG implements GeneradorEstructuraSVG {

	private ArbolBinarioOrdenado<Integer> arbolBinarioOrdenado;
	/**
	 * Constructor vacío.
	 */
	private GeneradorArbolBinarioOrdenadoSVG() {}

	/**
	 * Constructor que recibe una Lista de elementos del ArbolAVL.
	 * @throws ExcepcionFormatoEquivocado si algun elemento es caracter no imprimible.
	 * @throws NumberFormatException si algun elemento no es un numero entero.
	 */
	public GeneradorArbolBinarioOrdenadoSVG(Lista<String> elementos) {
		arbolBinarioOrdenado = new ArbolBinarioOrdenado<Integer>();
		for(String numero : elementos)
			if (numero.charAt(0) < 32)
				throw new ExcepcionFormatoEquivocado();
			else
				arbolBinarioOrdenado.agrega(new Integer(numero));
	}

	/**
	 * Devuelve un arreglo con el codigo SVG, en orden.
	 */
	@Override public String[] codigoSVG() {
		return null;
	}

	/**
	 * Imprime el codigo SVG que representa a la Estructura de Datos.
	 */
	@Override public void imprimirCodigoSVG() {}
}