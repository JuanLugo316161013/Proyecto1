package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.Cola;
import java.io.File;

/**
 * Clase que recibe archivos de texto y los imprime en el orden
 * que lo haría Sort de Unix.
 */
public class Proyecto1 {

    public static void main(String[] args ) {
    	/* Declaracion de variables */
    	EscritorFlujoCaracteres escritor;
    	OrdenadorLexicografico ordenador;
    	LectorFlujoCaracteres lector;
    	Cola<File> colaArchivos;
    	String[] orden;
    	File archivo;

    	/* Leer desde System.in */ 
    	lector = new LectorFlujoCaracteres(System.in);
    	ordenador = new OrdenadorLexicografico();
    	String linea;

    	while ((linea = lector.leer()) != null)
    		ordenador.agrega(linea); // Lee y ordena por renglones

    	lector.cerrar(); // Cierra el flujo de entrada

    	/* Leer archivos dede la entrada estandar */
    	colaArchivos = new Cola<File>();
    	for (int i = 0; i < args.length ; i++) {

    		/* Verifica si es la bandera -r */
    		if (args[i].equals("-r")) {
    			ordenador.bandera(args[i]);
    			continue;
    		}

    		/* Verifica que es la bandera -o */
    		if (args[i].equals("-o")) {
    			if (i+1 >= args.length) {
    				System.err.printf("La opción -o requiere un argumento\n");
    				System.exit(1);
    			}
    			archivo = new File(args[i+1]);
    			VerificadorArchivos.verificaArchivo_Escribir(archivo);
    			ordenador.bandera(args[i]);
    			colaArchivos.mete(archivo);
    			i++;
    			continue;
    		}

    		/* Verifica que sea un archivo */
    		archivo = new File(args[i]);
    		VerificadorArchivos.verificaArchivo_Leer(archivo);

    		/* Leer el archivo */
    		lector = new LectorFlujoCaracteres(archivo);
    		while ((linea = lector.leer()) != null)
    			ordenador.agrega(linea);

    		lector.cerrar();
    	}

    	/* Escribe en los archivos dador por -o */
    	if (ordenador.bandera_O()) {
    		while (!colaArchivos.esVacia()) {
    			orden = ordenador.salida();
    			escritor = new EscritorFlujoCaracteres(colaArchivos.saca());

    			for (int j = 0; j < orden.length; j++)
    				escritor.escribe(orden[j]);

    			escritor.cerrar();
 			}
 			System.exit(0);
    	}

    	/* Imprime las cadenas ordenadas */
    	System.out.printf(ordenador.toString());
    }
}
