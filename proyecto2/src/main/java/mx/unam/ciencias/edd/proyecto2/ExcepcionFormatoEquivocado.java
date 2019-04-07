package mx.unam.ciencias.edd.proyecto2;
/**
 * Se lanza si el formato para pedir una imagen de una Estrutura de datos
 * es invalida.
 */
public class ExcepcionFormatoEquivocado extends RuntimeException{
	
	/**
	 * Constructor vacío.
	 */
	public ExcepcionFormatoEquivocado() {}

	/**
     * Constructor que recibe un mensaje para el usuario.
     * @param mensaje un mensaje que verá el usuario cuando ocurra la excepción.
     */
	public ExcepcionFormatoEquivocado(String mensaje) {super(mensaje);}
}