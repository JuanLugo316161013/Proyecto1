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
            return iterador.next().elemento;
        }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T>,
                          ComparableIndexable<Vertice> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La distancia del vértice. */
        public double distancia;
        /* El índice del vértice. */
        public int indice;
        /* La lista de vecinos del vértice. */
        public Lista<Vecino> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;
            vecinos = new Lista<Vecino>();
            color = Color.NINGUNO;
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

        /* Define el índice del vértice. */
        @Override public void setIndice(int indice) {
            // Aquí va su código.
            this.indice = indice;
        }

        /* Regresa el índice del vértice. */
        @Override public int getIndice() {
            // Aquí va su código.
            return indice;
        }

        /* Compara dos vértices por distancia. */
        @Override public int compareTo(Vertice vertice) {
            // Aquí va su código.
            if (distancia > vertice.distancia)
                return 1;

            if (distancia < vertice.distancia)
                return -1;

            return 0;
        }

        private Vecino vecino(T elemento) {
            for(Vecino vecino : vecinos)
                if (vecino.vecino.elemento.equals(elemento))
                    return vecino;

            throw new NoSuchElementException();
        }
    }

    /* Clase interna privada para vértices vecinos. */
    private class Vecino implements VerticeGrafica<T> {

        /* El vértice vecino. */
        public Vertice vecino;
        /* El peso de la arista conectando al vértice con su vértice vecino. */
        public double peso;

        /* Construye un nuevo vecino con el vértice recibido como vecino y el
         * peso especificado. */
        public Vecino(Vertice vecino, double peso) {
            // Aquí va su código.
            this.vecino = vecino;
            this.peso = peso;
        }

        /* Regresa el elemento del vecino. */
        @Override public T get() {
            // Aquí va su código.
            return vecino.elemento;
        }

        /* Regresa el grado del vecino. */
        @Override public int getGrado() {
            // Aquí va su código.
            return vecino.getGrado();
        }

        /* Regresa el color del vecino. */
        @Override public Color getColor() {
            // Aquí va su código.
            return vecino.color;
        }

        /* Regresa un iterable para los vecinos del vecino. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            // Aquí va su código.
            return vecino.vecinos;
        }
    }

    /* Interface para poder usar lambdas al buscar el elemento que sigue al
     * reconstruir un camino. */
    @FunctionalInterface private interface BuscadorCamino {
        /* Regresa true si el vértice se sigue del vecino. */
        public boolean seSiguen(Grafica.Vertice v, Grafica.Vecino a);
    }

    /* Vértices. */
    private Lista<Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        // Aquí va su código.
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
            if (elemento.equals(vertice.elemento))
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
        if (a == null || b == null || a.equals(b))
            throw new IllegalArgumentException();

        Vertice verticeA = null, verticeB = null;

        for (Vertice vertice : vertices)
            if (a.equals(vertice.elemento))
                verticeA = vertice;
            else
                if (b.equals(vertice.elemento))
                    verticeB = vertice;

        if (verticeA == null)
            throw new NoSuchElementException();

        if (verticeB == null)
            throw new NoSuchElementException();

        for (Vecino vecino : verticeA.vecinos)
            if (b.equals(vecino.vecino.elemento))
                throw new IllegalArgumentException();

        aristas++;
        verticeA.vecinos.agrega(new Vecino(verticeB,1));
        verticeB.vecinos.agrega(new Vecino(verticeA,1));
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @param peso el peso de la nueva vecino.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, si a es
     *         igual a b, o si el peso es no positivo.
     */
    public void conecta(T a, T b, double peso) {
        // Aquí va su código.
        if (a == null || b == null || peso < 0 || a.equals(b))
            throw new IllegalArgumentException();

        Vertice verticeA = null, verticeB = null;

        for (Vertice vertice : vertices)
            if (a.equals(vertice.elemento))
                verticeA = vertice;
            else
                if (b.equals(vertice.elemento))
                    verticeB = vertice;

        if (verticeA == null)
            throw new NoSuchElementException();

        if (verticeB == null)
            throw new NoSuchElementException();

        for (Vecino vecino : verticeA.vecinos)
            if (b.equals(vecino.vecino.elemento))
                throw new IllegalArgumentException();

        aristas++;
        verticeA.vecinos.agrega(new Vecino(verticeB,peso));
        verticeB.vecinos.agrega(new Vecino(verticeA,peso));
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
        if (a == null || b == null || a.equals(b))
            throw new IllegalArgumentException();

        Vertice verticeA = null, verticeB = null;

        for (Vertice vertice : vertices)
            if (a.equals(vertice.elemento))
                verticeA = vertice;
            else
                if (b.equals(vertice.elemento))
                    verticeB = vertice;

        if (verticeA == null)
            throw new NoSuchElementException();

        if (verticeB == null)
            throw new NoSuchElementException();

        Vecino vecinoA = null, vecinoB = null;

        for (Vecino vecino : verticeA.vecinos) {
            if (b.equals(vecino.vecino.elemento)) {
                vecinoB = vecino;
                break;
            }
        }

        for (Vecino vecino : verticeB.vecinos) {
            if (a.equals(vecino.vecino.elemento)) {
                vecinoA = vecino;
                break;
            }
        }

        if (vecinoA == null || verticeB == null)
            throw new IllegalArgumentException();

        aristas--;
        verticeA.vecinos.elimina(vecinoB);
        verticeB.vecinos.elimina(vecinoA);
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <tt>true</tt> si el elemento está contenido en la gráfica,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
        for (Vertice vertice : vertices)
            if (vertice.elemento.equals(elemento))
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
        if (elemento == null)
            return;

        Vertice vertice = null;

        for (Vertice v : vertices)
            if (v.elemento.equals(elemento))
                vertice = v;

        if (vertice == null)
            throw new NoSuchElementException();

        for (Vecino vecino : vertice.vecinos)
            for (Vecino v : vecino.vecino.vecinos)
                if (v.vecino.elemento.equals(elemento))
                    vecino.vecino.vecinos.elimina(v);

        vertices.elimina(vertice);

        aristas -= vertice.getGrado();
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
        Vertice verticeA = null, verticeB = null;

        for (Vertice vertice : vertices)
            if (a.equals(vertice.elemento))
                verticeA = vertice;
            else
                if (b.equals(vertice.elemento))
                    verticeB = vertice;

        if (verticeA == null)
            throw new NoSuchElementException();

        if (verticeB == null)
            throw new NoSuchElementException();

        for (Vecino vecino : verticeA.vecinos)
            if (b.equals(vecino.vecino.elemento))
                return true;

        return false;
    }

    /**
     * Regresa el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return el peso de la arista que comparten los vértices que contienen a
     *         los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public double getPeso(T a, T b) {
        // Aquí va su código.
        Vertice verticeA = null, verticeB = null;

        for (Vertice vertice : vertices)
            if (a.equals(vertice.elemento))
                verticeA = vertice;
            else
                if (b.equals(vertice.elemento))
                    verticeB = vertice;

        if (verticeA == null)
            throw new NoSuchElementException();

        if (verticeB == null)
            throw new NoSuchElementException();

        for (Vecino vecino : verticeA.vecinos)
            if (b.equals(vecino.vecino.elemento))
                return vecino.peso;

        throw new IllegalArgumentException();
    }

    /**
     * Define el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @param peso el nuevo peso de la arista que comparten los vértices que
     *        contienen a los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados, o si peso
     *         es menor o igual que cero.
     */
    public void setPeso(T a, T b, double peso) {
        // Aquí va su código.
        if (peso < 0)
            throw new IllegalArgumentException();

        Vertice verticeA = null, verticeB = null;

        for (Vertice vertice : vertices)
            if (a.equals(vertice.elemento))
                verticeA = vertice;
            else
                if (b.equals(vertice.elemento))
                    verticeB = vertice;

        if (verticeA == null)
            throw new NoSuchElementException();

        if (verticeB == null)
            throw new NoSuchElementException();

        Vecino vecinoA = null, vecinoB = null;

        for (Vecino vecino : verticeA.vecinos) {
            if (b.equals(vecino.vecino.elemento)) {
                vecinoB = vecino;
                break;
            }
        }

        for (Vecino vecino : verticeB.vecinos) {
            if (a.equals(vecino.vecino.elemento)) {
                vecinoA = vecino;
                break;
            }
        }

        if (vecinoA == null || verticeB == null)
            throw new IllegalArgumentException();

        vecinoA.peso = peso;
        vecinoB.peso = peso;
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
            if (vertice.elemento.equals(elemento))
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
        // Aquí va su código.
        if (vertice == null || (vertice.getClass() != Vertice.class && vertice.getClass() != Vecino.class)) {
            throw new IllegalArgumentException();
        }
            if (vertice.getClass() == Vertice.class) {
            Vertice v = (Vertice)vertice;
            v.color = color;
        }
            if (vertice.getClass() == Vecino.class) {
            Vecino v = (Vecino)vertice;
            v.vecino.color = color;
        }
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

        Cola<Vertice> cola = new Cola<Vertice>();
        Vertice vertice = vertices.getPrimero();
        vertice.color = Color.NEGRO;
        cola.mete(vertices.getPrimero());

        while (!cola.esVacia()) {
            vertice = cola.saca();
            for (Vecino vecino : vertice.vecinos)
                if (vecino.vecino.color != Color.NEGRO) {
                    vecino.vecino.color = Color.NEGRO;
                    cola.mete(vecino.vecino);
                }
        }

        boolean esConexa = true;

        for (Vertice v : vertices)
            if (v.color == Color.NEGRO)
                v.color = Color.NINGUNO;
            else
                esConexa = false;

        return esConexa;
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
        if (vertices.esVacia())
            return;

        Cola<Vertice> cola = new Cola<Vertice>();
        Vertice vertice = vertices.getPrimero();
        vertice.color = Color.NEGRO;
        cola.mete(vertices.getPrimero());

        while (!cola.esVacia()) {
            vertice = cola.saca();
            accion.actua(vertice);
            for (Vecino vecino : vertice.vecinos)
                if (vecino.vecino.color != Color.NEGRO) {
                    vecino.vecino.color = Color.NEGRO;
                    cola.mete(vecino.vecino);
                }
        }

        for (Vertice v : vertices)
            v.color = Color.NINGUNO;
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
        if (vertices.esVacia())
            return;

        Pila<Vertice> pila = new Pila<Vertice>();
        Vertice vertice = vertices.getPrimero();
        vertice.color = Color.NEGRO;
        pila.mete(vertices.getPrimero());

        while (!pila.esVacia()) {
            vertice = pila.saca();
            accion.actua(vertice);
            for (Vecino vecino : vertice.vecinos)
                if (vecino.vecino.color != Color.NEGRO) {
                    vecino.vecino.color = Color.NEGRO;
                    pila.mete(vecino.vecino);
                }
        }

        for (Vertice v : vertices)
            v.color = Color.NINGUNO;
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

        String grafica = "{";
        String adyacencias = ", {";
        for (Vertice vertice : vertices) {
            grafica += vertice.get() + ", ";
            for (Vecino vecino : vertice.vecinos)
                if (vecino.vecino.color != Color.NEGRO)
                    adyacencias += String.format("(%s, %s), ", vertice.elemento.toString(), vecino.vecino.elemento.toString());
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

        try {
            for (Vertice vertice : vertices) {
                VerticeGrafica<T> verticeGrafica = grafica.vertice(vertice.elemento);
                for (VerticeGrafica<T> vecino : verticeGrafica.vecinos())
                    vertice.vecino(vecino.get());
            }
        } catch (NoSuchElementException nsee){return false;}

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
     * Calcula una trayectoria de distancia mínima entre dos vértices.
     * @param origen el vértice de origen.
     * @param destino el vértice de destino.
     * @return Una lista con vértices de la gráfica, tal que forman una
     *         trayectoria de distancia mínima entre los vértices <tt>a</tt> y
     *         <tt>b</tt>. Si los elementos se encuentran en componentes conexos
     *         distintos, el algoritmo regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> trayectoriaMinima(T origen, T destino) {
        // Aquí va su código.
        Vertice verticeOrigen = null, verticeDestino = null, vertice = null;

        for (Vertice v : vertices) {
            v.distancia = -1;

            if (v.elemento.equals(origen))
                verticeOrigen = v;

            if (v.elemento.equals(destino))
                verticeDestino = v;
        }

        if (verticeOrigen == null || verticeDestino == null)
            throw new NoSuchElementException();

        verticeOrigen.distancia = 0;
        Lista<VerticeGrafica<T>> trayectoria = new Lista<VerticeGrafica<T>>();

        if (verticeOrigen == verticeDestino) {
            trayectoria.agrega(verticeOrigen);
            return trayectoria;
        }

        Cola<Vertice> cola = new Cola<Vertice>();
        cola.mete(verticeOrigen);

        while (!cola.esVacia()) {
            vertice = cola.saca();
            for (Vecino vecino : vertice.vecinos) {
                if (vecino.vecino.distancia == -1) {
                    vecino.vecino.distancia = vertice.distancia + 1;
                    cola.mete(vecino.vecino);
                }
            }
        }

        if (verticeDestino.distancia == -1)
            return trayectoria;

        trayectoria.agrega(verticeDestino);
        vertice = verticeDestino;

        while (vertice != verticeOrigen) {
            for (Vecino vecino : vertice.vecinos) {
                if (vertice.distancia == vecino.vecino.distancia + 1) {
                    vertice = vecino.vecino;
                    trayectoria.agregaInicio(vertice);
                    break;
                }
            }
        }

        return trayectoria;
    }

    /**
     * Calcula la ruta de peso mínimo entre el elemento de origen y el elemento
     * de destino.
     * @param origen el vértice origen.
     * @param destino el vértice destino.
     * @return una trayectoria de peso mínimo entre el vértice <tt>origen</tt> y
     *         el vértice <tt>destino</tt>. Si los vértices están en componentes
     *         conexas distintas, regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> dijkstra(T origen, T destino) {
        // Aquí va su código.
        Vertice verticeOrigen = null, verticeDestino = null, vertice = null;

        for (Vertice v : vertices) {
            v.distancia = Double.POSITIVE_INFINITY;

            if (v.elemento.equals(origen))
                verticeOrigen = v;

            if (v.elemento.equals(destino))
                verticeDestino = v;
        }

        if (verticeOrigen == null || verticeDestino == null)
            throw new NoSuchElementException();

        verticeOrigen.distancia = 0;
        Lista<VerticeGrafica<T>> trayectoria = new Lista<VerticeGrafica<T>>();
        MonticuloDijkstra<Vertice> monticulo;
        int n = vertices.getElementos();

        if (aristas > ((n *( n - 1)) / 2) - n )
            monticulo = new MonticuloArreglo<Vertice>(vertices);
        else
            monticulo = new MonticuloMinimo<Vertice>(vertices);

        while (!monticulo.esVacia()) {
            vertice = monticulo.elimina();
            for (Vecino vecino : vertice.vecinos) {
                if (vecino.vecino.distancia > vertice.distancia + vecino.peso) {
                    vecino.vecino.distancia = vecino.peso + vertice.distancia;
                    monticulo.reordena(vecino.vecino);
                }
            }
        }

        if (verticeDestino.distancia ==  Double.POSITIVE_INFINITY)
            return trayectoria;

        trayectoria.agrega(verticeDestino);
        vertice = verticeDestino;
        while (vertice != verticeOrigen) {
            for (Vecino vecino : vertice.vecinos) {
                if (vecino.vecino.distancia == vertice.distancia - vecino.peso) {
                    vertice = vecino.vecino;
                    trayectoria.agregaInicio(vertice);
                    break;
                }
            }
        }
        return trayectoria;
    }
}
