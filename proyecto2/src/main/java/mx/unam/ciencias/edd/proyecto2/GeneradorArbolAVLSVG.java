package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.Lista;
/**
 * Genera codigo SVG que representa a un ArbolAVL.
 */
public class GeneradorArbolAVLSVG implements GeneradorEstructuraSVG {

	private ArbolAVL<Integer> arbolAVL;
	/**
	 * Constructor vacío.
	 */
	private GeneradorArbolAVLSVG() {}

	/**
	 * Constructor que recibe una Lista de elementos del ArbolAVL.
	 * @throws ExcepcionFormatoEquivocado si algun elemento es caracter no imprimible.
	 * @throws NumberFormatException si algun elemento no es un numero entero.
	 */
	public GeneradorArbolAVLSVG(Lista<String> elementos) {
		arbolAVL = new ArbolAVL<Integer>();
		for(String numero : elementos)
			if (numero.charAt(0) < 32)
				throw new ExcepcionFormatoEquivocado();
			else
				arbolAVL.agrega(new Integer(numero));
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