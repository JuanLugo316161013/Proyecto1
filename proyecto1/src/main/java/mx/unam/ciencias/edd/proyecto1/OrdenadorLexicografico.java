package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.ArbolRojinegro;
import java.util.NoSuchElementException;
import mx.unam.ciencias.edd.Pila;

public class OrdenadorLexicografico {
	
	private boolean reversa;

	private boolean salida;

	private ArbolRojinegro<Cadena> arbol;

	private Pila<Cadena> pila;

	public OrdenadorLexicografico() {
		arbol = new ArbolRojinegro<Cadena>();
	}

	public void agrega(Cadena elemento) {
		if (elemento == null)
			throw new IllegalArgumentException();

		arbol.agrega(elemento);
	}

	public void bandera(String bandera) {
		if (bandera == null)
			return;

		if (bandera.equals("-r"))
			reversa = true;

		if (bandera.equals("-o"))
			salida = true;
	}

	@Override public String toString() {
		if (arbol.esVacia())
			return "";

		pila = new Pila<Cadena>();
		String cadena = "";
		for (Cadena vertice : arbol) {
			cadena += vertice.getCadena() + "\n";
			pila.mete(vertice);
		}

		if (reversa) {
			cadena = "";
			while (!pila.esVacia())
				cadena += pila.saca().getCadena() + "\n";
		}

		return cadena;
	}

	public String[] salida() {
		if (arbol.esVacia())
			throw new NoSuchElementException();

		String[] orden = new String[arbol.getElementos()];
		int i = 0;

		for (Cadena vertice : arbol) {
			orden[i] = vertice.getCadena();
			i++;
		}

		return orden;
	}

	public static void main(String[] args) {
		OrdenadorLexicografico o = new OrdenadorLexicografico();

		String[] cadena = new String[29];
		cadena[0] = "Hombres necios que acusáis";
		cadena[1] = "    a la mujer sin razón,";
		cadena[2] = "sin ver que sois la ocasión";
		cadena[3] = "    de lo mismo que culpáis.";
		cadena[4] = "\n";
		cadena[5] = "Si con ansia sin igual";
		cadena[6] = "    solicitáis su desdén,";
		cadena[7] = "¿por qué queréis que obren bien";
		cadena[8] = "    si las incitáis al mal?";
		cadena[9] = "\n";
		cadena[10] = "Combatís su resistencia";
		cadena[11] = "    y luego con gravedad";
		cadena[12] = "decís que fue liviandad";
		cadena[13] = "    lo que hizo la diligencia.";
		cadena[14] = "\n";
		cadena[15] = "Parecer quiere el denuedo";
		cadena[16] = "    de vuestro parecer loco";
		cadena[17] = "al niño que pone el coco";
		cadena[18] = "    y luego le tiene miedo.";
		cadena[19] = "\n";
		cadena[20] = "Queréis con presunción necia";
		cadena[21] = "    hallar a la que buscáis,";
		cadena[22] = "para pretendida, Tais,";
		cadena[23] = "    y en la posesión, Lucrecia.";
		cadena[24] = "\n";
		cadena[25] = "¿Qué humor puede ser más raro";
		cadena[26] = "    que el que, falto de consejo,";
		cadena[27] = "él mismo empaña el espejo";
		cadena[28] = "    y siente que no esté claro?";

		Cadena c;
		for (int i = 0; i < cadena.length; i++) {
			c = new Cadena(cadena[i]);
			o.agrega(c);
		}	

		System.out.println(o.toString());

	}
}