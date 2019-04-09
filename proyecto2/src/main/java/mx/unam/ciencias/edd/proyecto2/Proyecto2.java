package mx.unam.ciencias.edd.proyecto2;

import java.util.NoSuchElementException;

public class Proyecto2 {
	public static void main(String[] args) {

		try {
			ProcesadorEntrada entrada = new ProcesadorEntrada(args);
			EstructurasDeDatosSVG estructura = new EstructurasDeDatosSVG(entrada.entradaProcesada());
			estructura.imprimirEstructuraSVG();
		} catch (ExcepcionEntradaSobrecargada ees) {
			System.err.printf("%s\n%s\n",
				"Error: entrada sobrecargada.","Solo se puede leer de un archivo o de la entrada estandar.");
			System.exit(1);
		} catch (NoSuchElementException nsee) {
			System.err.printf("%s\n%s\n",
				"Error: archivo invalido.","El archivo no existe, no se puede acceder al archivo, o el archivo esta vacio.");
			System.exit(1);
		} catch (ExcepcionEstructuraInvalida eei) {
			System.err.printf("%s\n%s\n",
				"Error: estructura invalida.","La estructura no existe.");
			System.exit(1);
		} catch (ExcepcionFormatoEquivocado efe) {
			System.err.printf("%s\n%s\n",
				"Error: formato equivocado.","Solo se puede separar los elementos con un caracter no imprimible.");
			System.exit(1);
		} catch (NumberFormatException nfe) {
			System.err.printf("%s\n%s\n",
				"Error: elemento invalido.","Los elementos de la estructura solo pueden ser números enteros.");
			System.exit(1);
		} catch (ExcepcionVerticesImpares evi) {
			System.err.printf("%s\n%s\n",
				"Error: vertices impares.","Los elementos de la grafica solo pueden se pares.");
			System.exit(1);
		} catch (IllegalArgumentException iae) {
			System.err.printf("%s\n%s\n",
				"Error: elementos repetidos.","No se puede repetir elementos en una grafica.");
			System.exit(1);
		}
	}
}
