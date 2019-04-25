package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
/**
 * Genera codigo SVG que representa a un MonticuloArreglo.
 */
public class GeneradorMonticuloArregloSVG extends GeneradorArreglosSVG {

	/**
	 * Constructor que recibe una Lista de elementos del MonticuloArreglo.
	 * @throws ExcepcionFormatoEquivocado si algun elemento es caracter no imprimible.
	 * @throws NumberFormatException si algun elemento no es un numero entero.
	 */
	public GeneradorMonticuloArregloSVG(Lista<String> elementos) {
		super(elementos);
	}
}