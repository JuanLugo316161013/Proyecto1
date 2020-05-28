package mx.unam.ciencias.edd.proyecto2;

import java.util.NoSuchElementException;
import java.io.File;
import mx.unam.ciencias.edd.Lista;
/**
 * Procesa la entrada estandar de un programa ejecutable, si son archivos, los lee.
 */
public class ProcesadorEntrada {

	/** Lector de archivos */
	private LectorArchivo lector;

	/** Entrada procesada */
	private Lista<String> entrada;

	/**
	 * Constructor vacío.
	 */
	private ProcesadorEntrada() {}

	/**
	 * Constructor que recibe los argumentos del metodo main
	 * @param args argumentos del metodo main.
	 * @throws IllegalArgumentException si el args es null.
	 * @throws ExcepcionEntradaSobrecargada si se quiere leer de un archivo y la entrada estandar.
	 * @throws NoSuchElementException el archivo no exite.
	 */
	public ProcesadorEntrada(String[] args) {
		entrada = new Lista<String>();

		if (args.length < 1) {
			lector = new LectorArchivo(System.in);
			procesaArchivo();
			lector.cerrar();
			return;
		}

		lector = new LectorArchivo(System.in);

		if (lector.estaListo())
			throw new ExcepcionEntradaSobrecargada();

		if (args.length == 1) {
			lector = new LectorArchivo(new File(args[0]));
			procesaArchivo();
			lector.cerrar();
			return;
		}

		lector.cerrar();
		procesaEntradaEstandar(args);
	}

	/**
	 * Procesa la entrada estandar.
	 * @param args los argumentos del metodo main.
	 */
	private void procesaEntradaEstandar(String[] args) {
		for (String cadena : args) {
			if (cadena.contains("#")) {
				String[] s = cadena.split("#");
					entrada.agrega(s[0]);
					break;
			} else {
				entrada.agrega(cadena);
			}
		}
	}

	/**
	 * Procesa un archivo, con un LectorArchivo.
	 * @throws NoSuchElementException si el archivo no existe.
	 */
	private void procesaArchivo() {
		String linea;

		while ((linea = lector.leer()) != null) {
			if (linea.trim().isEmpty())
				continue;
			for (String cadena : linea.trim().split("\\s")) {
				if (cadena.contains("#")) {
					String[] s = cadena.split("#");
					entrada.agrega(s[0]);
					break;
				} else {
					entrada.agrega(cadena);
				}
			}
		}
	}

	/**
	 * Regresa una lista con la entrada procesada.
	 * @return lista de elementos en la entrada.
	 * @throws NoSuchElementException si la lista es vacía;
	 */
	public Lista<String> entradaProcesada() {
		if (entrada.esVacia())
			throw new NoSuchElementException();
		return entrada;
	}
}
