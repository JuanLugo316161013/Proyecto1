package mx.unam.ciencias.edd.proyecto2;
/**
 * Se lanza si se solicito una Estrutura de Datos que no existe.
 */
public class ExcepcionEstructuraInvalida extends RuntimeException {
	
	/**
	 * Constructor vacio.
	 */
	public ExcepcionEstructuraInvalida() {}

	/**
     * Constructor que recibe un mensaje para el usuario.
     * @param mensaje un mensaje que verá el usuario cuando ocurra la excepción.
     */
	public ExcepcionEstructuraInvalida(String mensaje) {super(mensaje);}
}