package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.ArbolBinarioCompleto;
import mx.unam.ciencias.edd.Lista;
/**
 * Genera codigo SVG que representa a un ArbolBinarioCompleto.
 */
public class GeneradorArbolBinarioCompletoSVG implements GeneradorEstructuraSVG {

	private ArbolBinarioCompleto<Integer> arbolBinarioCompleto;
	/**
	 * Constructor vacío.
	 */
	private GeneradorArbolBinarioCompletoSVG() {}

	/**
	 * Constructor que recibe una Lista de elementos del ArbolBinarioCompleto.
	 * @throws ExcepcionFormatoEquivocado si algun elemento es caracter no imprimible.
	 * @throws NumberFormatException si algun elemento no es un numero entero.
	 */
	public GeneradorArbolBinarioCompletoSVG(Lista<String> elementos) {
		arbolBinarioCompleto = new ArbolBinarioCompleto<Integer>();
		for(String numero : elementos)
			if (numero.charAt(0) < 32)
				throw new ExcepcionFormatoEquivocado();
			else
				arbolBinarioCompleto.agrega(new Integer(numero));
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