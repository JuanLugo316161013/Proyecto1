package mx.unam.ciencias.edd;

import java.util.NoSuchElementException;

/**
 * <p>Clase abstracta para árboles binarios genéricos.</p>
 *
 * <p>La clase proporciona las operaciones básicas para árboles binarios, pero
 * deja la implementación de varias en manos de las subclases concretas.</p>
 */
public abstract class ArbolBinario<T> implements Coleccion<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class Vertice implements VerticeArbolBinario<T> {

        /** El elemento del vértice. */
        public T elemento;
        /** El padre del vértice. */
        public Vertice padre;
        /** El izquierdo del vértice. */
        public Vertice izquierdo;
        /** El derecho del vértice. */
        public Vertice derecho;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public Vertice(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;
        }

        /**
         * Nos dice si el vértice tiene un padre.
         * @return <tt>true</tt> si el vértice tiene padre,
         *         <tt>false</tt> en otro caso.
         */
        @Override public boolean hayPadre() {
            // Aquí va su código.
            return padre != null;
        }

        /**
         * Nos dice si el vértice tiene un izquierdo.
         * @return <tt>true</tt> si el vértice tiene izquierdo,
         *         <tt>false</tt> en otro caso.
         */
        @Override public boolean hayIzquierdo() {
            // Aquí va su código.
            return izquierdo != null;
        }

        /**
         * Nos dice si el vértice tiene un derecho.
         * @return <tt>true</tt> si el vértice tiene derecho,
         *         <tt>false</tt> en otro caso.
         */
        @Override public boolean hayDerecho() {
            // Aquí va su código.
            return derecho != null;
        }

        /**
         * Regresa el padre del vértice.
         * @return el padre del vértice.
         * @throws NoSuchElementException si el vértice no tiene padre.
         */
        @Override public VerticeArbolBinario<T> padre() {
            // Aquí va su código.
            if (padre == null)
                throw new NoSuchElementException();
            return padre;
        }

        /**
         * Regresa el izquierdo del vértice.
         * @return el izquierdo del vértice.
         * @throws NoSuchElementException si el vértice no tiene izquierdo.
         */
        @Override public VerticeArbolBinario<T> izquierdo() {
            // Aquí va su código.
            if (izquierdo == null)
                throw new NoSuchElementException();
            return izquierdo;
        }

        /**
         * Regresa el derecho del vértice.
         * @return el derecho del vértice.
         * @throws NoSuchElementException si el vértice no tiene derecho.
         */
        @Override public VerticeArbolBinario<T> derecho() {
            // Aquí va su código.
            if (derecho == null)
                throw new NoSuchElementException();
            return derecho;
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            // Aquí va su código.
            return altura(this);
        }

        private int altura(Vertice vertice) {
            if (vertice == null)
                return -1;

            int alturaIzquierda = altura(vertice.izquierdo);
            int alturaDerecha = altura(vertice.derecho);

            if (alturaIzquierda > alturaDerecha)
                return alturaIzquierda + 1;

            return alturaDerecha + 1;
        }

        /**
         * Regresa la profundidad del vértice.
         * @return la profundidad del vértice.
         */
        @Override public int profundidad() {
            // Aquí va su código.
            return profundidad(this,0);
        }

        /**
         * Calcula la profundidad de un vertice recursivamente
         * @param vertice vertice al que se quiere calcular la profundidad
         * @param contador contador que llevara la profundidad de los vertices
         * @return profundidad del vertice.
         */
        private int profundidad(Vertice vertice, int contador) {
            if (vertice == raiz)
                return contador;

            return profundidad(vertice.padre, contador + 1);
        }

        /**
         * Regresa el elemento al que apunta el vértice.
         * @return el elemento al que apunta el vértice.
         */
        @Override public T get() {
            // Aquí va su código.
            if (elemento == null)
                return null;
            return elemento;
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>. Las clases que extiendan {@link Vertice} deben
         * sobrecargar el método {@link Vertice#equals}.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link Vertice}, su elemento es igual al elemento de éste
         *         vértice, y los descendientes de ambos son recursivamente
         *         iguales; <code>false</code> en otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") Vertice vertice = (Vertice)objeto;
            // Aquí va su código.
            return equals(this,vertice);
        }

        /**
         * Compara dos vertices regresa verdadero si son iguales
         * y falso si no lo son
         * @param a vertice a comparar
         * @param b vertice a comparar
         * @return verdadero si son iguales, falso si no lo son
         */
        private boolean equals(Vertice a, Vertice b) {
            if (a == null && b == null)
                return true;

            if (a != null && b != null) {
                if (!a.elemento.equals(b.elemento))
                    return false;

                return equals(a.izquierdo,b.izquierdo) && equals(a.derecho,b.derecho);
            }
            return false;
        }

        /**
         * Regresa una representación en cadena del vértice.
         * @return una representación en cadena del vértice.
         */
        public String toString() {
            // Aquí va su código.
            return elemento + "";
        }
    }

    /** La raíz del árbol. */
    protected Vertice raiz;
    /** El número de elementos */
    protected int elementos;

    /**
     * Constructor sin parámetros. Tenemos que definirlo para no perderlo.
     */
    public ArbolBinario() {}

