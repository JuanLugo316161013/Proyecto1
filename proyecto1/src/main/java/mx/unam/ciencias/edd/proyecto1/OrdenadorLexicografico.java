package mx.unam.ciencias.edd.proyecto1;

public class OrdenadorLexicografico implements Comparable<String>{	
	
	String cadena;

	public OrdenadorLexicografico() {}

	public OrdenadorLexicografico(T cadena) {
		this.cadena = cadena;
	}

	public boolean esVacia() {
		return cadena == null;
	}

	@Override public int compareTo(String cadena) {
		String cadena1 = this.cadena.trim(), cadena2 = cadena.trim();
		return cadena1.compareTo(cadena2);
	}

	public void setCadena(String cadena) {
		this.cadena = cadena;
	}
}