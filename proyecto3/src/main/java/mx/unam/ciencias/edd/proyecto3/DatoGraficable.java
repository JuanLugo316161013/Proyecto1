package mx.unam.ciencias.edd.proyecto3;


/**
 * Interfaz para un dato en una grafica, el dato debe poder darnos una representación del mismo, 
 * y su frecuencia (la cantidad de veces que el elemento participa en algun suceso).
 */
public interface DatoGraficable<T> {

	/** 
	 * Devuelve la representacion del dato a graficar.
	 * @return representacion del dato.
	 */
	public String get();

	/**
	 * Devuelve la frecuencia que el dato hace presencia en un suceso.
	 * @return frecuencia del dato.
	 */
	public int frecuencia();
}