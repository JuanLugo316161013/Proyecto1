package mx.unam.ciencias.edd.proyecto3;

import java.util.NoSuchElementException;
import java.lang.SecurityException;
/**
 * Clase principal para el proyecto 3, contador de palabras.
 */
public class Proyecto3 {
	public static void main(String[] args) {
		try {
			ProcesadorEntrada entrada = new ProcesadorEntrada(args);
			GestorArchivos gestor = new GestorArchivos(entrada.archivosProcesados(),entrada.directorio());
			gestor.crearArchivosHtml();
		} catch (ExcepcionArchivoInvalido e) {
			System.err.printf("%s\n",
				"Error: archivo invalido");
			System.exit(1);
		} catch (ExcepcionDirectorioInvalido e) {
			System.err.printf("%s\n",
				"Error: directorio invalido");
			System.exit(1);
		} catch (NoSuchElementException e) {
			System.err.printf("%s\n",
				"Error: lectura/escritura archivos");
			System.exit(1);
		} catch (SecurityException e) {
			System.err.printf("%s\n",
				"Error: acceso denegado");
			System.exit(1);
		} 
	}
}