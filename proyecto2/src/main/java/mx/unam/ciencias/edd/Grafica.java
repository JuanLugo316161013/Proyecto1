package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
            // Aquí va su código.
            iterador = vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            // Aquí va su código.
            return iterador.next().get();
        }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La lista de vecinos del vértice. */
        public Lista<Vertice> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;
            color = Color.NINGUNO;
            vecinos = new Lista<Vertice>();
        }

        /* Regresa el elemento del vértice. */
        @Override public T get() {
            // Aquí va su código.
            return elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            // Aquí va su código.
            return vecinos.getElementos();
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
            // Aquí va su código.
            return color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            // Aquí va su código.
            return vecinos;
        }
    }

    /* Vértices. */
    private Lista<Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        // Aquí va su código
        vertices = new Lista<Vertice>();
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return vertices.getElementos();
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        // Aquí va su código.
        return aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento ya había sido agregado a
     *         la gráfica.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if (elemento == null)
            throw new IllegalArgumentException();

        for (Vertice vertice : vertices)
            if (elemento.equals(vertice.get()))
                throw new IllegalArgumentException();

        vertices.agrega(new Vertice(elemento));
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
        // Aquí va su código.
        if (a.equals(b))
            throw new IllegalArgumentException();

        Vertice verticeA = null,
        verticeB =  null;

        for (Vertice vertice : vertices) {
            if (a.equals(vertice.get()))
                verticeA = vertice;

            if (b.equals(vertice.get()))
                verticeB = vertice;
        }

        if (verticeA == null || verticeB == null)
            throw new NoSuchElementException();

        if (verticeA.vecinos.contiene(verticeB))
            throw new IllegalArgumentException();

        aristas++;
        verticeA.vecinos.agrega(verticeB);
        verticeB.vecinos.agrega(verticeA);
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
        // Aquí va su código.
        Vertice verticeA = null,
        verticeB =  null;

        for (Vertice vertice : vertices) {
            if (a.equals(vertice.get()))
                verticeA = vertice;

            if (b.equals(vertice.get()))
                verticeB = vertice;
        }

        if (verticeA == null || verticeB == null)
            throw new NoSuchElementException();

        if (!verticeA.vecinos.contiene(verticeB))
            throw new IllegalArgumentException();

        aristas--;
        verticeA.vecinos.elimina(verticeB);
        verticeB.vecinos.elimina(verticeA);
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <tt>true</tt> si el elemento está contenido en la gráfica,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.for (Vertice vertice : vertices)
        for (Vertice vertice : vertices)
            if (elemento.equals(vertice.get()))
                return true;

        return false;
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        Vertice vertice =  null;
        for (Vertice verticeAuxiliar : vertices)
            if (verticeAuxiliar.get().equals(elemento))
                vertice = verticeAuxiliar;

        if (vertice == null)
            throw new NoSuchElementException();

        for (Vertice vecino : vertice.vecinos) {
            vecino.vecinos.elimina(vertice);
            aristas--;
        }

        vertices.elimina(vertice);
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <tt>true</tt> si a y b son vecinos, <tt>false</tt> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
        // Aquí va su código.
        Vertice verticeA = null,
        verticeB = null;

        for (Vertice vertice : vertices) {
            if (a.equals(vertice.get()))
                verticeA = vertice;

            if (b.equals(vertice.get()))
                verticeB = vertice;
        }

        if (verticeA == null || verticeB == null)
            throw new NoSuchElementException();

        return verticeA.vecinos.contiene(verticeB);
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        // Aquí va su código.
        for (Vertice vertice : vertices)
            if (elemento.equals(vertice.get()))
                return vertice;

        throw new NoSuchElementException();
    }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
        if (vertice == null || vertice.getClass() != Vertice.class)
            throw new IllegalArgumentException("Vértice inválido");
        Vertice v = (Vertice)vertice;
        // Aquí va su código.
        v.color = color;
    }

    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
        // Aquí va su código.
        if (vertices.esVacia())
            return false;

        Vertice vertice = vertices.getPrimero();

        for (Vertice rojo : vertices)
            rojo.color = Color.ROJO;

        Cola<Vertice> cola = new Cola<Vertice>();
        vertice.color = Color.NEGRO;
        cola.mete(vertice);

        while (!cola.esVacia()) {
            vertice = cola.saca();
            for (Vertice vecino : vertice.vecinos) {
                if (vecino.color != Color.NEGRO) {
                    vecino.color = Color.NEGRO;
                    cola.mete(vecino);
                }
            }
        }

        boolean conexa = true;

        for (Vertice verticeConexo : vertices) {
            if (verticeConexo.color != Color.NEGRO) {
                conexa = false;
                verticeConexo.color = Color.NINGUNO;
            }
        }

        return conexa;
    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
        for (Vertice vertice : vertices)
            accion.actua(vertice);
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
        Vertice vertice = null;

        for (Vertice verticeAuxiliar : vertices)
            if (elemento.equals(verticeAuxiliar.get()))
                vertice = verticeAuxiliar;

        if (vertice == null)
            throw new NoSuchElementException();

        for (Vertice rojo : vertices)
            rojo.color = Color.ROJO;

        Cola<Vertice> cola = new Cola<Vertice>();
        vertice.color = Color.NEGRO;
        cola.mete(vertice);

        while (!cola.esVacia()) {
            vertice = cola.saca();
            accion.actua(vertice);

            for (Vertice vecino : vertice.vecinos) {
                if (vecino.color != Color.NEGRO) {
                    vecino.color = Color.NEGRO;
                    cola.mete(vecino);
                }
            }
        }

        for (Vertice colorNinguno : vertices)
            colorNinguno.color = Color.NINGUNO;
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
        Vertice vertice = null;

        for (Vertice verticeAuxiliar : vertices)
            if (elemento.equals(verticeAuxiliar.get()))
                vertice = verticeAuxiliar;

        if (vertice == null)
            throw new NoSuchElementException();

        for (Vertice rojo : vertices)
            rojo.color = Color.ROJO;

        Pila<Vertice> pila = new Pila<Vertice>();
        vertice.color = Color.NEGRO;
        pila.mete(vertice);

        while (!pila.esVacia()) {
            vertice = pila.saca();
            accion.actua(vertice);

            for (Vertice vecino : vertice.vecinos) {
                if (vecino.color != Color.NEGRO) {
                    vecino.color = Color.NEGRO;
                    pila.mete(vecino);
                }
            }
        }
        for (Vertice colorNinguno : vertices)
            colorNinguno.color = Color.NINGUNO;
    }

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
        return vertices.esVacia();
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override public void limpia() {
        // Aquí va su código.
        vertices.limpia();
        aristas = 0;
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * @return una representación en cadena de la gráfica.
     */
    @Override public String toString() {
        // Aquí va su código.
        if (vertices.esVacia())
            return "";

        for (Vertice rojo : vertices)
            rojo.color = Color.ROJO;

        String grafica = "{";
        String adyacencias = ", {";
        for (Vertice vertice : vertices) {
            grafica += vertice.get() + ", ";
            for (Vertice vecino : vertice.vecinos)
                if (vecino.color != Color.NEGRO)
                    adyacencias += String.format("(%s, %s), ", vertice.elemento + "", vecino.elemento + "");
            vertice.color = Color.NEGRO;
        }
        grafica += "}";
        adyacencias += "}";

        return grafica + adyacencias;
    }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <tt>true</tt> si la gráfica es igual al objeto recibido;
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Grafica<T> grafica = (Grafica<T>)objeto;
        // Aquí va su código.
        if (grafica.getElementos() != vertices.getElementos() || grafica.getAristas() != aristas)
            return false;

        VerticeGrafica<T> verticeGrafica;

        try {
            for (Vertice vertice : vertices) {
                verticeGrafica = grafica.vertice(vertice.get());
                if (vertice.vecinos.getElementos() != verticeGrafica.getGrado())
                    return false;

                for (VerticeGrafica<T> vecino : verticeGrafica.vecinos())
                    if (!contiene(vertice.vecinos,vecino.get()))
                        return false;
            }
        } catch (NoSuchElementException nsee) {return false;}

        return true;
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Nos dice si un elemento generico esta en un iterable generico de tipo VerticeGrafica
     * @param iterable colecion iterable.
     * @param elemento elemento a verificar si se encuentra en la coleccion iterable
     * @return 'true' si se encuentra en la coleccion ,
     *         'false' si no. 
     */
    private static <T> boolean contiene(Iterable<? extends VerticeGrafica<T>> iterable, T elemento) {
        if (iterable == null || elemento == null)
            return false;

        VerticeGrafica vertice =  null;

        for (VerticeGrafica<T> verticeAuxiliar : iterable)
            if (elemento.equals(verticeAuxiliar.get()))
                vertice = verticeAuxiliar;

        if (vertice == null)
            return false;

        return true;
    }
}
