package mx.unam.ciencias.edd.proyecto1;

import java.io.File;
import mx.unam.ciencias.edd.Lista;
/**
 * Clase que procesa los argumento de la entrada estandar;
 */
public class Argumentos {
	/* Lista de archivos*/
	private Lista<File> archivos;

	/* Archivo donde se escribira la salida del programa.*/
	private File archivo;

	/** Si se ha pasado la bandera -o */
	private boolean bandera_O;	

	/** Si se ha paso la bandera -r */
	private boolean bandera_R;

	/**
	 * Contructor que recibe los argumentos de la entrada estandar.
	 * @param args argumentos de la entrada estandar.
	 */
	public Argumentos(String[] args) {
		archivos = new Lista<File>();
		int bandera_O = 0;
		int bandera_R = 0;

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-r")) {
				this.bandera_R = true;
				bandera_R++;
				if (bandera_R == 1)
					continue;
				throw new ExcepcionBanderaRepetida();
			}
 
			if (args[i].equals("-o")) {
				this.bandera_O = true;
				bandera_O++;
				if (bandera_O == 1)
					continue;
				throw new ExcepcionBanderaRepetida();
			}

			if (bandera_O == 1) {
				this.archivo = new File(args[i]);
				if (!VerificadorArchivo.verificaDirectorio(archivo))
					throw new ExcepcionArchivoInvalido();
				continue;
			}

			File archivo = new File(args[i]);
			if (!VerificadorArchivo.verificaArchivo(archivo))
				throw new ExcepcionArchivoInvalido();
			archivos.agrega(archivo);
		}

		if (bandera_O == 1 && this.archivo == null) {
			System.err.println("Error la bandera -o requiere de un argumento");
			System.exit(1);
		}

	}

	/**
	 * Devuelve la lista de archivos que se paso en los argumentos de la
	 * entrada estandar.
	 * @return lista de archivos en la entrada en los argumentos del programa.
	 */
	public Lista<File> archivos() {return archivos.esVacia() ? null : archivos;}

	/**
	 * Devuelve el archivo donde se escribira la salida del programa.
	 * @return el archivo donde se escribira la salida del programa, <code>null</code>
	 *         si no se ha pasado la bandera -o en los argumentos del programa.
	 */
	public File archivo() {return archivo;}

	/**
	 * Nos dice si el usuario a pasando la bandera -o en los argumentos del programa.
	 * @return <code>true</code> si se ha pasado la bandera -o en los argumentos del programa
	 *         <code>false</code> en otro caso.
	 */
	public boolean bandera_O () {return bandera_O;}

		/**
	 * Nos dice si el usuario a pasando la bandera -r en los argumentos del programa.
	 * @return <code>true</code> si se ha pasado la bandera -r en los argumentos del programa
	 *         <code>false</code> en otro caso.
	 */
	public boolean bandera_R () {return bandera_R;}
}