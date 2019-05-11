package mx.unam.ciencias.edd;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeAVL extends Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
            // Aquí va su código.
            super(elemento);
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            // Aquí va su código.
            return altura;
        }

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        @Override public String toString() {
            // Aquí va su código.
            int alturaIzquierdo = -1, alturaDerecho = -1;
            String vertice;
            vertice = elemento + " " + altura + "/";
            
            if (izquierdo != null) 
                alturaIzquierdo = izquierdo.altura();

            if (derecho != null)
                alturaDerecho = derecho.altura();

            return vertice += alturaIzquierdo - alturaDerecho;
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)objeto;
            // Aquí va su código.
            return (altura == vertice.altura && super.equals(objeto));
        }
    }

    /* Convierte el vértice a VerticeAVL */
    private VerticeAVL verticeAVL(VerticeArbolBinario<T> vertice) {
        return (VerticeAVL)vertice;
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolAVL() { super(); }

    /**
     * Construye un árbol AVL a partir de una colección. El árbol AVL tiene los
     * mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol AVL.
     */
    public ArbolAVL(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        // Aquí va su código.
        return new VerticeAVL(elemento);
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        super.agrega(elemento);
        VerticeAVL vertice = verticeAVL(ultimoAgregado);
        /* Rebalancea el arbol si es que se ah desbalanceado despues de agregar*/
        rebalancea(verticeAVL(vertice.padre));
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        if (elemento == null || esVacia())
            return;

        VerticeAVL vertice = verticeAVL(super.busca(elemento));

        if (vertice == null)
            return;

        elementos--;
        if (vertice.hayDerecho() && vertice.hayIzquierdo())
            vertice = verticeAVL(super.intercambiaEliminable(vertice));
        
        super.eliminaVertice(vertice);
        /* Rebalancea el arbol si es que se ah desbalanceado despues de eliminar*/
        rebalancea(verticeAVL(vertice.padre));
    }


    /**
     * Rebalancea el arbol recursivamente despues de agregar o eliminar un vertice,
     * recibe el padre del vertice eliminado o agregado.
     * @param vertice padre del vertice eliminado o agregado.
     */
    private void rebalancea(VerticeAVL vertice) {
        /* Clausula de escape */
        if (vertice == null)
            return;

        VerticeAVL hijoIzquierdo = verticeAVL(vertice.izquierdo); // hijo izquierdo de vertice.
        VerticeAVL hijoDerecho = verticeAVL(vertice.derecho); // hijo derecho de vertice.
        VerticeAVL sobrinoIzquierdo;
        VerticeAVL sobrinoDerecho;

        vertice.altura = altura(vertice);

        /* Si el balance del vertice es -2 (b(v) = -2)*/
        if (altura(hijoIzquierdo) - altura(hijoDerecho) == -2) {

            sobrinoIzquierdo = verticeAVL(hijoDerecho.izquierdo);
            sobrinoDerecho = verticeAVL(hijoDerecho.derecho);
            /* Caso extremo para b(v) = -2 */
            if (altura(sobrinoIzquierdo) - altura(sobrinoDerecho) == 1) {
                super.giraDerecha(hijoDerecho);
                hijoDerecho.altura = altura(hijoDerecho);
                sobrinoIzquierdo.altura = altura(sobrinoIzquierdo);
            }

            hijoDerecho = verticeAVL(vertice.derecho);
            super.giraIzquierda(vertice);
            vertice.altura = altura(vertice);
            hijoDerecho.altura = altura(hijoDerecho);
            rebalancea(verticeAVL(hijoDerecho.padre));
            return;
        }

        /* Si el balance del vertice es 2 (b(v) = 2)*/
        if (altura(hijoIzquierdo) - altura(hijoDerecho) == 2) {

            sobrinoIzquierdo = verticeAVL(hijoIzquierdo.izquierdo);
            sobrinoDerecho = verticeAVL(hijoIzquierdo.derecho);
            /* Caso extremo para b(v) = 2 */
            if (altura(sobrinoIzquierdo) - altura(sobrinoDerecho) == -1) {
                super.giraIzquierda(hijoIzquierdo);
                hijoIzquierdo.altura = altura(hijoIzquierdo);
                sobrinoDerecho.altura = altura(sobrinoIzquierdo);
            }

            hijoIzquierdo = verticeAVL(vertice.izquierdo);
            super.giraDerecha(vertice);
            vertice.altura = altura(vertice);
            hijoIzquierdo.altura = altura(hijoIzquierdo);
            rebalancea(verticeAVL(hijoIzquierdo.padre));
            return;
        }
        rebalancea(verticeAVL(vertice.padre));
    }

    /**
     * Regresa la altura de un vertice que se ha movido.
     * @param vertice vertice que cambio de posicion.
     * @return altura del vertice.
     */
    private int altura(VerticeAVL vertice) {
        if (vertice == null)
            return -1;

        VerticeAVL izquierdo = verticeAVL(vertice.izquierdo);
        VerticeAVL derecho = verticeAVL(vertice.derecho);
        int alturaIzquierdo = -1, alturaDerecho = -1;
        
        if (izquierdo != null)
            alturaIzquierdo = izquierdo.altura;
        
        if (derecho != null)
            alturaDerecho = derecho.altura;

        if (alturaIzquierdo < alturaDerecho)
            return alturaDerecho + 1;

        return alturaIzquierdo + 1; 
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la izquierda por el " +
                                                "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la derecha por el " +
                                                "usuario.");
    }
}