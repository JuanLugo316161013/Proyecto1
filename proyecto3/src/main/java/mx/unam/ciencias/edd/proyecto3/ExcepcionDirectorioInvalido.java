package mx.unam.ciencias.edd.proyecto3;

/** 
 * Se lanza si el archivo no cumple alguna de las siguientes condiciones:
 * <ul>
 *    <li>El archivo es un directorio.</li>
 *    <li>El directorio no se puede crear en caso de no existir.</li>
 *    <li>Se puede escribir en el directorio.</li>
 * </ul>
 */
public class ExcepcionDirectorioInvalido extends RuntimeException {

	/**
	 * Constructor vacío.
	 */
	public ExcepcionDirectorioInvalido() {}

	/** 
	 * Constructor que recibe un mensaje para el usuario.
     * @param mensaje mensaje con detalles de la excepcion.
     */
	public ExcepcionDirectorioInvalido(String mensaje) {super(mensaje);} 
}