/**
 * Clase que grafica una Lista de datos.
 */
public class GraficadoraSVG{

	/**
	 * Constructor vacío.
	 */
	private GraficadoraSVG() {}

	/**
	 * Devuleve una grafica de pastel en codigo svg de los datos.
	 * @param datos datos de la grafica.
	 * @param valorTotal valor total de lo datos.
	 * @return grafica de pastel en codigo svg.
	 */
	public static <T extends DatoGraficable<T>> String graficaPastel(Lista<T> datos, int valorTotal) {
		String grafica = "<svg width='500' height='500' xmlns='http://www.w3.org/2000/svg'>\n";
		grafica += "\t<circle cx='240' cy='240' r='200' fill='red'/>\n";
		int radio = 200;
		double angulo = 0;
		int valorDatos = 0;
		double xi, yi, xf, yf;
		for (T elemento : datos) {
			xi = 240 + radio*Math.cos(Math.toRadians(angulo));
			yi = 240 + radio*Math.sin(Math.toRadians(angulo));
			angulo += 360/((double)valorTotal/(double)elemento.frecuencia());
			xf = 240 + radio*Math.cos(Math.toRadians(angulo));
			yf = 240 + radio*Math.sin(Math.toRadians(angulo));
			grafica += String.format("\t<path d='M 240 240 L %.4f %.4f A 200 200 0 0 1 %.4f %.4f z' fill='black' stroke-width='2' stroke='white'/>\n",
			xi, yi, xf, yf);
			valorDatos += elemento.frecuencia();
		}
		grafica += "\t<circle cx='240' cy='240' r='115' fill='white'/>\n";
		return grafica += "</svg>";
	}

	/**
	 * Devuleve una grafica de barras en codigo svg de los datos.
	 * @param datos datos de la grafica.
	 * @param valorTotal valor total de lo datos.
	 * @return grafica de pastel en codigo svg.
	 */
	public static <T extends DatoGraficable<T>> String graficaBarras(Lista<T> datos, int valorTotal) {
		return null;
	}

	public static void main(String[] args) {
		Lista<Palabra> palabras = new Lista<Palabra>();
		palabras.agrega(new Palabra("perro",6));
		palabras.agrega(new Palabra("perro",6));
		palabras.agrega(new Palabra("perro",6));
		palabras.agrega(new Palabra("perro",6));
		palabras.agrega(new Palabra("perro",6));
		palabras.agrega(new Palabra("perro",6));
		palabras.agrega(new Palabra("perro",6));
		palabras.agrega(new Palabra("perro",6));
		palabras.agrega(new Palabra("perro",6));
		palabras.agrega(new Palabra("perro",6));
		palabras.agrega(new Palabra("perro",6));
		palabras.agrega(new Palabra("perro",6));
		palabras.agrega(new Palabra("perro",6));
		palabras.agrega(new Palabra("perro",6));
		System.out.println(graficaPastel(palabras,100));
	}
}