package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T>>
    implements Coleccion<T>, MonticuloDijkstra<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Índice del iterador. */
        private int indice;

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return indice < elementos;
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            // Aquí va su código.
            if (indice >= elementos)
                throw new NoSuchElementException();

            return arbol[indice++];
        }
    }

    /* Clase estática privada para adaptadores. */
    private static class Adaptador<T  extends Comparable<T>>
        implements ComparableIndexable<Adaptador<T>> {

        /* El elemento. */
        private T elemento;
        /* El índice. */
        private int indice;

        /* Crea un nuevo comparable indexable. */
        public Adaptador(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;
            indice = -1;
        }

        /* Regresa el índice. */
        @Override public int getIndice() {
            // Aquí va su código.
            return indice;
        }

        /* Define el índice. */
        @Override public void setIndice(int indice) {
            // Aquí va su código.
            this.indice = indice;
        }

        /* Compara un adaptador con otro. */
        @Override public int compareTo(Adaptador<T> adaptador) {
            // Aquí va su código.
            return elemento.compareTo(adaptador.elemento);
        }
    }

    /* El número de elementos en el arreglo. */
    private int elementos;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arbol;

    /* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private T[] nuevoArreglo(int n) {
        return (T[])(new ComparableIndexable[n]);
    }

    /**
     * Constructor sin parámetros. Es más eficiente usar {@link
     * #MonticuloMinimo(Coleccion)} o {@link #MonticuloMinimo(Iterable,int)},
     * pero se ofrece este constructor por completez.
     */
    public MonticuloMinimo() {
        arbol = nuevoArreglo(100); /* 100 es arbitrario. */
    }

    /**
     * Constructor para montículo mínimo que recibe una colección. Es más barato
     * construir un montículo con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param coleccion la colección a partir de la cuál queremos construir el
     *                  montículo.
     */
    public MonticuloMinimo(Coleccion<T> coleccion) {
        this(coleccion, coleccion.getElementos());
    }

    /**
     * Constructor para montículo mínimo que recibe un iterable y el número de
     * elementos en el mismo. Es más barato construir un montículo con todos sus
     * elementos de antemano (tiempo <i>O</i>(<i>n</i>)), que el insertándolos
     * uno por uno (tiempo <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param iterable el iterable a partir de la cuál queremos construir el
     *                 montículo.
     * @param n el número de elementos en el iterable.
     */
    public MonticuloMinimo(Iterable<T> iterable, int n) {
        // Aquí va su código.
        int i = 0;
        elementos = n;
        arbol = nuevoArreglo(n);
        for (T elemento : iterable) {
            arbol[i] = elemento;
            arbol[i].setIndice(i);
            i++;
        }

        for (int j = (int)(n/2); j >= 0; j--)
            heapifyDown(arbol[j]);
    }

    /**
     * Agrega un nuevo elemento en el montículo.
     * @param elemento el elemento a agregar en el montículo.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if (elemento == null)
            return;

        if (elementos == arbol.length) {
            T[] arbolAuxiliar = nuevoArreglo(2*arbol.length);
            for (int i = 0; i < arbol.length; i++)
                arbolAuxiliar[i] = arbol[i];
            arbol = arbolAuxiliar;
        }

        elemento.setIndice(elementos);
        arbol[elementos] = elemento;
        heapifyUp(arbol[elementos++]);
    }

    /**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    @Override public T elimina() {
        // Aquí va su código.
        if (elementos == 0)
            throw new IllegalStateException();

        T elemento = arbol[0];
        intercambia(arbol[0],arbol[elementos-1]);
        elementos--;
        heapifyDown(arbol[0]);
        elemento.setIndice(-1);
        return elemento;
    }

    /**
     * Elimina un elemento del montículo.
     * @param elemento a eliminar del montículo.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        int indice = elemento.getIndice();
        
        if (indice < 0 || indice >= elementos)
            return;

        intercambia(elemento,arbol[elementos-1]);
        elementos--;
        reordena(arbol[indice]);
        arbol[elementos] = null;
        elemento.setIndice(-1);
    }

    /**
     * Nos dice si un elemento está contenido en el montículo.
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
        int indice = elemento.getIndice();
        
        if (indice < 0 || indice >= elementos)
            return false;

        return elemento.equals(arbol[elemento.getIndice()]);
    }

    /**
     * Nos dice si el montículo es vacío.
     * @return <tt>true</tt> si ya no hay elementos en el montículo,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
        return elementos == 0;
    }

    /**
     * Limpia el montículo de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        // Aquí va su código.
        for (int i = 0; i < elementos; i++) {
            arbol[i].setIndice(-1);
            arbol[i] = null;
        }
        elementos = 0;
    }

   /**
     * Reordena un elemento en el árbol.
     * @param elemento el elemento que hay que reordenar.
     */
    @Override public void reordena(T elemento) {
        // Aquí va su código.
        heapifyDown(elemento);
        heapifyUp(elemento);
    }

    /**
     * Regresa el número de elementos en el montículo mínimo.
     * @return el número de elementos en el montículo mínimo.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return elementos;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @param i el índice del elemento que queremos, en <em>in-order</em>.
     * @return el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    @Override public T get(int i) {
        // Aquí va su código.
        if (i < 0 || i >= elementos)
            throw new NoSuchElementException();

        return arbol[i];
    }

    /**
     * Regresa una representación en cadena del montículo mínimo.
     * @return una representación en cadena del montículo mínimo.
     */
    @Override public String toString() {
        // Aquí va su código.
        String monticulo = "";
        for (int i = 0; i < elementos; i++)
            monticulo += arbol[i].toString() + ", ";
        return monticulo;
    }

    /**
     * Nos dice si el montículo mínimo es igual al objeto recibido.
     * @param objeto el objeto con el que queremos comparar el montículo mínimo.
     * @return <code>true</code> si el objeto recibido es un montículo mínimo
     *         igual al que llama el método; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") MonticuloMinimo<T> monticulo =
            (MonticuloMinimo<T>)objeto;
        // Aquí va su código.
        if (elementos != monticulo.getElementos())
            return false;

        for (int i = 0; i < elementos; i++)
            if (!arbol[i].equals(monticulo.get(i)))
                return false;

        return true;
    }

    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se
     * itera en orden BFS.
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Ordena la colección usando HeapSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param coleccion la colección a ordenar.
     * @return una lista ordenada con los elementos de la colección.
     */
    public static <T extends Comparable<T>> Lista<T> heapSort(Coleccion<T> coleccion) {
        // Aquí va su código.
        Lista<Adaptador<T>> adaptadores = new Lista<Adaptador<T>>();
        Lista<T> elementos = new Lista<T>();

        for (T elemento : coleccion)
            adaptadores.agrega(new Adaptador<T>(elemento));

        MonticuloMinimo<Adaptador<T>> monticulo = new MonticuloMinimo<Adaptador<T>>(adaptadores);

        while (!monticulo.esVacia())
            elementos.agrega(monticulo.elimina().elemento);
 
        return elementos;
    }

    /**
     * Nos dice si un elemento en el arreglo tiene un hijo izquierdo.
     * @param elemento elemento que conocer si hay izquierdo
     */
    private boolean hayIzquierdo(T elemento) {
        int izquierdo = 2*elemento.getIndice()+1;
        return izquierdo < elementos; 
    }

    /**
     * Si el elemento tiene izquierdo nos regresa su hijo izquerdo
     * si no regresa null.
     * @param elemento elemento del que queremos su hijo izquierdo.
     */
    private T izquierdo(T elemento) {
        return hayIzquierdo(elemento)? arbol[2*elemento.getIndice()+1] : null;
    }

    /**
     * Nos dice si un elemento en el arreglo tiene un hijo derecho.
     * @param elemento elemento que conocer si hay derecho.
     */
    private boolean hayDerecho(T elemento) {
        int derecho = 2*elemento.getIndice()+2;
        return derecho < elementos;
    }

    /**
     * Si el elemento tiene derecho nos regresa su hijo derecho
     * si no regresa null.
     * @param elemento elemento del que queremos su hijo derecho.
     */
    private T derecho(T elemento) {
        return hayDerecho(elemento)? arbol[2*elemento.getIndice()+2] : null;
    }

    /**
     * Nos dice si un elemento en el arreglo tiene padre.
     * @param elemento elemento que conocer si hay padre.
     */
    private T padre(T elemento) {
        return arbol[(int)((elemento.getIndice()-1)/2)];
    }

    /**
     * Si el elemento tiene derecho nos regresa su derecho
     * si no regresa null.
     * @param elemento elemento del que queremos su derecho.
     */
    private boolean hayPadre(T elemento) {
        return (int)((elemento.getIndice()-1)/2) >= 0;
    }

    /**
     * Acomoda un elemento hací arriba.
     * @param elemento elemento que será acomodado hacía arriba.
     */
    private void heapifyUp(T elemento) {
        if (!hayPadre(elemento) || elemento.compareTo(padre(elemento)) >= 0)
            return;

        intercambia(elemento,padre(elemento));
        heapifyUp(elemento);
    }

    /**
     * Regresa el menor de dos elementos, si ambos elementos son null, regresa null
     * @param a elemento 1.
     * @param b elemento 2.
     * @return elemento menor.
     */
    private T menor(T a, T b) {
        if (a == null && b == null)
            return null;

        if (a == null)
            return b;

        if (b == null)
            return a;

        return a.compareTo(b) <= 0 ? a : b;
    }

    /**
     * Acomoda un elemento hací abajo.
     * @param elemento elemento que será acomodado hacía abajo.
     */
    public void heapifyDown(T elemento) {
        T derecho = derecho(elemento),
          izquierdo = izquierdo(elemento),
          menor = menor(izquierdo,derecho);

        if (menor == null || elemento.compareTo(menor) <= 0)
            return;

        intercambia(elemento,menor);
        heapifyDown(elemento);
    }

    /**
     * intercambia las posiciones, los indices, y las posiciones en el arbol a dos elementos.
     * @param a elemento 1.
     * @param b elemento 2.
     */
    private void intercambia(T a, T b) {
        int indiceA = a.getIndice(),
            indiceB = b.getIndice();
        arbol[indiceA] = b;
        arbol[indiceB] = a;
        arbol[indiceA].setIndice(indiceA);
        arbol[indiceB].setIndice(indiceB);
    }
}