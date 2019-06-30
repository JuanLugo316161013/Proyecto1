package mx.unam.ciencias.edd.proyecto2;

import java.util.NoSuchElementException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.lang.NumberFormatException;
import java.lang.SecurityException;
import mx.unam.ciencias.edd.Lista;
/**
 * <p>Clase que determina de si hay que leer de la entrada estandar, un archivo o de los argumentos del programa.</p>
 * <p>La clase lee los elementos de una estructura de datos especificados en la entrada (entrada estandar, archivo, 
 * argumentos del programa) y el nombre de la estructura.</p>
 */
public class ProcesadorEntrada {

	/** Lector de archivos */
	private BufferedReader lector;

	/** Elementos de la estructura de datos */
	private Lista<Integer> elementos;

	/** Estructura de Datos */
	private String estructura; 

	/**
	 * Constructor que recibe los argumentos del programa.
	 * @param args argumentos del programa.
	 * @throws ExcepcionEntradaSobrecargada si se quiere leer de un archivo y la entrada estandar.
	 * @throws ExcepcionArchivoInvalido si el archivo no se puede leer.
	 */
	public ProcesadorEntrada(String[] args) {
		try  {
			if (args.length < 1) {
				lector = new BufferedReader(new InputStreamReader(System.in));
				procesaArchivo();
				return;
			} else if (args.length == 1) {
				File archivo = new File(args[0]);
				if (!VerificadorArchivo.verificaArchivo(archivo))
					throw new ExcepcionArchivoInvalido();
				lector = new BufferedReader(new FileReader(archivo));
				procesaArchivo();
				return;
			} else {
				procesaEntradaEstandar(args);
			}
		} catch (IOException ioe) {
			System.err.println("Error en la lectura de archivo.");
			System.exit(1);
		} catch (SecurityException se) {
			System.err.println("Error en la lectura de archivo.");
			System.exit(1);
		}
	}

	/**
	 * Procesa la entrada estandar.
	 * @param args los argumentos del metodo main.
	 * @throws NoSuchElementException si no se lee ninguna estructura.
	 */
	private void procesaEntradaEstandar(String[] args) {
		if (args[0].equals("#"))
			throw new NoSuchElementException();
		estructura = args[0];
		elementos = new Lista<Integer>();
		for (int i = 1; i < args.length; i++)
			elementos.agrega(Integer.valueOf(args[i]));
	}

	/**
	 * Procesa un archivo, con un LectorArchivo.
	 * @throws NoSuchElementException si el archivo no existe.
	 */
	private void procesaArchivo() {
		String linea, cadena;
		elementos = new Lista<Integer>();
		try {
			while ((linea = lector.readLine()) != null) {
				cadena = "";
				char[] caracteres = linea.toCharArray();
				for (int i = 0; i < caracteres.length; i++){
					if (caracteres[i] == '#')
						break;
					if (caracteres[i] < 33)	{
						if (cadena.isEmpty()) {
							continue;
						} else if (estructura == null) {
							estructura = cadena;
							cadena = "";
							continue;
						} else {
							elementos.agrega(Integer.valueOf(cadena));
							cadena = "";
							continue;
						}
					}
					cadena += String.valueOf(caracteres[i]);
					System.out.println(cadena);
					if (!cadena.isEmpty() && i == caracteres.length-1 && estructura == null)
						estructura = cadena;
					else if (!cadena.isEmpty() && i == caracteres.length-1)
						elementos.agrega(Integer.valueOf(cadena));
				}
			}
		} catch (IOException ioe) {
			System.err.println("Error en la lectura de archivo.");
			System.exit(1);
		} catch (NumberFormatException nfe) {
			System.err.println("Error elemento invalido.");
			System.exit(1);
		} 
	}

	/**
	 * Regresa el nombre de la estructura de datos.
	 * @return nombre de la estructura de datos.
	 */
	public String estructura() {return estructura;}

	/**
	 * Regresa una Lista de {@link Integer} que son los elementos de la estructura de datos.
	 * @return elementos de la estrucura de datos.
	 */
	public Lista<Integer> elementos() {return elementos;}
}
