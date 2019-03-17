package mx.unam.ciencias.edd.proyecto1;
/**
 * Clase que compara cadenas basada en la comparación de cadenas de
 * caracteres Sort de Unix. (Clase que envuelve a String)
 */

public class Cadena implements Comparable<Cadena>{

	/* Cadena de caracteres de Cadena*/
	private String  cadena;

	/**
	 * Constructor vacío de Cadena
	 */
	public Cadena() {}

	/**
	 * Contructor que recibe e inicializa a la cadena de caracteres 
	 * @param cadena cadena de caracteres (String)
	 */
	public Cadena(String cadena) {
		this.cadena = cadena;
	}

	/**
	 * Regresa la cadena de caracteres de la Cadena
	 * @return cadena de caracteres de Cadena (String)
	 */
	public String getCadena() {return cadena;}

	/**
	 * Define la cadena de caracteres de la Cadena
	 * @param cadena cadena de caracteres (String)
	 */
	public void setCadena(String cadena) {
		this.cadena = cadena;
	}

	/**
	 * Compara dos Cadenas por sus cadenas de caracteres basado en la comparacion
	 * de cadenas de caracteres Sort de Unix.
	 * @param cadena cadena a comparar
	 * @return si la cadena primera es mayor regresa un numero mayor a 0,
			   si son iguales regresa 0
			   si la primera es menor regresa un numero negativo
	 */
	@Override public int compareTo(Cadena cadena) {
		if (cadena == null)
			throw new NullPointerException();

		String cadena1 = this.cadena.trim(), cadena2 = cadena.getCadena().trim();
		return cadena1.compareTo(cadena2);
	}

	@Override public boolean equals(Object objeto){
		if (objeto == null || getClass() != objeto.getClass())
            return false;

        @SuppressWarnings("unchecked") Cadena cadena = (Cadena) objeto;

		return this.cadena.equals(cadena.getCadena());
	}
} 