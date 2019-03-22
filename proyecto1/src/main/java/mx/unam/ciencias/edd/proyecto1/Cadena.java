package mx.unam.ciencias.edd.proyecto1;

import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import java.text.Collator;

/**
 * Clase que compara cadenas basada en la comparación de cadenas de
 * caracteres Sort de Unix. (Clase que envuelve a String).
 */

public class Cadena implements Comparable<Cadena>{

	/** Cadena de caracteres de Cadena*/
	private String  cadena;

	/**
	 * Constructor vacío de Cadena
	 * No se puede crear un objeto cadena sin su cadena
	 * de caracteres.
	 */
	private Cadena() {}

	/**
	 * Contructor que recibe e inicializa a la cadena de caracteres
	 * @param cadena cadena de caracteres (String).
	 * @throws IllegalAccessException si la cadena es null.
	 */
	public Cadena(String cadena) {
		if (cadena == null)
			throw new IllegalArgumentException();

		this.cadena = cadena;
	}

	/**
	 * Regresa la cadena de caracteres de la Cadena.
	 * @return cadena de caracteres de Cadena (String).
	 * @throws NoSuchElementException si la cadena no tiene su cadena
	 *								  de caracteres.
	 */
	public String getCadena() {
		if (cadena == null)
			throw new NoSuchElementException();

		return cadena;
	}

	/**
	 * Define la cadena de caracteres de la Cadena.
	 * @param cadena cadena de caracteres (String).
	 * @throws IllegalAccessException si la cadena es null.
	 */
	public void setCadena(String cadena) {
		if (cadena == null)
			throw new IllegalArgumentException();

		this.cadena = cadena;
	}

	/**
	 * Compara dos Cadenas por sus cadenas de caracteres basado en la comparacion
	 * de cadenas de caracteres Sort de Unix.
	 * @param cadena cadena a comparar
	 * @return si la cadena primera es mayor regresa un numero mayor a 0,
	 *		   si son iguales regresa 0
	 *	 	   si la primera es menor regresa un numero negativo.
	 * @throws NullPointerException si la cadena de Cadena es null.
	 * @throws IllegalAccessException si la cadena que se pasa es null.
	 */
	@Override public int compareTo(Cadena cadena) {
		if (this.cadena == null)
			throw new NoSuchElementException();

		if (cadena == null || cadena.getCadena() == null)
			throw new IllegalArgumentException();

		String cadena1 = this.cadena, cadena2 = cadena.getCadena();
		Collator comparador = Collator.getInstance();
		comparador.setStrength(Collator.PRIMARY);
		return comparador.compare(cadena1.replaceAll("[^\\p{L}\\p{Z}]", ""),
			                      cadena2.replaceAll("[^\\p{L}\\p{Z}]", ""));
	}

	/**
	 * Determina si la cadena es igual a un objeto.
	 * @param objeto objeto a comparar.
	 * @return 'true' si son iguales,
	 *		   'false' si no.
	 */
	@Override public boolean equals(Object objeto){
		if (objeto == null || getClass() != objeto.getClass())
            return false;

		if (cadena == null)
        	return false;

        @SuppressWarnings("unchecked") Cadena cadenaObjeto = (Cadena) objeto;

		return cadena.equals(cadenaObjeto.getCadena());
	}
}
