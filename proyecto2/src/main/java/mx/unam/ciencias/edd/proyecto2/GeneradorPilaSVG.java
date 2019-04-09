package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Pila;
import mx.unam.ciencias.edd.Lista;
/**
 * Genera codigo SVG que representa a un Pila.
 */
public class GeneradorPilaSVG implements GeneradorEstructuraSVG {

	private Pila<Integer> Pila;
	/**
	 * Constructor vacío.
	 */
	private GeneradorPilaSVG() {}

	/**
	 * Constructor que recibe una Lista de elementos del Pila.
	 * @throws ExcepcionFormatoEquivocado si algun elemento es caracter no imprimible.
	 * @throws NumberFormatException si algun elemento no es un numero entero.
	 */
	public GeneradorPilaSVG(Lista<String> elementos) {
		Pila = new Pila<Integer>();
		for(String numero : elementos)
			if (numero.charAt(0) < 32)
				throw new ExcepcionFormatoEquivocado();
			else
				Pila.mete(new Integer(numero));
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