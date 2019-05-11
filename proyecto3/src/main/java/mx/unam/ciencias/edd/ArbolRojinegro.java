package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<tt>null</tt>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            // Aquí va su código.
            super(elemento);
            color = Color.NINGUNO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        public String toString() {
            // Aquí va su código.
            if (color == Color.ROJO)
                return "R{"+elemento+"}";
            return "N{"+elemento+"}";
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
                VerticeRojinegro vertice = (VerticeRojinegro)objeto;
            // Aquí va su código.
            return color == vertice.color && super.equals(objeto);
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() { super(); }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeRojinegro(elemento);
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
        if (!(vertice instanceof ArbolRojinegro.VerticeRojinegro))
            throw new ClassCastException();

        if (vertice == null)
            return Color.NEGRO;

        VerticeRojinegro verticeRJ = verticeRojinegro(vertice);
        return verticeRJ.color;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        super.agrega(elemento);
        VerticeRojinegro vertice = verticeRojinegro(ultimoAgregado);
        vertice.color = Color.ROJO;
        agrega(vertice);
    }
    /**
     * Balancea el arbol sobre un vertice agregado
     * @param vertice vertice agregado
     */
    private void agrega(VerticeRojinegro vertice) {
        VerticeRojinegro abuelo;
        VerticeRojinegro padre;
        VerticeRojinegro tio;
        VerticeRojinegro verticeAuxilar;

        // caso 1
        /* Si no hay padre pintamos al vertice de negro */
        if (!vertice.hayPadre()) {
            vertice.color = Color.NEGRO;
            return;
        }

        // caso 2
        /* Si el padre es negro terminamos */
        padre = verticeRojinegro(vertice.padre);
        if (padre.color == Color.NEGRO)
            return;

        // caso 3
        abuelo = verticeRojinegro(padre.padre);
        tio = null;

        if (abuelo.izquierdo == padre)
            tio = verticeRojinegro(abuelo.derecho);

        if (abuelo.derecho == padre)
            tio = verticeRojinegro(abuelo.izquierdo);

        /* Si el tio es rojo, pitamos al tio y a la padre de negro
        y hacemos recursion el abuelo y regresando terminamos*/
        if (tio != null && tio.color == Color.ROJO) {
            padre.color = Color.NEGRO;
            tio.color = Color.NEGRO;
            abuelo.color = Color.ROJO;
            agrega(abuelo);
            return;
        }

        // caso 4
        /* Si el vertice y su padre estan cruzados, es decir que sus
           direcciones son diferentes, giramos en la direccion del padre 
           despues hacemos que el vertice sea e padre y el padre sea 
           el vertice */
        boolean caso4 = false;
        
        if (abuelo.izquierdo == padre && padre.derecho == vertice) {
            super.giraIzquierda(padre);
            caso4 = true;
        }

        if (abuelo.derecho == padre && padre.izquierdo == vertice) {
            super.giraDerecha(padre);
            caso4 = true;
        }

        // Reetiquetamos
        if (caso4) {
            verticeAuxilar = vertice;
            vertice = padre;
            padre = verticeAuxilar;
            abuelo = verticeRojinegro(padre.padre);
        }

        // caso 5
        /* Si el vertice y su padre tiene la misma direccion,coloreamos al
           padre de negro, al vértice abuelo de rojo y giramos el abuelo
           en la dirección contraria a la del vertice */
        padre.color = Color.NEGRO;
        abuelo.color = Color.ROJO;

        if (padre.izquierdo == vertice)
            super.giraDerecha(abuelo);
        else
            super.giraIzquierda(abuelo);
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        if (elemento == null || raiz == null)
            return;

        VerticeRojinegro vertice = verticeRojinegro(super.busca(elemento));

        if (vertice == null)
            return;

        VerticeRojinegro fantasma = null;
        int hijos = 0;
        elementos--;

        if (vertice.hayIzquierdo())
            hijos++;

        if (vertice.hayDerecho())
            hijos++;

        if (hijos == 2) {
            vertice = verticeRojinegro(super.intercambiaEliminable(vertice));
            if (vertice.hayIzquierdo()) {
                fantasma = verticeRojinegro(vertice.izquierdo);
            } else {
                fantasma = verticeRojinegro(nuevoVertice(null));
                fantasma.color = Color.NEGRO;
                vertice.derecho = fantasma;
                fantasma.padre = vertice;
            }
        }

        if (hijos == 1) {
            if (vertice.hayIzquierdo())
                fantasma = verticeRojinegro(vertice.izquierdo);
            else
                fantasma = verticeRojinegro(vertice.derecho);
        }

        if (hijos == 0) {
            fantasma = verticeRojinegro(nuevoVertice(null));
            fantasma.color = Color.NEGRO;
            vertice.derecho = fantasma;
            fantasma.padre = vertice;
        }

        super.eliminaVertice(vertice);

        if (fantasma.color == Color.ROJO && vertice.color == Color.NEGRO)
            fantasma.color = Color.NEGRO;
        else
            if (fantasma.color == Color.NEGRO && vertice.color == Color.NEGRO)
                rebalancear(fantasma);

        if (fantasma.elemento == null) {
            if (vertice.hayPadre()) {
                if (fantasma.padre.izquierdo == fantasma) {
                    fantasma.padre.izquierdo = null;
                } else {
                    fantasma.padre.derecho = null;
                }
            } else { 
                raiz = null;
            }
        }
    }

    /**
     * Rebalancea el arbol sobre un vertice el hijo de un
     * vertice eliminado (puede ser un vertice fantasma)
     * @param vertice vertice donde se rebalancea
     */
    private void rebalancear(VerticeRojinegro vertice) {
        VerticeRojinegro hermano;
        VerticeRojinegro padre;
        /* hijo izquierdo del hermano */
        VerticeRojinegro izquierdo;
        /* hijo derecho del hermano */
        VerticeRojinegro derecho;

        // caso 1
        /* Si el padre del vertice es vacio entonces terminamos */
        if(!vertice.hayPadre())
            return;

        // caso 2
        boolean caso2 = false;
        padre = verticeRojinegro(vertice.padre);

        if (padre.izquierdo == vertice)
            hermano = verticeRojinegro(padre.derecho);
        else
            hermano = verticeRojinegro(padre.izquierdo);

        /* Si el hijo del vertice es rojo, pintamos al padre de rojo,
           al hijo de negro, giramos sobre el padre en la direccion
           del vertice */
        if (hermano.color == Color.ROJO) {
            caso2 = true;
            hermano.color = Color.NEGRO;
            padre.color = Color.ROJO;
            if (padre.derecho == vertice)
                super.giraDerecha(padre);
            else
                super.giraIzquierda(padre);
        }

        if (caso2) {
            if (padre.izquierdo == vertice)
                hermano = verticeRojinegro(padre.derecho);
            else
                hermano = verticeRojinegro(padre.izquierdo);
        }

        // caso3
        izquierdo = verticeRojinegro(hermano.izquierdo);
        derecho = verticeRojinegro(hermano.derecho);
  
        /* Si el los dos hijos del hemano son negros, el padre
           es negro y el hermano es negro, pintamos al hermano
           de rojo, hacemos recursion sobre el padre y terminamos */
        if ((izquierdo == null || izquierdo.color == Color.NEGRO) &&
            (derecho == null || derecho.color == Color.NEGRO) &&
            padre.color == Color.NEGRO && hermano.color == Color.NEGRO)
        {
            hermano.color = Color.ROJO;
            rebalancear(padre);
            return;
        }

        // caso4
        /*Si los hijos del hermano son negros, el hermano es negro 
          y el padre es rojo coloreamos al hermano de rojo y al padre
          de negro, y terminamos*/
        if ((izquierdo == null || izquierdo.color == Color.NEGRO) &&
            (derecho == null || derecho.color == Color.NEGRO) &&
            padre.color == Color.ROJO && hermano.color == Color.NEGRO)
        {
            hermano.color = Color.ROJO;
            padre.color = Color.NEGRO;
            return;
        }

        // caso 5
        boolean caso5 = false;
        /* Si el vertice es izquierdo, el izquerdo del hermano es rojo,
           el dereho del hermano es negro ó el vertice es derecho, el
           derecho del hermano es negro y el izquierdo del hermano es
           rojo, coloreamos al hermano de rojo, al hijo rojo del hermano
           lo coloreamos de negro y giramos sobre el hermano en la 
           direccion contraria al vertice */
        if ((padre.izquierdo == vertice) &&
           (izquierdo != null && izquierdo.color == Color.ROJO) &&
           (derecho == null || derecho.color == Color.NEGRO))
        {
            caso5 = true;
            hermano.color = Color.ROJO;
            izquierdo.color = Color.NEGRO;
            super.giraDerecha(hermano);
        }
        else
            if ((padre.derecho == vertice) &&
               (izquierdo == null || izquierdo.color == Color.NEGRO) &&
               (derecho != null && derecho.color == Color.ROJO))
            {
                caso5 = true;
                hermano.color = Color.ROJO;
                derecho.color = Color.NEGRO;
                super.giraIzquierda(hermano);
            }

        if (caso5) {
            if (padre.izquierdo == vertice)
                hermano = verticeRojinegro(padre.derecho);
            else
                hermano = verticeRojinegro(padre.izquierdo);

            izquierdo = verticeRojinegro(hermano.izquierdo);
            derecho = verticeRojinegro(hermano.derecho);
        }

        // caso6
        /* si el vertice es izquierdo y el izquierdo del hermano es
           rojo ó el vertice es derecho y el derecho del hermano es
           rojo, coloreamos al hermano del color del padre, al padre
           de negro al hijo del hermano con direccion contraria del
           vertice de negro, y giramos sobre el padre en la direccion
           contraria del vertice */
        hermano.color = padre.color;
        padre.color = Color.NEGRO;
        
        if (padre.izquierdo == vertice) {
            derecho.color = Color.NEGRO;
            super.giraIzquierda(padre);
        } else {
            izquierdo.color = Color.NEGRO;
            super.giraDerecha(padre);
        }
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la izquierda " +
                                                "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la derecha " +
                                                "por el usuario.");
    }

    /**
     * Convierte el vértice (visto como instancia de {@link
     * VerticeArbolBinario}) en vértice (visto como instancia de {@link
     * VerticeRojimegro}). Método auxiliar para hacer esta audición en un único lugar.
     * @param vertice el vértice de árbol binario que queremos como vértice.
     * @return el vértice recibido visto como vérticeRojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    private VerticeRojinegro verticeRojinegro(VerticeArbolBinario<T> vertice) {
        VerticeRojinegro v = (VerticeRojinegro)vertice;
        return v;
    }
}