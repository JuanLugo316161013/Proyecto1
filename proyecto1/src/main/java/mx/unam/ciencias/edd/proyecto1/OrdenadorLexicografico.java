package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.ArbolRojinegro;
import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.IOException;
import java.io.File;
import java.lang.SecurityException;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

/**
 * Clase que ordena lexicografícamente una lista de archivos. Basado en Sort de Unix
 */
public class OrdenadorLexicografico {
	
	/* Estructura que ordena la lista de archivos */
	private ArbolRojinegro<Cadena> ordenadorLexicografico;

	/* Argumentos del Programa */
	private Argumentos argumentos;

	/**
	 * Constructor que recibe los argumentos del metodo principal.
	 * @param args argumentos del metodo principal.
	 */
	public OrdenadorLexicografico(String[] args) {
		ordenadorLexicografico = new ArbolRojinegro<Cadena>();
		ProcesadorEntrada entrada = new ProcesadorEntrada(args);
		argumentos = entrada.argumentos();
		Cadena.COMPARADOR = argumentos.bandera_R();
		if (entrada.metodo()) 
			ordenaListaArchivos();
		else
			ordenaEntradaEstandar();
		if (argumentos.bandera_O())
			escribeOrdenLexicografico();
		else 
			imprimeOrdenLexicografico();
	}

	/**
	 * Lee y ordena lo recibido en la entrada estandar.
	 */ 
	private void ordenaEntradaEstandar() {
		LectorArchivo lector = new LectorArchivo(System.in);
 		Cadena renglon;
		while ((renglon = lector.leer()) != null)
			ordenadorLexicografico.agrega(renglon);
	}

	/**
	 * Lee y ordena una lista de archivos de los argumentos del metodo principal.
	 */
	private void ordenaListaArchivos() {
		LectorArchivo lector;
		Cadena renglon;
		for (File archivo : argumentos.archivos()) {
			lector = new LectorArchivo(archivo);
			while ((renglon = lector.leer()) != null)
				ordenadorLexicografico.agrega(renglon);
		}
	}

	/**
	 * Imprime en orden lexicografíco las lineas de texto recibidas.
	 */
	private void imprimeOrdenLexicografico() {
		ordenadorLexicografico.dfsInOrder(v -> System.out.println(v.get()));
	}

	/**
	 * Escribe en orden lexicografíco las lineas de texto recibidas, en el archivo
	 * especificada por la bandera '-o'.
	 */
	private void escribeOrdenLexicografico() {
		try {
			BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter
			          (new FileOutputStream(argumentos.archivo(),true),StandardCharsets.UTF_8));
			for (Cadena renglon : ordenadorLexicografico)
				escritor.write(renglon.toString());
		} catch (FileNotFoundException fnfe) {
			System.err.println("Error en la escritura de archivos");
			System.exit(1);
		} catch (SecurityException se) {
			System.err.println("Error en la escritura de archivos");
			System.exit(1);
		} catch(IOException ioe) {
			System.err.println("Error en la escritura de archivos");
			System.exit(1);
		}
	}
}