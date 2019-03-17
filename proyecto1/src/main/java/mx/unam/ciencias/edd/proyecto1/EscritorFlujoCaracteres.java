package mx.unam.ciencias.edd.proyecto1;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.IOException;
import java.io.FileWriter;
import java.io.File;

public class EscritorFlujoCaracteres {

	BufferedWriter escritor;

	private EscritorFlujoCaracteres() {}

	public EscritorFlujoCaracteres(File archivo){
		try {
			escritor = new BufferedWriter(new FileWriter(archivo));
		} catch (IOException ioe) {
			System.err.println("Error en el flujo de datos");
		}
	}

	public EscritorFlujoCaracteres(OutputStream salida) {
		escritor = new BufferedWriter(new OutputStreamWriter(salida));
	} 

	public void escribe(String cadena) {
		try {
			escritor.write(cadena);
			escritor.newLine();
		} catch(IOException ioe) {
			System.err.println("Error enel fluyo de datos");
		}
	}

	public void cerrar() {
		try {
			escritor.close();
		} catch (IOException ioe) {
			System.err.println("Error en el flujo de datos");
		}
	}
}
