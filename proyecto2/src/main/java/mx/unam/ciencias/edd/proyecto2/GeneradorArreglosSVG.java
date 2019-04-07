package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
/**
 * Genera codigo SVG que representa a un Arreglos.
 */
public class GeneradorArreglosSVG implements GeneradorEstructuraSVG {

	private Integer[] arreglo;
	/**
	 * Constructor vacío.
	 */
	private GeneradorArreglosSVG() {}

	/**
	 * Constructor que recibe una Lista de elementos del Arreglos.
	 * @throws ExcepcionFormatoEquivocado si algun elemento es caracter no imprimible.
	 * @throws NumberFormatException si algun elemento no es un numero entero.
	 */
	public GeneradorArreglosSVG(Lista<String> elementos) {
		arreglo = new Integer[elementos.getElementos()];
		int i = 0;
		for(String numero : elementos)
			if (numero.charAt(0) < 32)
				throw new ExcepcionFormatoEquivocado();
			else
				arreglo[i++] = new Integer(numero);
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