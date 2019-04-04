import java.io.*;
import java.nio.charset.StandardCharsets;

public class pruebaSplit{

    public static void main(String[] args) {

    	try {
	        BufferedReader bf = new BufferedReader(new InputStreamReader
				        (new FileInputStream("prueba.txt"), StandardCharsets.UTF_8));
	        StreamTokenizer st = new StreamTokenizer(bf);
	        st.commentChar('#');
	        //st.parseNumbers();
	        //st.pushBack();
	        int entero;
	        while ((entero = st.nextToken()) != StreamTokenizer.TT_EOF)
	        	System.out.println(entero +" "+ st.toString());
	    }
		catch (FileNotFoundException fnfe) {} 
		catch (SecurityException se) {}
		catch (IOException ioe) {}
    }
}