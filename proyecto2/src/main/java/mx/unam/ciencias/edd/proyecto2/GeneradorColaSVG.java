package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Cola;
import mx.unam.ciencias.edd.Lista;
/**
 * Genera codigo SVG que representa a un Cola.
 */
public class GeneradorColaSVG implements GeneradorEstructuraSVG {

	private Cola<Integer> cola;
	/**
	 * Constructor vacío.
	 */
	private GeneradorColaSVG() {}

	/**
	 * Constructor que recibe una Lista de elementos del Cola.
	 * @throws ExcepcionFormatoEquivocado si algun elemento es caracter no imprimible.
	 * @throws NumberFormatException si algun elemento no es un numero entero.
	 */
	public GeneradorColaSVG(Lista<String> elementos) {
		cola = new Cola<Integer>();
		for(String numero : elementos)
			if (numero.charAt(0) < 32)
				throw new ExcepcionFormatoEquivocado();
			else
				cola.mete(new Integer(numero));
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