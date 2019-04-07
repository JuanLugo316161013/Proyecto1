package mx.unam.ciencias.edd.proyecto2;

/**
 * Se lanza si el numero de vertices en el formato de peticion es impar.
 */
public class ExcepcionVerticesImpares extends RuntimeException {

	/**
	 * Constructor vacío.
	 */
	public ExcepcionVerticesImpares() {}

	/**
     * Constructor que recibe un mensaje para el usuario.
     * @param mensaje un mensaje que verá el usuario cuando ocurra la excepción.
     */
	public ExcepcionVerticesImpares(String mensaje) {super(mensaje);}
}