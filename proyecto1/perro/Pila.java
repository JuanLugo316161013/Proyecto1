package mx.unam.ciencias.edd;

/**
 * Clase para pilas genéricas.
 */
public class Pila<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la pila.
     * @return una representación en cadena de la pila.
     */
    @Override public String toString() {
        // Aquí va su código.
        if (esVacia())
            return "";
        Nodo nodoAuxiliar = cabeza;
        String pila = "";
        while (nodoAuxiliar != null){
            pila += nodoAuxiliar.elemento + "\n";
            nodoAuxiliar = nodoAuxiliar.siguiente;
        }
        return pila;
    }

    /**
     * Agrega un elemento al tope de la pila.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
        // Aquí va su código.
        if (elemento == null)
            throw new IllegalArgumentException("Elemento Nulo");
        if (esVacia()) {
            cabeza = new Nodo(elemento);
            rabo = cabeza;
            return;
        }
        Nodo nodoAuxiliar = new Nodo(elemento);
        nodoAuxiliar .siguiente = cabeza;
        cabeza = nodoAuxiliar;
    }
}
