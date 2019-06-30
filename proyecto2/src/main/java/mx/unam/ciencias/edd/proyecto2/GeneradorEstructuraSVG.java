package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
/**
 * Clase que genera codigo svg que representa una estructura de datos.
 */
public abstract class GeneradorEstructuraSVG<T>{
	/**
	 * Imprime en la salida estandar el codigo svg que representa la estructura de datos.
	 */
	public abstract void imprimirCodigoSVG();

	/**
	 * Regresa una lista con el orden que debe tener el codigo svg para representar a la estructura de datos.
	 * @return lista con el orden que debe tener el codigo svg para representar a la estructura de datos.
	 */
	public abstract Lista<String> codigoSVG(){};

	/**
	 * Regresa el codigo svg que representa una estructura vacia.
	 * @return codigo svg que representa una estructura vacia.
	 */
	protected String vacio() {
		String vacio = "";
		vacio += "<svg width='200' height='200' xmlns='http://www.w3.org/2000/svg'>\n\n";
		vacio += "<circle cx='100' cy='100' r='50' stroke='black' stroke-width='3' fill='white' />\n";
		vacio += "<line x1='160' y1='40' x2='40' y2='160' stroke='black' stroke-width='3' />\n";
		vacio += "\n</svg>";
		return vacio;
	}
}