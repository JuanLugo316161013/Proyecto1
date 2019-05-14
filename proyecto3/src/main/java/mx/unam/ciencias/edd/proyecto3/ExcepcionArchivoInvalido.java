package mx.unam.ciencias.edd.proyecto3;

/** 
 * Se lanza si el archivo no cumple alguna de las siguientes condiciones
 * <ul>
 *    <li>El archivo es un archivo normal.</li>
 *    <li>El archivo existe.</li>
 *    <li>Se puede leer del archivo.</li>
 * </ul>
 */
public class ExcepcionArchivoInvalido extends RuntimeException {

	/**
	 * Constructor vacío.
	 */
	public ExcepcionArchivoInvalido() {}

	/** 
	 * Constructor que recibe un mensaje para el usuario.
     * @param mensaje mensaje con detalles de la excepcion.
     */
	public ExcepcionArchivoInvalido(String mensaje) {super(mensaje);} 
}