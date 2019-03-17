package mx.unam.ciencias.edd.proyecto1;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Pila;


public class OrdenadorLexicografico {
	
	private boolean reversa;

	private boolean salida;

	private ArbolRojinegro<Cadena> arbol;

	public OrdenadorLexicografico() {
		arbol = new ArbolRojinegro<Cadena>();
	}

	public OrdenadorLexicografico(String bandera) {
	}

	public void agrega(Cadena elemento) {
		arbol.agrega(elemento);
	}

	public void imprime() {
	}
}