    /**
     * Construye un árbol binario a partir de una colección. El árbol binario
     * tendrá los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario.
     */
    public ArbolBinario(Coleccion<T> coleccion) {
        // Aquí va su código.
        for (T elemento : coleccion)
            agrega(elemento);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link Vertice}. Para
     * crear vértices se debe utilizar este método en lugar del operador
     * <code>new</code>, para que las clases herederas de ésta puedan
     * sobrecargarlo y permitir que cada estructura de árbol binario utilice
     * distintos tipos de vértices.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    protected Vertice nuevoVertice(T elemento) {
        return new Vertice(elemento);
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol es la altura de su
     * raíz.
     * @return la altura del árbol.
     */
    public int altura() {
        // Aquí va su código.
        if (esVacia())
            return -1;
        return raiz.altura();
    }

    /**
     * Regresa el número de elementos que se han agregado al árbol.
     * @return el número de elementos en el árbol.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return elementos;
    }

    /**
     * Nos dice si un elemento está en el árbol binario.
     * @param elemento el elemento que queremos comprobar si está en el árbol.
     * @return <code>true</code> si el elemento está en el árbol;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
        if (elemento == null)
            return false;

        if (raiz == null)
            return false;

        Cola<Vertice> cola = new Cola<Vertice>();
        Vertice vertice;
        cola.mete(raiz);

        while (!cola.esVacia()) {
            vertice = cola.saca();

            if (vertice.elemento.equals(elemento))
                return true;

            if (vertice.hayIzquierdo())
                cola.mete(vertice.izquierdo);

            if (vertice.hayDerecho())
                cola.mete(vertice.derecho);
        }

        return false;
    }

    /**
     * Busca el vértice de un elemento en el árbol. Si no lo encuentra regresa
     * <tt>null</tt>.
     * @param elemento el elemento para buscar el vértice.
     * @return un vértice que contiene el elemento buscado si lo encuentra
     *         <tt>null</tt> en otro caso.
     */
    public VerticeArbolBinario<T> busca(T elemento) {
        // Aquí va su código.
        if (elemento == null || raiz == null)
            return null;

        Cola<Vertice> cola = new Cola<Vertice>();
        Vertice vertice;
        cola.mete(raiz);

        while (!cola.esVacia()) {
            vertice = cola.saca();

            if (vertice.elemento.equals(elemento))
                return vertice;

            if (vertice.hayIzquierdo())
                cola.mete(vertice.izquierdo);

            if (vertice.hayDerecho())
                cola.mete(vertice.derecho);
        }

        return null;
    }

    /**
     * Regresa el vértice que contiene la raíz del árbol.
     * @return el vértice que contiene la raíz del árbol.
     * @throws NoSuchElementException si el árbol es vacío.
     */
    public VerticeArbolBinario<T> raiz() {
        // Aquí va su código.
        if (raiz == null)
            throw new NoSuchElementException();
        return raiz;
    }

    /**
     * Nos dice si el árbol es vacío.
     * @return <code>true</code> si el árbol es vacío, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
        return raiz == null || elementos == 0;
    }

    /**
     * Limpia el árbol de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        // Aquí va su código.
        elementos = 0;
        raiz = null;
    }

    /**
     * Compara el árbol con un objeto.
     * @param objeto el objeto con el que queremos comparar el árbol.
     * @return <code>true</code> si el objeto recibido es un árbol binario y los
     *         árboles son iguales; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked")
            ArbolBinario<T> arbol = (ArbolBinario<T>)objeto;
        // Aquí va su código.
        try {return raiz.equals(arbol.raiz());}
        catch (NoSuchElementException nsee) {return true;}
    }

    /**
     * Crea un String con el esquelto de un Arbol Binario
     * @param nivel bivel de la rama en el que se encuentra
     * @param arreglo arreglo donde tiene el patron para imprimir
     *        el esqueleto del arbol
     * @return esqueleto del subarbol
     */
    private String dibujaEspacios(int nivel, int[] arreglo) {
        String esqueleto = "";
        for (int i = 0; i < nivel; i++)
            if (arreglo[i] == 1)
                esqueleto += "│  ";
            else
                esqueleto += "   ";
        return esqueleto;
    }

    private String toString(Vertice vertice, int nivel,int[] arreglo) {
        String arbol = vertice.toString() + "\n";
        arreglo[nivel] = 1;

        if (vertice.izquierdo != null && vertice.derecho != null) {
            arbol += dibujaEspacios(nivel,arreglo);
            arbol += "├─›";
            arbol += toString(vertice.izquierdo, nivel+1,arreglo);
            arbol += dibujaEspacios(nivel,arreglo);
            arbol +=  "└─»";
            arreglo[nivel] = 0;
            arbol += toString(vertice.derecho, nivel+1, arreglo);
        } else if (vertice.izquierdo != null) {
            arbol += dibujaEspacios(nivel,arreglo);
            arbol += "└─›";
            arreglo[nivel] = 0;
            arbol += toString(vertice.izquierdo,nivel+1, arreglo);
        } else if (vertice.derecho != null) {
            arbol += dibujaEspacios(nivel,arreglo);
            arbol += "└─»";
            arreglo[nivel] = 0;
            arbol += toString(vertice.derecho,nivel+1, arreglo);
        }
        return arbol;
    }

    /**
     * Regresa una representación en cadena del árbol.
     * @return una representación en cadena del árbol.
     */
    @Override public String toString() {
        // Aquí va su código.
        if (esVacia())
            return "";

        int altura = altura();
        int[] arreglo = new int[altura + 1];

        for (int i = 0; i < altura+1; i++)
            arreglo[i] = 0;
        return toString(raiz,0,arreglo);
    }

    /**
     * Convierte el vértice (visto como instancia de {@link
     * VerticeArbolBinario}) en vértice (visto como instancia de {@link
     * Vertice}). Método auxiliar para hacer esta audición en un único lugar.
     * @param vertice el vértice de árbol binario que queremos como vértice.
     * @return el vértice recibido visto como vértice.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         Vertice}.
     */
    protected Vertice vertice(VerticeArbolBinario<T> vertice) {
        return (Vertice)vertice;
    }
}
