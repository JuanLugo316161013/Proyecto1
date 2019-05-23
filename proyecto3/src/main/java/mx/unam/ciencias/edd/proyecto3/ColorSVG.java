package mx.unam.ciencias.edd.proyecto3;

/**
 * Enumeracion para colores SVG.
 */
public enum ColorSVG {

	/** Colores para graficas en svg */
	BURLYWOOD("burlywood"), MARRON("maroon"), PURPLE("purble"), LEMONCHIFFON("lemonchiffon"), 
	MEDIUMBLUE("mediumblue"), MEDIUMSEAGREEN("mediumseagreen"), YELLOW("yellow"), ORANGE("orange"), 
	ORANGERED ("orangered"), DARKRED("darkred"), TOMATO("tomato"), DARKSLATEBLUE("darkslateblue"),
	SEAGREEN("seagreen"), DARKBLUE("darkblue"), LIGTHSALMON("lightsalmon"),KHAKI("khaki"),
	MEDIUMPURPLE("mediumpurple"), PINK("pink"); 
	
	/** Nombre del color en svg. */
	private String color;

	/**
	 * Constructor que recibe el nombre del color.
	 * @param color nombre del color.
	 */
	private ColorSVG(String color) {this.color = color;}

	/**
	 * Devuelve el nombre del color svg.
	 * @return nombre del color svg.
	 */ 
	@Override public String toString() {return color;}
}