package mx.unam.ciencias.edd.proyecto2;
/**
 * <p>Clase abstracta que genera genera codigo svg para estructuras lineales restringidas a operaciones
 * mete/saca/mira.</p>
 * <p>Las clases que extienden de esta clase son: </p>
 * <ul>
 *     <li>{@link GeneradorColaSVG}</li>
 *     <li>{@link GeneradorPilaSVG}</li>
 * </ul>
 */
public abstract class GeneradorMeteSacaSVG<T> extends GeneradorEstructuraSVG<T> {

	/** Estructura lineal restringida a operaciones mete/saca/mira */
	MeteSaca<T> meteSaca;

	public GeneradorMeteSacaSVG()
}