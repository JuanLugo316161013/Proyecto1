 package mx.unam.ciencias.edd.proyecto1;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileReader;
import java.io.File;

public class LectorFlujoCaracteres {

	private BufferedReader lector;

	private LectorFlujoCaracteres () {}

	public LectorFlujoCaracteres(File archivo) {
		try {
			lector = new BufferedReader(new FileReader(archivo));
		} catch (FileNotFoundException fnfe) {
			System.err.println("Error en el flujo de datos");
		}
	}

	public LectorFlujoCaracteres(InputStream entrada) {
		lector = new BufferedReader(new InputStreamReader(entrada));
	} 

	public String leer() {
		try {
			return lector.readLine();
		} 
		catch (IOException ioe) {
			System.err.println("Error en el flujo de datos");
			return null;
		}
	}

	public void cerrar() {
		try {lector.close();}
		catch (IOException ioe) {System.err.println("Error en el flujo de datos");}
	}
}