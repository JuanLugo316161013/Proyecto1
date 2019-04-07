package mx.unam.ciencias.edd.proyecto2;
/**
 * Se lanza si se quiere pasar más de una Estructura de Datos.
 */
public class ExcepcionEntradaSobrecargada extends RuntimeException {
	
	/**
	 * Construtor vacío.
	 */
	public ExcepcionEntradaSobrecargada() {}

	/**
     * Constructor que recibe un mensaje para el usuario.
     * @param mensaje un mensaje que verá el usuario cuando ocurra la excepción.
     */
	public ExcepcionEntradaSobrecargada(String mensaje) {super(mensaje);}
}