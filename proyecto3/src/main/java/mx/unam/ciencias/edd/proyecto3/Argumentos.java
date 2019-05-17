package mx.unam.ciencias.edd.proyecto3;

import java.io.File;
import mx.unam.ciencias.edd.Lista;
/**
 * Clase que procesa los argumento de la entrada estandar;
 */
public class Argumentos {

	/* Lista de archivo de texto. */
	private Lista<File> archivosTexto;

	/* Directorio donde estarán los archivos html correspondinetes a los archivos de texto.*/
	private File directorio;

	/**
	 * Contructor que recibe los argumentos de la entrada estandar.
	 * @param args argumentos de la entrada estandar.
	 */
	public Argumentos(String[] args) {
		int bandera_O = 0;

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-o")) {
				bandera_O++;
				if (bandera_O == 1)
					continue;
				throw new ExcepcionBanderaRepetida();
			}

			if (bandera_O == 1) {
				directorio = new File(args[i]);
				if (!VerificadorArchivo.verificaDirectorio(directorio))
					throw new ExcepcionDirectorioInvalido();
				continue;
			}

			File archivo = new File(args[i]);
			if (!VerificadorArchivo.verificaArchivo(archivo))
				throw new ExcepcionArchivoInvalido();
			archivosTexto.agrega(archivo);
		}

		// Si no se especifico 
		if (bandera_O == 0) {
			directorio = new File(System.getProperty("user.dir"));
			if (!VerificadorArchivo.verificaDirectorio(directorio))
					throw new ExcepcionDirectorioInvalido();
		}
	}

	/**
	 * Devuelve la lista de archivos que se paso en los argumentos de la
	 * entrada estandar.
	 * @return lista de archivos.
	 */
	public Lista<File> archivos() {return archivosTexto;}

	/**
	 * Devuelve el directorio en donde se crearan todos los archivos html y svg.
	 * @return directorio donde se crearan los archivos html y svg.
	 */
	public File directorio() {return directorio;}
}