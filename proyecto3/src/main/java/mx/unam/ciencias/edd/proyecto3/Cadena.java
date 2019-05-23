package mx.unam.ciencias.edd.proyecto3;

import java.text.Normalizer;
/**
 * Clase que contiene una una cadena de caracteres, y la misma cadena pero en su forma 
 * normalizada (sin acentos, y en minusculas).
 */
public class Cadena {

	/* Cadena original */
	private String cadena;

	/* Cadena normalizada. */
	private String cadenaNormalizada;

	/**
	 * Constructor vacío.
	 */
	private Cadena() {}

	/**
	 * Constructor que recibe un String que será la cadena interna asociada.
	 * @param cadena String que será la cadena asociada a Cadena.
	 */
	public Cadena(String cadena) {
		this.cadena = cadena;
		cadenaNormalizada = Normalizer.normalize(cadena,Normalizer.Form.NFKD);
		cadenaNormalizada = cadenaNormalizada.replaceAll("[^a-zA-Z0-9]", "");
	}

	/**
	 * Regresa su cadena de caracteres asociado.
	 * @return cadena asociada.
	 */
	public String getCadena() {return cadena;}

	/**
	 * Regresa la cadena de caracteres normalizada.
	 * @return cadena normalizada.
	 */
	private String getCadenaNormalizada() {return cadenaNormalizada;}

	@Override public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") Cadena cadena = (Cadena)o;
        return cadenaNormalizada.equals(cadena.getCadenaNormalizada());
	}

	/**
	 * Devuelve la dispersion del objeto de la cadena normalizada.
	 * @return dispersion de la cadena normalizada.
	 */
	@Override public int hashCode() {return cadenaNormalizada.hashCode();}
}