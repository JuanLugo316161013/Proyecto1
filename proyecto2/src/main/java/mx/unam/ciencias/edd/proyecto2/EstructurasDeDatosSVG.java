package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import java.util.NoSuchElementException;
/**
 * Clase que dado el nombre y los elemntos de una estructura de datos, imprime el codigo svg que representa 
 * a dicha estructura.
 */
public class EstructurasDeDatosSVG<T> {

	/** Generador de codigo SVG que representa una estructura de datos */
	GeneradorEstructuraSVG estructura;

	/**
	 * Constructor vacío.
	 */
	private EstructurasDeDatosSVG() {}

	/**
	 * Constructor que recibe una lista con el nombre de la estructura de datos,
	 * y sus elementos.
	 * @param estructuraDeDatos nombre de la estructura de datos. 
	 * @param elementos elementos de la estructura de datos. 
	 * @throws ExcepcionEstructuraInvalida si el nombre de la estructura no esta en la siguiente lista.
	 * <ul>
	 *	 <li>ArbolAVL</li>				
	 *	 <li>ArbolBinarioCompleto</li>				
	 *	 <li>ArbolBinarioOrdenado</li>				
	 *	 <li>ArbolRojinegro</li>				
	 *	 <li>Cola</li>				
	 *	 <li>Grafica</li>				
	 *	 <li>Lista</li>
	 *	 <li>MonticuloArreglo</li>
	 *	 <li>MonticuloMinimo</li>
	 *	 <li>Pila</li>
	 * </ul>
	 */
	public EstructurasDeDatosSVG(String estructuraDeDatos, Lista<T> elementos)  {
		if (estructuraDeDatos.equals("ArbolAVL")) {
			this.estructura = new GeneradorArbolAVLSVG(elementos);
			return;
		}

		if (estructuraDeDatos.equals("ArbolBinarioCompleto")) {
			this.estructura = new GeneradorArbolBinarioCompletoSVG(elementos);
			return;
		}

		if (estructuraDeDatos.equals("ArbolBinarioOrdenado")) {
			this.estructura = new GeneradorArbolBinarioOrdenadoSVG(elementos);
			return;
		}

		if (estructuraDeDatos.equals("ArbolRojinegro")) {
			this.estructura = new GeneradorArbolRojinegroSVG(elementos);
			return;
		}

		if (estructuraDeDatos.equals("Cola")) {
			this.estructura = new GeneradorColaSVG(elementos);
			return;
		}

		if (estructuraDeDatos.equals("Grafica")) {
			this.estructura = new GeneradorGraficaSVG(elementos);
			return;
		}

		if (estructuraDeDatos.equals("Lista")) {
			this.estructura = new GeneradorListaSVG(elementos);
			return;
		}

		if (estructuraDeDatos.equals("MonticuloArreglo")) {
			this.estructura = new GeneradorMonticuloArregloSVG(elementos);
			return;
		}

		if (estructuraDeDatos.equals("MonticuloMinimo")) {
			this.estructura = new GeneradorMonticuloMinimoSVG(elementos);
			return;
		}

		if (estructuraDeDatos.equals("Pila")) {
			this.estructura = new GeneradorPilaSVG(elementos);
			return;
		}

		throw new ExcepcionEstructuraInvalida();
	}

	/**
	 * Imprime el codigo SVG que representa a la estrutura definida.
	 * @throws NoSuchElementException si no se definio bien la estructura.
	 */
	public void imprimirEstructuraSVG() {estructura.imprimirCodigoSVG();}
}
