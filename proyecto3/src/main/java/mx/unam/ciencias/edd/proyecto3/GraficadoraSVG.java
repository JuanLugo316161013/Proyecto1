package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;
/**
 * Clase que grafica una Lista de datos.1
 */
public class GraficadoraSVG{
	
	/**
	 * Constructor vacío.
	 */
	private GraficadoraSVG() {}

	/**
	 * Devuleve una grafica de pastel en codigo svg de los datos.
	 * @param datos datos de la grafica.
	 * @param valorTotal valor total de lo datos.
	 * @return grafica de pastel en codigo svg.
	 */
	public static <T extends Dato<T>> String graficaPastel(Lista<T> datos, int valorTotal) {
		return null;
	}

	/**
	 * Devuleve una grafica de barras en codigo svg de los datos.
	 * @param datos datos de la grafica.
	 * @param valorTotal valor total de lo datos.
	 * @return grafica de pastel en codigo svg.
	 */
	public static <T extends Dato<T>> String graficaBarras(Lista<T> datos, int valorTotal) {
		return null;
	}
}