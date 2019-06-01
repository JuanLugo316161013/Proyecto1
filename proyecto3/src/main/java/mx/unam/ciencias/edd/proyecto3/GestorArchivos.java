package mx.unam.ciencias.edd.proyecto3;

import java.io.File;
import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Conjunto;
import mx.unam.ciencias.edd.Lista;
import java.util.NoSuchElementException;
/**
 * Clase que recibe una Lista de archivos, y 
 */
public class GestorArchivos {
	/** grafica del gestor de archivos */
	private Grafica<Integer> grafica;

	/* Cojunto de archivos */
	private Lista<Archivo> archivos;

	/* Directorio donde se crearan los archivos html*/
	private File directorio;

	/* Archivo index con los demás archivos */
	private File index;

	/* Aspectos graficos y esteticos de los archivos html*/
	private File css, graficaVH;
	
	/**
	 * Constructor que recibe una Lista de archivos
	 * @param archivos lista de archivos.
	 */
	public GestorArchivos(Lista<Archivo> archivos,File directorio) {
		this.directorio = directorio;
		this.archivos = archivos;
		grafica = new Grafica<Integer>();
		css = new File(String.format("%s/archivo.css",directorio.getAbsolutePath()));
		graficaVH = new File(String.format("%s/graficaVH.svg",directorio.getAbsolutePath()));
		index = new File(String.format("%s/index.html", directorio.getAbsolutePath()));
		for (Archivo archivo : archivos) 
			grafica.agrega(archivo.numeroArchivo());

		for (Archivo archivo: archivos) {
			for (Cadena cadena : archivo) { 
				if (cadena.getCadena().length() >= 7) {
					for (Archivo a : archivos) {
						if (a.contiene(cadena)) {
							try {
								grafica.conecta(a.numeroArchivo(),archivo.numeroArchivo());
							} 
							catch (IllegalArgumentException iae) {}
							catch (NoSuchElementException nee) {}
						}
					}
				}
			}
		}
	}

	/**
	 * Crea los archivos html que estaran ligados al archivo index.html
	 */
	public void crearArchivosHtml() {
		for (Archivo archivo : archivos)
			archivo.creaHtml();
		if (!VerificadorArchivo.verificaNuevoArchivo(index))
			throw new ExcepcionDirectorioInvalido();
		creaGraficas();
		EscritorArchivo escritor = new EscritorArchivo(index);
		escritor.escribe("<!DOCTYPE HTML>\n");
		escritor.escribe("<html>\n");
		escritor.escribe("<head>\n");
		escritor.escribe("<meta charset='UTF-8'>\n");
		escritor.escribe(String.format("<title>%s</title>\n","Index"));
		escritor.escribe(String.format("<link rel='stylesheet' href='%s'>",css.getAbsolutePath()));
		//escritor.escribe("<STYLE> {text-decoration: none;} </STYLE>");
		escritor.escribe("</head>\n");
		escritor.escribe("<body>\n");
		escritor.escribe("<header>\n");
		escritor.escribe(String.format("<h1>%s</h1>\n","Index"));
		escritor.escribe("</header>\n");
		escritor.escribe("<h2>Archivos.</h2>\n");
		escritor.escribe("<ol>\n");
		for (Archivo archivo: archivos) 
			escritor.escribe(String.format("<li><a href='%s' style='color : #000000;'>%s : %d</a></li>\n",archivo.nombreCompleto(),archivo.nombre(),archivo.palabras()));
		escritor.escribe("</ol>\n");
		escritor.escribe("<figure>\n");
		escritor.escribe(String.format("<img src = '%s' alt='Dos archivos estan conectados si y solo si comparten una palabra de 7 caracteres'\ntitle= 'Grafica: Dos archivos estan conectados si y solo si comparten una palabra de 7 caracteres'>\n",
			graficaVH.getAbsolutePath()));
		escritor.escribe("<figcaption>Grafica: Dos archivos estan conectados si y solo si comparten una palabra de 7 caracteres</figcaption>\n");
		escritor.escribe("</body>\n");
		escritor.escribe("</html>");
		escritor.cerrar();
	}

	private void creaGraficas() {
		if (!VerificadorArchivo.verificaNuevoArchivo(graficaVH))
			throw new ExcepcionDirectorioInvalido();
		EscritorArchivo escritor = new EscritorArchivo(graficaVH);
		GeneradorGraficaSVG<Integer> generador = new GeneradorGraficaSVG<Integer>(grafica);
		for (String codigo : generador.codigoSVG())
			escritor.escribe(codigo);
		escritor.cerrar();
		escritor = new EscritorArchivo(css);
		escritor.escribe("header\n{\ntext-align: center;\nfont: 250% sans-serif;\n}\n");
		escritor.escribe("h2\n{\ntext-align: center;\nfont: 170% sans-serif;\n}\n");
		escritor.escribe("ul\n{\ntext-align: left;\nfont: 100% sans-serif;\ncolumn-count: 6;\ncolumn-gap: 100px\n}\n");
		escritor.escribe("ol\n{\ntext-align: left;\nfont: 100% sans-serif;\ncolumn-count: 6;\ncolumn-gap: 100px\n}\n");
		escritor.escribe("figure\n{\ntext-align: left;\n}\n");
		escritor.escribe("figcaption\n{\ntext-align: center;\nfont: 75% sans-serif;\n}\n");
		escritor.escribe("#barras\n{\nwidth: 1200px;\n}\n");
		escritor.cerrar();
	}
}