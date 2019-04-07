package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
/**
 * Genera codigo SVG que representa a un Lista.
 */
public class GeneradorListaSVG implements GeneradorEstructuraSVG {

	private Lista<Integer> lista;
	/**
	 * Constructor vacío.
	 */
	private GeneradorListaSVG() {}

	/**
	 * Constructor que recibe una Lista de elementos de Lista.
	 * @throws ExcepcionFormatoEquivocado si algun elemento es caracter no imprimible.
	 * @throws NumberFormatException si algun elemento no es un numero entero.
	 */
	public GeneradorListaSVG(Lista<String> elementos) {
		lista = new Lista<Integer>();
		for(String numero : elementos)
			if (numero.charAt(0) < 32)
				throw new ExcepcionFormatoEquivocado();
			else
				lista.agrega(new Integer(numero));
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