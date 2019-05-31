package mx.unam.ciencias.edd.proyecto3;

public class Proyecto3 {
	public static void main(String[] args) {
		ProcesadorEntrada entrada = new ProcesadorEntrada(args);
		for (Archivo archivo : entrada.archivosProcesados())
			archivo.creaHtml();
	}
}