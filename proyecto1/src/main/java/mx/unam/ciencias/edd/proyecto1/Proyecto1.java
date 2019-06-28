package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.Cola;
import java.io.File;

/**
 * Clase principal del Ordenador Lexicografíco.
 */
public class Proyecto1 {
    
    /** 
     * Metodo principal del Ordenador Lexicografíco.
     * @param args banderas o lista de archivos.
     */
    public static void main(String[] args ) {
        long tiempoInicial = System.nanoTime();
        try {
            OrdenadorLexicografico ordenadorLexicografico = new OrdenadorLexicografico(args);
        } catch (ExcepcionBanderaRepetida ebr) {
            System.err.println("Error bandera repetida.");
            System.exit(1);
        } catch (ExcepcionArchivoInvalido eai) {
            System.err.println("Error archivo invalido.");
            System.exit(1);
        }
    }
}
