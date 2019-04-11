package mx.unam.ciencias.edd.proyecto2;

import java.util.NoSuchElementException;
/**
 * Genera un codigo SVG que representa una Estructura de datos.
 */
public abstract interface GeneradorEstructuraSVG{

	/**
	 * Imprime el codigo SVG que representa a la Estructura de Datos.
	 * @throws NoSuchElementException si la estructura es null.
	 */
	public void imprimirCodigoSVG();
}