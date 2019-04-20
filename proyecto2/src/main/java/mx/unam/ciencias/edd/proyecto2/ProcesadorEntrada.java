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
		if (args == null)
			throw new IllegalArgumentException();

		entrada = new Lista<String>();

		if (args.length < 1) {
			lector = new LectorArchivo(System.in);
			procesaArchivo();
			return;
		}

		lector = new LectorArchivo(System.in);

		if (args.length == 1 && !lector.estaListo()) {
			lector = new LectorArchivo(new File(args[0]));
			procesaArchivo();
			return;
		}

		lector = new LectorArchivo(System.in);
		if (lector.estaListo())
			throw new ExcepcionEntradaSobrecargada();


		procesaEntradaEstandar(args);
	}

	/**
	 * Procesa la entrada estandar.
	 * @param args los argumentos del metodo main.
	 */
	private void procesaEntradaEstandar(String[] args) {
		for (String cadena : args)
			if (cadena.equals("#"))
				break;
			else
				entrada.agrega(cadena);
	}

	/**
	 * Procesa un archivo, con un LectorArchivo.
	 * @throws NoSuchElementException si el archivo no existe.
	 */
	private void procesaArchivo() {
		String linea;

		while ((linea = lector.leer()) != null)
			for (String cadena : linea.trim().split("\\s"))
				if (cadena.contains("#"))
					break;
				else
					entrada.agrega(cadena);
	}

	/**
	 * Regresa una lista con la entrada procesada.
	 * @throws NoSuchElementException si la lista es vacía;
	 */
	public Lista<String> entradaProcesada() {
		if (entrada.esVacia())
			throw new NoSuchElementException();
		return entrada;
	}
}
