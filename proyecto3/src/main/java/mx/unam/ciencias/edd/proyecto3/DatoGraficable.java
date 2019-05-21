package mx.unam.ciencias.edd.proyecto3;


/**
 * Interfaz para un dato en una grafica, el dato debe poder darnos su elemento, y su concurrencia
 * (la cantidad de veces que el elemento participa en algun suceso).
 */
public interface DatoGraficable<T> {

	/** 
	 * Devuelve el elemento del dato en la grafica.
	 * @return elemento del deto en la grafica.
	 */
	public T get();

	/**
	 * Devuelve la concurrencia del elemento.
	 * @return concurrencia del elemento.
	 */
	public int concurrencia();
}