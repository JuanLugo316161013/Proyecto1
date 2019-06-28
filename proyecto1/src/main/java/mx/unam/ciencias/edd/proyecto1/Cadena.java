package mx.unam.ciencias.edd.proyecto1;

import java.text.Collator;
import java.text.Normalizer;
/**
 * Clase que contiene una una cadena de caracteres, y la misma cadena pero en su forma 
 * normalizada (sin acentos, y en minusculas).
 */
public class Cadena implements Comparable<Cadena> {

	/* Cadena original */
	private String cadena;

	/* Cadena normalizada. */
	private String cadenaNormalizada;

	/** 
	 * Especifica el modo de comparar los objetos {@link Cadena}
	 * si es <code>false</code> las cadenas se comparan de manera normal,
	 * si es <code>true</code> el valor de la cadena se multiplica por -1*/
	public static boolean COMPARADOR;


	/* Objeto que compara dos cadenas normalizadas */
	private static Collator comparador = Collator.getInstance();

	/**
	 * Constructor que recibe un String que será la cadena interna asociada.
	 * @param cadena String que será la cadena asociada a Cadena.
	 */
	public Cadena(String cadena) {
		this.cadena = cadena;
		cadenaNormalizada = Normalizer.normalize(cadena,Normalizer.Form.NFKD);
		cadenaNormalizada = cadenaNormalizada.replaceAll("[^a-z,A-Z,0-9,\\p{Z}]", "");
	}

	/**
	 * Compara dos objetos Cadena, compara sus cadenas normalizadas.
	 * @param cadena cadena a comparar.
	 * @return un número mayor a cero si la cadena a comparar es mayor a la cadena normalizada del objeto Cadena
	 *         un número menor a cero si es menor la cadena a comparar, o cero si son iguales.
	 */
	@Override public int compareTo(Cadena cadena) {
		comparador.setStrength(Collator.PRIMARY);
		int valor = comparador.compare(cadenaNormalizada,cadena.getCadena());
		return COMPARADOR ? valor * -1 : valor;
	}

	/**
	 * Regresa la cadena de caracteres normalizada.
	 * @return cadena normalizada.
	 */
	private String getCadena() {return cadenaNormalizada;}

	@Override public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") Cadena cadena = (Cadena)o;
        return cadenaNormalizada.equals(cadena.getCadena());
	}

 	/**
 	 * Regresa el {@link String} pasado en el constructor.
 	 * @return cadena normalizada.
 	 */
	@Override public String toString() {return cadena;}
}