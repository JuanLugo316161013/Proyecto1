import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Iterable<T>{

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        public T elemento;
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nodo con un elemento. */
        public Nodo(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;
            anterior = null;
            siguiente = null;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nuevo iterador. */
        public Iterador() {
            // Aquí va su código.
            anterior = null;
            siguiente = cabeza;
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
            // Aquí va su código.
            if (!hasNext())
                throw new NoSuchElementException();

            anterior = siguiente;
            siguiente = siguiente.siguiente;
            return anterior.elemento;
        }

        /* Nos dice si hay un elemento anterior. */
        public boolean hasPrevious() {
            // Aquí va su código.
            return anterior != null;
        }

        /* Nos da el elemento anterior. */
        public T previous() {
            // Aquí va su código.
            if (!hasPrevious())
                throw new NoSuchElementException();

            siguiente = anterior;
            anterior = anterior.anterior;
            return siguiente.elemento;
        }

        /* Mueve el iterador al inicio de la lista. */
        public void start() {
            // Aquí va su código.
            siguiente = cabeza;
            anterior = null;
        }

        /* Mueve el iterador al final de la lista. */
        public void end() {
            // Aquí va su código.
            anterior = rabo;
            siguiente = null;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        // Aquí va su código.
        return longitud;
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    public int getElementos() {
        // Aquí va su código.
        return longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    public boolean esVacia() {
        // Aquí va su código.
        return cabeza == null && rabo == null;
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agrega(T elemento) {
        // Aquí va su código.
        if (elemento == null)
            throw new IllegalArgumentException();

        longitud++;

        if (esVacia()) {
            rabo = new Nodo(elemento);
            cabeza = rabo;
            return;
        }
        rabo.siguiente = new Nodo(elemento);
        rabo.siguiente.anterior = rabo;
        rabo = rabo.siguiente;
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        // Aquí va su código.
        if (elemento == null)
            throw new IllegalArgumentException();

        longitud++;

        if (esVacia()) {
            rabo = new Nodo(elemento);
            cabeza = rabo;
            return;
        }

        rabo.siguiente = new Nodo(elemento);
        rabo.siguiente.anterior = rabo;
        rabo = rabo.siguiente;
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        // Aquí va su código.
        if (elemento == null)
            throw new IllegalArgumentException();

        longitud++;

        if (esVacia()) {
            cabeza = new Nodo(elemento);
            rabo = cabeza;
            return;
        }

        cabeza.anterior = new Nodo(elemento);
        cabeza.anterior.siguiente = cabeza;
        cabeza = cabeza.anterior;
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al final de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
        // Aquí va su código.
        if (elemento == null)
            throw new IllegalArgumentException();

        if (i < 1) {
            agregaInicio(elemento);
            return;
        }

        if (i > longitud-1) {
            agregaFinal(elemento);
            return;
        }

        Nodo nodoAuxiliar,nodoNuevo;
        nodoAuxiliar = cabeza;
        nodoNuevo = new Nodo(elemento);

        for (int j = 0; j < i ; j++)
            nodoAuxiliar = nodoAuxiliar.siguiente;

        nodoAuxiliar.anterior.siguiente = nodoNuevo;
        nodoNuevo.anterior = nodoAuxiliar.anterior;
        nodoAuxiliar.anterior = nodoNuevo;
        nodoNuevo.siguiente = nodoAuxiliar;
        longitud++;
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    public void elimina(T elemento) {
        // Aquí va su código.
        if (elemento == null)
            return;
        eliminaNodo(buscaElemento(elemento));
    }

    /**
     * Busca un elemento en la lista y regresa el nodo en el que se.
     * encuentra, si no lo encuentra regresa null.
     * @param elemento elemento a buscar en la lista.
     * @return si encuentra el elemento regresa el nodo en el que se
     *         encuentra si no regresa null.
     */
    private Nodo buscaElemento(T elemento) {
        for (Nodo nodoAuxiliar = cabeza; nodoAuxiliar != null; nodoAuxiliar = nodoAuxiliar.siguiente)
            if (nodoAuxiliar.elemento.equals(elemento))
                return nodoAuxiliar;
        return null;
    }

    /**
     * Elimina el nodo de la lista, si la lista es vacia no hacer nada.
     * @param nodoAEliminar nodo que se va a eliminar de la lista.
     */
    private void eliminaNodo(Nodo nodoAEliminar) {
        if (esVacia())
            return;

        if (nodoAEliminar == cabeza & nodoAEliminar == rabo) {
            limpia();
            return;
        }
        if (nodoAEliminar == cabeza) {
            eliminaPrimero();
            return;
        }
        if (nodoAEliminar == rabo) {
            eliminaUltimo();
            return;
        }
        nodoAEliminar.siguiente.anterior = nodoAEliminar.anterior;
        nodoAEliminar.anterior.siguiente = nodoAEliminar.siguiente;
        longitud--;
    }


    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        // Aquí va su código.
        if (esVacia())
            throw new NoSuchElementException();

        T elemento = cabeza.elemento;
        longitud--;
        if (cabeza == rabo) {
            limpia();
            return elemento;
        }

        cabeza = cabeza.siguiente;
        cabeza.anterior = null;
        return elemento;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        // Aquí va su código.
        if (esVacia())
            throw new NoSuchElementException("La lista es vacía");

        T elemento = rabo.elemento;
        longitud--;
        if (rabo == cabeza) {
            limpia();
            return elemento;
        }

        rabo = rabo.anterior;
        rabo.siguiente = null;
        return elemento;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <tt>true</tt> si <tt>elemento</tt> está en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public boolean contiene(T elemento) {
        // Aquí va su código.
        if (elemento == null)
            return false;

        for (Nodo nodoAuxiliar = cabeza; nodoAuxiliar != null; nodoAuxiliar = nodoAuxiliar.siguiente)
            if (nodoAuxiliar.elemento.equals(elemento))
                return true;
        return false;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        // Aquí va su código.
        Lista<T> listaEnReversa = new Lista<T>();
        for (Nodo nodoAuxiliar = rabo; nodoAuxiliar != null; nodoAuxiliar = nodoAuxiliar.anterior)
            listaEnReversa.agrega(nodoAuxiliar.elemento);
        return listaEnReversa;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        // Aquí va su código.
        Lista<T> listaCopia = new Lista<T>();
        for (Nodo nodoAuxiliar = cabeza; nodoAuxiliar != null; nodoAuxiliar = nodoAuxiliar.siguiente)
            listaCopia.agrega(nodoAuxiliar.elemento);
        return listaCopia;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    public void limpia() {
        // Aquí va su código.
        cabeza = null;
        rabo = null;
        longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        // Aquí va su código.
        if (esVacia())
            throw new NoSuchElementException();
        return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        // Aquí va su código.
        if (esVacia())
            throw new NoSuchElementException();
        return rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws IndexOutOfBoundsException si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
        // Aquí va su código.
        if (i < 0 || i >= longitud)
            throw new IndexOutOfBoundsException();

        if (i == 0)
            return getPrimero();

        if (i-1 == longitud)
            return getUltimo();

        Nodo nodoAuxiliar = cabeza;
        for (int j = 0; j < i; j++)
            nodoAuxiliar = nodoAuxiliar.siguiente;

        return nodoAuxiliar.elemento;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        // Aquí va su código.
        if (elemento == null)
            throw new IllegalArgumentException();

        Nodo nodoAuxiliar = cabeza;
        for (int i = 0; i < longitud; i++)
            if (nodoAuxiliar.elemento.equals(elemento))
                return i;
            else
                nodoAuxiliar = nodoAuxiliar.siguiente;

        return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        // Aquí va su código.
        if (esVacia())
            return "[]";

        String lista = "[" + cabeza.elemento;
        for (Nodo nodoAuxiliar = cabeza.siguiente; nodoAuxiliar != null; nodoAuxiliar = nodoAuxiliar.siguiente)
            lista += ", " + nodoAuxiliar.elemento;
        return lista + "]";
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <tt>true</tt> si la lista es igual al objeto recibido;
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)objeto;
        // Aquí va su código.
        if (longitud != lista.getLongitud())
            return false;
        Nodo obj = lista.cabeza;
        for (Nodo nodoAuxiliar = cabeza; nodoAuxiliar != null; obj = obj.siguiente, nodoAuxiliar = nodoAuxiliar.siguiente)
            if (!nodoAuxiliar.elemento.equals(obj.elemento))
                return false;
        return true;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public Lista<T> mergeSort(Comparator<T> comparador) {
        // Aquí va su código.
        if (longitud <= 1)
            return copia();

        Lista<T> listaOrdenada = new Lista<T>();
        Lista<T> sublistaA = new Lista<T>();
        Lista<T> sublistaB = new Lista<T>();
        Nodo nodolista = cabeza;
        for (int i = 0; i < longitud/2; i++, nodolista = nodolista.siguiente)
            sublistaA.agrega(nodolista.elemento);

        for (int j = longitud/2; j < longitud; j++, nodolista = nodolista.siguiente)
            sublistaB.agrega(nodolista.elemento);

        sublistaA = sublistaA.mergeSort(comparador);
        sublistaB = sublistaB.mergeSort(comparador);
        listaOrdenada.mezcla(sublistaA,sublistaB,comparador);
        return listaOrdenada;
    }
    /**
     * Mezcla dos listas en una de una con los elemento ordenados.
     * @param listaA lista parqa mezclar.
     * @param listaB lista para mezclar.
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     */
    private void mezcla(Lista<T> listaA, Lista<T> listaB, Comparator<T> comparador) {
        Nodo nodoA = listaA.cabeza;
        Nodo nodoB = listaB.cabeza;

        while (nodoA != null && nodoB != null) {
            if (comparador.compare(nodoA.elemento,nodoB.elemento) > 0) {
                agrega(nodoB.elemento);
                nodoB = nodoB.siguiente;
            } else {
                agrega(nodoA.elemento);
                nodoA = nodoA.siguiente;
            }
        }

        for (nodoA = nodoA; nodoA != null; nodoA = nodoA.siguiente)
            agrega(nodoA.elemento);

        for (nodoB = nodoB; nodoB != null; nodoB = nodoB.siguiente)
            agrega(nodoB.elemento);
    }

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>> Lista<T> mergeSort(Lista<T> lista) {
        return lista.mergeSort((a, b) -> a.compareTo(b));
    }

}