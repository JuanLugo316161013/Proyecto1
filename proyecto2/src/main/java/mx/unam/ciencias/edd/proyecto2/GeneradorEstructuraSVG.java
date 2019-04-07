package mx.unam.ciencias.edd.proyecto2;

import java.util.NoSuchElementException;
/**
 * Genera un codigo SVG que representa una Estructura de datos.
 */
public abstract interface GeneradorEstructuraSVG{

	/**
	 * Devuelve un arreglo con el codigo SVG, en orden.
	 * @return un arreglo con el codigo SVG que representa a la Estructura.
	 * @throws NoSuchElementException si la estructura es null.
	 */
	public String[] codigoSVG();

	/**
	 * Imprime el codigo SVG que representa a la Estructura de Datos.
	 * @throws NoSuchElementException si la estructura es null.
	 */
	public void imprimirCodigoSVG();
}