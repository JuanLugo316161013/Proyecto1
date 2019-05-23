/**
 * Interfaz para un dato en una grafica, el dato debe poder darnos su elemento, y su frecuencia
 * (la cantidad de veces que el elemento participa en algun suceso).
 */
public interface DatoGraficable<T> {

	/** 
	 * Devuelve el elemento del dato en la grafica.
	 * @return elemento del deto en la grafica.
	 */
	public String get();

	/**
	 * Devuelve la frecuencia del elemento.
	 * @return frecuencia del elemento.
	 */
	public int frecuencia();
}