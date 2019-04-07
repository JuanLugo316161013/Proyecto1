package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import java.util.NoSuchElementException;
/**
 * Representa una estructura de datos en codigo SVG.
 */
public class EstructurasDeDatosSVG {
	
	/** Generador de codigo SVG que representa una estructura de datos */
	GeneradorEstructuraSVG estructura;

	/**
	 * Constructor vacío.
	 */
	private EstructurasDeDatosSVG() {}

	/**
	 * Constructor que recibe una lista con el nombre de la estructura y sus elementos,
	 * define que estructura sera representada en SVG.
	 * @param estructura lista con el nombre de la estructura y sus elementos.
	 * @throws ExcepcionEstructuraInvalida si la estructura no es :
	 * 				
	 * 				- ArbolAVL.
	 * 				- ArbolBinarioCompleto.
	 * 				- ArbolBinarioOrdenado.
	 * 				- ArbolRojinegro.
	 *				- Arreglos.
	 *				- Cola.
	 *				- Grafica.
	 * 				- Lista.
	 *				- Pila.
	 */
	public EstructurasDeDatosSVG(Lista<String> estructura)  {

		String estructuraDeDatos = estructura.eliminaPrimero();

		if (estructuraDeDatos.equals("ArbolAVL")) {
			this.estructura = new GeneradorArbolAVLSVG(estructura);
			return;
		}

		if (estructuraDeDatos.equals("ArbolBinarioCompleto")) {
			this.estructura = new GeneradorArbolBinarioCompletoSVG(estructura);
			return;
		}

		if (estructuraDeDatos.equals("ArbolBinarioOrdenado")) {
			this.estructura = new GeneradorArbolBinarioOrdenadoSVG(estructura);
			return;
		}

		if (estructuraDeDatos.equals("ArbolRojinegro")) {
			this.estructura = new GeneradorArbolRojinegroSVG(estructura);
			return;
		}

		if (estructuraDeDatos.equals("Arreglos")) {
			this.estructura = new GeneradorArreglosSVG(estructura);
			return;
		}

		if (estructuraDeDatos.equals("Cola")) {
			this.estructura = new GeneradorColaSVG(estructura);
			return;
		}

		if (estructuraDeDatos.equals("Grafica")) {
			this.estructura = new GeneradorGraficaSVG(estructura);
			return;
		}

		if (estructuraDeDatos.equals("Lista")) {
			this.estructura = new GeneradorListaSVG(estructura);
			return;
		}

		if (estructuraDeDatos.equals("Pila")) {
			this.estructura = new GeneradorPilaSVG(estructura);
			return;
		}

		throw new ExcepcionEstructuraInvalida();
	}

	/**
	 * Imprime el codigo SVG que representa a la estrutura definida.
	 * @throws NoSuchElementException si no se definio bien la estructura.
	 */
	public void imprimirEstructuraSVG() {
		if (estructura == null) 
			throw new NoSuchElementException();

		estructura.imprimirCodigoSVG();
	}
}