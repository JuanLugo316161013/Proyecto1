public class Palabra implements DatoGraficable<Palabra>{

	private String palabra;

	private int frecuencia;

	public Palabra(String palabra,int frecuencia) {
		this.palabra = palabra;
		this.frecuencia = frecuencia;
	}

	/** 
	 * Devuelve el elemento del dato en la grafica.
	 * @return elemento del deto en la grafica.
	 */
	public String get() {return palabra;}

	/**
	 * Devuelve la frecuencia del elemento.
	 * @return frecuencia del elemento.
	 */
	public int frecuencia() {return frecuencia;}
}