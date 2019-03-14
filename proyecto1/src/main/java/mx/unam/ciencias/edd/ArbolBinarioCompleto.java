package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Inicializa al iterador. */
        public Iterador() {
            // Aquí va su código.
            cola = new Cola<Vertice>();
            if (raiz != null)
                cola.mete(raiz);
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return !cola.esVacia();
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override public T next() {
            // Aquí va su código.
            if (cola.esVacia())
                throw new java.util.NoSuchElementException();

            Vertice vertice = cola.mira();

            if (vertice.hayIzquierdo())
                cola.mete(vertice.izquierdo);

            if (vertice.hayDerecho())
                cola.mete(vertice.derecho);
            
            vertice = cola.saca();
            return vertice.get();
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() { super(); }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if (elemento == null)
            throw new IllegalArgumentException();

        if (esVacia()) {
            raiz = nuevoVertice(elemento);
            elementos++;
            return;
        }

        Vertice vertice = raiz;
        Vertice nuevoVertice = nuevoVertice(elemento);
        int altura = altura();
        double x = (elementos - Math.pow(2,altura) + 1) % Math.pow(2,altura);  
        
        while (altura > 0) {
            if (x < Math.pow(2,altura - 1)) {
                if (vertice.hayIzquierdo())
                    vertice = vertice.izquierdo;
            } else {
                if (vertice.hayDerecho())
                    vertice = vertice.derecho;
                x -= Math.pow(2,altura - 1);
            }
            altura--;
        }

        nuevoVertice.padre = vertice;
        if (!vertice.hayIzquierdo())  
            vertice.izquierdo = nuevoVertice;
        else
            vertice.derecho = nuevoVertice;
        elementos++;
    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        if (elemento == null || raiz == null)
            return;

        if (raiz.elemento.equals(elemento)) {
            if (!raiz.hayIzquierdo() && !raiz.hayDerecho()){
                limpia();
            }
        }

        if (busca(elemento) == null)
            return;

        Vertice vertice = vertice(busca(elemento));
        Vertice ultimoVertice = daUltimoVertice();
        Vertice padreDeUltimoVertice = ultimoVertice.padre;

        elementos--;

        intercambia(vertice,ultimoVertice);

        if (padreDeUltimoVertice.izquierdo == ultimoVertice)
            padreDeUltimoVertice.izquierdo = null;
        else
            padreDeUltimoVertice.derecho = null;
    }

    private Vertice daUltimoVertice() {
        Cola<Vertice> cola = new Cola<Vertice>();
        Vertice vertice = null;
        cola.mete(raiz);

        while (!cola.esVacia()) {
            vertice = cola.saca();
            if (vertice.hayIzquierdo())
                cola.mete(vertice.izquierdo);
            if (vertice.hayDerecho())
                cola.mete(vertice.derecho);
        }
        return vertice;
    }

    private void intercambia(Vertice a,Vertice b) {
        T elemento = a.elemento;
        a.elemento = b.elemento;
        b.elemento = elemento;
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
    @Override public int altura() {
        // Aquí va su código.
        if (elementos == 0)
            return -1;
        return (int)(Math.floor(Math.log(elementos) / Math.log(2)));
    }

    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en
     * cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        Cola<Vertice> cola = new Cola<Vertice>();
        Vertice vertice = null;
        if (!esVacia())
            cola.mete(raiz);

        while (!cola.esVacia()) {
            vertice = cola.saca();
            accion.actua(vertice);
            if (vertice.hayIzquierdo())
                cola.mete(vertice.izquierdo);
            if (vertice.hayDerecho())
                cola.mete(vertice.derecho);
        }
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
