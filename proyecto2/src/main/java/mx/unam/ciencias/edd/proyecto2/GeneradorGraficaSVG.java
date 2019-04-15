package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Lista;
/**
 * Genera codigo SVG que representa a un Grafica.
 */
public class GeneradorGraficaSVG implements GeneradorEstructuraSVG {

	private Grafica<Integer> grafica;
	/**
	 * Constructor vacío.
	 */
	private GeneradorGraficaSVG() {}

	/**
	 * Constructor que recibe una Lista de elementos del Grafica.
	 * @throws ExcepcionFormatoEquivocado si algun elemento es caracter no imprimible.
	 * @throws NumberFormatException si algun elemento no es un numero entero.
	 * @throws ExcepcionVerticesImpares si el numero de elementos son impares.
	 */
	public GeneradorGraficaSVG(Lista<String> elementos) {
		if (elementos.getElementos()%2 != 0)
				throw new ExcepcionVerticesImpares();

		grafica = new Grafica<Integer>();

		int i = 0;
		Integer[] parVertices = new Integer[2];
		for(String numero : elementos) {
			if (numero.isEmpty())
				throw new ExcepcionFormatoEquivocado();

			parVertices[i] = new Integer(numero);
			
			if (i == 1) {
				if (parVertices[0].equals(parVertices[1])) {
					grafica.agrega(parVertices[0]);
				} else {
					try {
						grafica.agrega(parVertices[0]);
						grafica.agrega(parVertices[1]);
				    } catch (IllegalArgumentException iae) {}
					grafica.conecta(parVertices[0],parVertices[1]);
				}
				i = 0;
			}
			i++; 
		}
	}

	/**
	 * Imprime el codigo SVG que representa a la Estructura de Datos.
	 */
	@Override public void imprimirCodigoSVG() {System.out.println(grafica.toString());}
}