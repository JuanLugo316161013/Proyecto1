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
	protected void vacio() {
		System.out.println("<svg width='200' height='200' >\n");
		System.out.println("<circle cx='100' cy='100' r='50' stroke='black' stroke-width='3' fill='white' />");
		System.out.println("<line x1='160' y1='40' x2='40' y2='160' stroke='black' stroke-width='3' />");
		System.out.println("\n</svg>");
	}

}