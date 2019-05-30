import java.io.*;

public class directorio {
	public static void main(String[] args) {
		try {
			File archivo = new File("../proyecto3/graficadora/archivo.html");
			System.out.println(archivo.getCanonicalPath());
		} catch (IOException ioe) {}
	}
}