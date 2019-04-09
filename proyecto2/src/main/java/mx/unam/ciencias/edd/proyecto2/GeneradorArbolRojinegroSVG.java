package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Lista;
/**
 * Genera codigo SVG que representa a un ArbolRojinegro.
 */
public class GeneradorArbolRojinegroSVG implements GeneradorEstructuraSVG {

	private ArbolRojinegro<Integer> arbolRojinegro;
	/**
	 * Constructor vacío.
	 */
	private GeneradorArbolRojinegroSVG() {}

	/**
	 * Constructor que recibe una Lista de elementos del ArbolRojinegro.
	 * @throws ExcepcionFormatoEquivocado si algun elemento es caracter no imprimible.
	 * @throws NumberFormatException si algun elemento no es un numero entero.
	 */
	public GeneradorArbolRojinegroSVG(Lista<String> elementos) {
		arbolRojinegro = new ArbolRojinegro<Integer>();
		for(String numero : elementos)
			if (numero.charAt(0) < 32)
				throw new ExcepcionFormatoEquivocado();
			else
				arbolRojinegro.agrega(new Integer(numero));
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
	@Override public void imprimirCodigoSVG() {System.out.println(arbolRojinegro.toString());}
}