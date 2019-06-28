package mx.unam.ciencias.edd.proyecto1;

/** 
 * Se lanza si en los argumentos de la entrada existe una bandera repetida.
 */
public class ExcepcionBanderaRepetida extends RuntimeException {

	/**
	 * Constructor vacío.
	 */
	public ExcepcionBanderaRepetida() {}

	/** 
	 * Constructor que recibe un mensaje para el usuario.
     * @param mensaje mensaje con detalles de la excepcion.
     */
	public ExcepcionBanderaRepetida(String mensaje) {super(mensaje);} 
}