package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Pila;
import mx.unam.ciencias.edd.Cola;
import java.util.NoSuchElementException;

/**
 * Clase que ordena cadenas de caracteres basado en el ordenamiento
 * Sort de Unix.
 */
public class OrdenadorLexicografico {

	/** Bandera -r, bandera de imprimir en orden inverso. */
	private boolean reversa;

	/** Bandera -o, bandera de salida a un archivo. */
	private boolean salida;

	/** Arbol que ordena agrega y ordena cadenas en tiempo logaritmico. */
	private ArbolRojinegro<Cadena> arbol;

	/**
	 * Contructor vacio.
	 * Inicializa la estructura que ordena las cadenas.
	 */
	public OrdenadorLexicografico() {
		arbol = new ArbolRojinegro<Cadena>();
	}

	/**
	 * Devuelve si el Ordenador Lexicografico es vacio.
	 * @return 'true' si la estructura que ordena es vacia
	 *         'false' si no lo es.
	 */
	public boolean esVacio() {
		return arbol.esVacia();
	}

	/**
	 * Regresa un ArbolRojinegro de tipo Cadena que es la estrutura,
	 * que ordena las cadena.
	 * @return ArbolRojinegro de tipo Cadena.
	 * @throws NoSuchElementException si el ordenador es vacio.
	 */
	public ArbolRojinegro<Cadena> getOrdenador() {
		if (arbol == null)
			throw new NoSuchElementException();

		return new ArbolRojinegro<Cadena>(arbol);
	}

	/**
	 * Regresa si la bandera -r esta activada
	 * @return si se ah pasado la bandera -r.
	 */
	public boolean bandera_R() {return reversa;}

	/**
	 * Regresa si la bandera -o esta activada.
	 * @return si se ah pasado la bandera -o.
	 */
	public boolean bandera_O() {return salida;}

	/**
	 * Agrega una cadena y la ordena.
	 * @param elemento cadena a ordenar.
	 * @throws IllegalArgumentException si el elemento el null.
	 */
	public void agrega(String elemento) {
		if (elemento == null)
			throw new IllegalArgumentException();

		Cadena cadena = new Cadena(elemento);
		arbol.agrega(cadena);
	}

	/**
	 * Define si una bandera es valida, y la inicializa,
	 * si no lo es no hace nada.
	 * @param bandera bandera que se paso.
	 * @throws IllegalArgumentException si la bandera es null.
	 */
	public void bandera(String bandera) {
		if (bandera == null)
			throw new IllegalArgumentException();

		if (bandera.equals("-r"))
			reversa = true;

		if (bandera.equals("-o"))
			salida = true;
	}

	/**
	 * Regresa el orden el contenido del ordenador lexicografico
	 * en orden.
	 * Si la bandera -o es falsa regresa null.
	 * @return un arreglo de de cadenas en orden o
	 *         null si la bandera -o es falsa;
	 * @throws NoSuchElementException si el ordenador es null.
	 */
	public String[] salida() {
		if (arbol.esVacia())
			throw new NoSuchElementException();

		String[] orden = new String[arbol.getElementos()];
		Pila<Cadena> pila;
		Cola<Cadena> cola;
		String cadena = "";

		cola = new Cola<Cadena>();
		pila = new Pila<Cadena>();

		for (Cadena vertice : arbol) {
			cola.mete(vertice);
			pila.mete(vertice);
		}

		if (reversa)
			for (int i = 0; !pila.esVacia(); i++)
				orden[i] = pila.saca().getCadena() + "\n";
		else
			for (int i = 0; !cola.esVacia(); i++)
				orden[i] = cola.saca().getCadena() + "\n";

		return orden;
	}

	/**
	 * Regresa una representacion de las cadenas del
	 * Ordenador Lexicografico, ordenado.
	 * @return representacion de un Ordenador Lexicografico.
	 */
	@Override public String toString() {
		if (arbol.esVacia())
			return "";

		Pila<Cadena> pila;
		Cola<Cadena> cola;
		String cadena = "";

		cola = new Cola<Cadena>();
		pila = new Pila<Cadena>();

		for (Cadena vertice : arbol) {
			cola.mete(vertice);
			pila.mete(vertice);
		}

		if (reversa)
			while (!pila.esVacia())
				cadena += pila.saca().getCadena() + "\n";
		else
			while(!cola.esVacia())
				cadena += cola.saca().getCadena() + "\n";

		return cadena;
	}

	/**
	 * Determina si un Ordenador Lexicografico es igual a un objeto.
	 * @param objeto objeto a comparar.
	 * @return 'true' si son iguales
	 *		   'false' si no lo son.
	 */
	@Override public boolean equals(Object objeto) {
		if (objeto == null || getClass() != objeto.getClass())
            return false;

		if (arbol == null)
			return false;

		@SuppressWarnings("unchecked") OrdenadorLexicografico ordenador = (OrdenadorLexicografico)objeto;

		try {
			return arbol.equals(ordenador.getOrdenador()) &&
			bandera_R() == ordenador.bandera_R() &&
			ordenador.bandera_O() == bandera_O();
		} catch (NoSuchElementException nsee) {return true;}
	}
}
