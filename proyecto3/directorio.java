import java.io.*;

public class directorio {
	public static void main(String[] args) {
		try {
			File archivo = new File("../proyecto3/graficadora/archivo.html");
			archivo = new File(archivo.getCanonicalPath());
			System.out.println(archivo.getAbsolutePath());
		} catch (IOException ioe) {}
	}
}