import java.io.*;

public class directorio {
	public static void main(String[] args) {
		try {
			File directory = new File("../proyecto3/perro/gato/raton");	
			directory = new File(directory.getCanonicalPath());
			if (directory.mkdirs())
				System.out.println("Yes ;^)");
			File archivo = new File("perro/archivo.txt");
			archivo.createNewFile();
		} catch (IOException ioe) {}
	}
}