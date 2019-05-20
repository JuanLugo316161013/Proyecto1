package mx.unam.ciencias.edd.proyecto3;

import java.text.Normalizer;
/**
 * Clase que tiene que contiene una cadena ({@link String}) y misma cadena 'liquida',
 * diremos que una cadena es liquida si no tiene acentos y esta en minusculas.
 */
public class Cadena {

	/* Cadena original */
	private String cadenaOriginal;

	/* Cadena 'liquida' */
	private String cadenaLiquida;

	/**
	 * Constructor vacío.
	 */
	private Cadena() {}

	/**
	 * Constructor que recibe un String que será la cadena interna asociada.
	 * @param cadena String que será la cadena asociada a Cadena.
	 */
	public Cadena(String cadena) {
		cadenaOriginal = cadena;
		cadenaLiquida = Normalizer.normalize(cadena,Normalizer.Form.NFKD);
		cadenaLiquida = cadenaLiquida.replaceAll("[^a-zA-Z0-9]", "");
	}

	/**
	 * Regresa su String asociado.
	 * @return String asociado.
	 */
	public String getCadena() {return cadenaOriginal;}

	/**
	 * Regresa su String asociado en su forma 'liquida'.
	 * @return String asociado en su forma 'liquida'.
	 */
	private String getCadenaLiquida() {return cadenaLiquida;}

	@Override public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") Cadena cadena = (Cadena)o;
        return cadenaLiquida.equals(cadena.getCadenaLiquida());
	}

	/**
	 * Devuelve la dispersion del objeto {@link Cadena}.
	 * @return dispersion de la cadena interna 'liquida'.
	 */
	@Override public int hashCode() {return cadenaLiquida.hashCode();}
}