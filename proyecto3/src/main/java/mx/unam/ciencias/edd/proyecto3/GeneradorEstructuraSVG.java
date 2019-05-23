package mx.unam.ciencias.edd.proyecto2;

/**
 * Genera un codigo SVG que representa una Estructura de datos.
 */
public abstract class GeneradorEstructuraSVG<T>{

	/**
	 * Imprime el codigo SVG que representa a la Estructura de Datos.
	 */
	public abstract void imprimirCodigoSVG();

	/**
	 * Imprime el codigo svg de una estructura vacía.
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