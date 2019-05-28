package mx.unam.ciencias.edd;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para diccionarios (<em>hash tables</em>). Un diccionario generaliza el
 * concepto de arreglo, mapeando un conjunto de <em>llaves</em> a una colección
 * de <em>valores</em>.
 */
public class Diccionario<K, V> implements Iterable<V> {

    /* Clase interna privada para entradas. */
    private class Entrada {

        /* La llave. */
        public K llave;
        /* El valor. */
        public V valor;

        /* Construye una nueva entrada. */
        public Entrada(K llave, V valor) {
            // Aquí va su código.
            this.llave = llave;
            this.valor = valor;
        }

        private String cadena(){
            return String.format("'%s': '%s', ",llave.toString(),valor.toString());
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador {

        /* En qué lista estamos. */
        private int indice;
        /* Iterador auxiliar. */
        private Iterator<Entrada> iterador;

        /* Construye un nuevo iterador, auxiliándose de las listas del
         * diccionario. */
        public Iterador() {
            // Aquí va su código.
            for (int i = 0; i < entradas.length; i++) {
                if (entradas[i] != null) {
                    indice = i;
                    iterador = entradas[i].iterator();
                    break;
                }
            }
        }

        /* Nos dice si hay una siguiente entrada. */
        public boolean hasNext() {
            // Aquí va su código.
            return iterador != null && iterador.hasNext();
        }

        /* Regresa la siguiente entrada. */
        public Entrada siguiente() {
            // Aquí va su código.
            if (iterador == null)
                throw new NoSuchElementException();

            Entrada entrada = null;

            if (iterador.hasNext())
                entrada = iterador.next();

            if (!iterador.hasNext()) {
                iterador = null;

                for (int i = indice + 1; i < entradas.length; i++) {
                    if (entradas[i] != null) {
                        indice = i;
                        iterador = entradas[i].iterator();
                        break;
                    }
                }
            }

            return entrada;
        }
    }

    /* Clase interna privada para iteradores de llaves. */
    private class IteradorLlaves extends Iterador
        implements Iterator<K> {

        /* Regresa el siguiente elemento. */
        @Override public K next() {
            // Aquí va su código.
            return siguiente().llave;
        }
    }

    /* Clase interna privada para iteradores de valores. */
    private class IteradorValores extends Iterador
        implements Iterator<V> {

        /* Regresa el siguiente elemento. */
        @Override public V next() {
            // Aquí va su código.
            return siguiente().valor;
        }
    }

    /** Máxima carga permitida por el diccionario. */
    public static final double MAXIMA_CARGA = 0.72;

    /* Capacidad mínima; decidida arbitrariamente a 2^6. */
    private static final int MINIMA_CAPACIDAD = 64;

    /* Dispersor. */
    private Dispersor<K> dispersor;
    /* Nuestro diccionario. */
    private Lista<Entrada>[] entradas;
    /* Número de valores. */
    private int elementos;

    /* Truco para crear un arreglo genérico. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked")
    private Lista<Entrada>[] nuevoArreglo(int n) {
        return (Lista<Entrada>[])Array.newInstance(Lista.class, n);
    }

    /**
     * Construye un diccionario con una capacidad inicial y dispersor
     * predeterminados.
     */
    public Diccionario() {
        this(MINIMA_CAPACIDAD, (K llave) -> llave.hashCode());
    }

    /**
     * Construye un diccionario con una capacidad inicial definida por el
     * usuario, y un dispersor predeterminado.
     * @param capacidad la capacidad a utilizar.
     */
    public Diccionario(int capacidad) {
        this(capacidad, (K llave) -> llave.hashCode());
    }

    /**
     * Construye un diccionario con una capacidad inicial predeterminada, y un
     * dispersor definido por el usuario.
     * @param dispersor el dispersor a utilizar.
     */
    public Diccionario(Dispersor<K> dispersor) {
        this(MINIMA_CAPACIDAD, dispersor);
    }

    /**
     * Construye un diccionario con una capacidad inicial y un método de
     * dispersor definidos por el usuario.
     * @param capacidad la capacidad inicial del diccionario.
     * @param dispersor el dispersor a utilizar.
     */
    public Diccionario(int capacidad, Dispersor<K> dispersor) {
        // Aquí va su código.
        this.dispersor = dispersor;

        capacidad = (capacidad < MINIMA_CAPACIDAD) ? MINIMA_CAPACIDAD : capacidad;

        int n = 1;
        while (n < capacidad * 2)
            n *= 2;

        entradas = nuevoArreglo(n);
    }

    /**
     * Agrega un nuevo valor al diccionario, usando la llave proporcionada. Si
     * la llave ya había sido utilizada antes para agregar un valor, el
     * diccionario reemplaza ese valor con el recibido aquí.
     * @param llave la llave para agregar el valor.
     * @param valor el valor a agregar.
     * @throws IllegalArgumentException si la llave o el valor son nulos.
     */
    public void agrega(K llave, V valor) {
        // Aquí va su código.
        if (llave == null || valor == null)
            throw new IllegalArgumentException();

        int llaveDispersada = dispersor.dispersa(llave) & (entradas.length - 1);

        if (entradas[llaveDispersada] == null) {
            entradas[llaveDispersada] = new Lista<Entrada>();
            entradas[llaveDispersada].agrega(new Entrada(llave,valor));
            elementos++;
        } else {
            boolean estaLlave = false;

            for (Entrada entrada : entradas[llaveDispersada]) {
                if (entrada.llave.equals(llave)){
                    estaLlave = true;
                    entrada.valor = valor;
                }
            }

            if (!estaLlave) {
                entradas[llaveDispersada].agrega(new Entrada(llave,valor));
                elementos++;
            }
        }

        if (carga() >= MAXIMA_CARGA)
            extiendeDiccionario();
    }

    private void extiendeDiccionario() {
        Lista<Entrada>[] diccionario = entradas;
        entradas = nuevoArreglo(diccionario.length*2);
        int llaveDispersada;

        for (int i = 0; i < diccionario.length; i++) {
            if (diccionario[i] != null) {
                for (Entrada entrada : diccionario[i]) {
                    llaveDispersada = dispersor.dispersa(entrada.llave) & (entradas.length-1);
                    if (entradas[llaveDispersada] != null) {
                        entradas[llaveDispersada].agrega(entrada);
                    } else {
                        Lista<Entrada> nuevaEntrada = new Lista<Entrada>();
                        nuevaEntrada.agrega(entrada);
                        entradas[llaveDispersada] = nuevaEntrada;
                    }
                }
            }
        }
    }

    /**
     * Regresa el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor.
     * @return el valor correspondiente a la llave.
     * @throws IllegalArgumentException si la llave es nula.
     * @throws NoSuchElementException si la llave no está en el diccionario.
     */
    public V get(K llave) {
        // Aquí va su código.
        if (llave == null)
            throw new IllegalArgumentException();

        Lista<Entrada> listaEntradas = entradas[dispersor.dispersa(llave) & (entradas.length-1)];

        if(listaEntradas == null)
            throw new NoSuchElementException();

        V valor = null;

        for (Entrada entrada : listaEntradas)
            if (entrada.llave.equals(llave)) {
                valor = entrada.valor;
            }

        if (valor == null)
            throw new NoSuchElementException();

        return valor;
    }

    /**
     * Nos dice si una llave se encuentra en el diccionario.
     * @param llave la llave que queremos ver si está en el diccionario.
     * @return <tt>true</tt> si la llave está en el diccionario,
     *         <tt>false</tt> en otro caso.
     */
    public boolean contiene(K llave) {
        // Aquí va su código.
        if (llave == null)
            return false;

        Lista<Entrada> listaEntradas = entradas[dispersor.dispersa(llave) & (entradas.length-1)];

        if(listaEntradas == null)
            return false;

        for (Entrada entrada : listaEntradas)
            if (entrada.llave.equals(llave))
                return true;

        return false;
    }

    /**
     * Elimina el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor a eliminar.
     * @throws IllegalArgumentException si la llave es nula.
     * @throws NoSuchElementException si la llave no se encuentra en
     *         el diccionario.
     */
    public void elimina(K llave) {
        // Aquí va su código.
        if (llave == null)
            throw new IllegalArgumentException();

        int llaveDispersada = dispersor.dispersa(llave) & (entradas.length-1);
        Lista<Entrada> listaEntradas = entradas[dispersor.dispersa(llave) & (entradas.length-1)];

        if(listaEntradas == null)
            throw new NoSuchElementException();

        boolean estaLlave = false;
        for (Entrada entrada : listaEntradas) {
            if (entrada.llave.equals(llave)) {
                estaLlave = true;
                listaEntradas.elimina(entrada);
                elementos--;
                if (listaEntradas.esVacia())
                    entradas[llaveDispersada] = null;
                break;
            }
        }

        if (!estaLlave)
            throw new NoSuchElementException();
    }

    /**
     * Nos dice cuántas colisiones hay en el diccionario.
     * @return cuántas colisiones hay en el diccionario.
     */
    public int colisiones() {
        // Aquí va su código.
        int colisiones = 0;
        for (int i = 0; i < entradas.length; i++)
            if (entradas[i] != null)
                colisiones += entradas[i].getElementos() - 1;

        return colisiones;
    }

    /**
     * Nos dice el máximo número de colisiones para una misma llave que tenemos
     * en el diccionario.
     * @return el máximo número de colisiones para una misma llave.
     */
    public int colisionMaxima() {
        // Aquí va su código.
        int colisionMaxima = 0;
        for (int i = 0; i < entradas.length; i++)
            if (entradas[i] != null)
                if (entradas[i].getElementos() >= colisionMaxima)
                    colisionMaxima = entradas[i].getElementos() - 1;

        return colisionMaxima;
    }

    /**
     * Nos dice la carga del diccionario.
     * @return la carga del diccionario.
     */
    public double carga() {
        // Aquí va su código.
        return (double)elementos/entradas.length;
    }

    /**
     * Regresa el número de entradas en el diccionario.
     * @return el número de entradas en el diccionario.
     */
    public int getElementos() {
        // Aquí va su código.
        return elementos;
    }

    /**
     * Nos dice si el diccionario es vacío.
     * @return <code>true</code> si el diccionario es vacío, <code>false</code>
     *         en otro caso.
     */
    public boolean esVacia() {
        // Aquí va su código.
        return elementos == 0;
    }

    /**
     * Limpia el diccionario de elementos, dejándolo vacío.
     */
    public void limpia() {
        // Aquí va su código.
        entradas = nuevoArreglo(MINIMA_CAPACIDAD);
        elementos = 0;
    }

    /**
     * Regresa una representación en cadena del diccionario.
     * @return una representación en cadena del diccionario.
     */
    @Override public String toString() {
        // Aquí va su código.
        if (elementos == 0)
            return "{}";

        String diccionario = "{ ";
        for (int i = 0; i < entradas.length; i++)
            if (entradas[i] != null)
                for (Entrada entrada : entradas[i])
                    diccionario += entrada.cadena();

        return diccionario + "}";
    }

    /**
     * Nos dice si el diccionario es igual al objeto recibido.
     * @param o el objeto que queremos saber si es igual al diccionario.
     * @return <code>true</code> si el objeto recibido es instancia de
     *         Diccionario, y tiene las mismas llaves asociadas a los mismos
     *         valores.
     */
    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") Diccionario<K, V> diccionario =
            (Diccionario<K, V>)o;
        // Aquí va su código.
        if (elementos != diccionario.getElementos())
            return false;

        for (int i = 0; i < entradas.length; i++) {
            if (entradas[i] != null){
                for (Entrada entrada : entradas[i]) {
                    try {
                        if (!entrada.valor.equals(diccionario.get(entrada.llave)))
                            return false;
                    } catch(NoSuchElementException nsee) {return false;}
                }
            }
        }
        return true;
    }

    /**
     * Regresa un iterador para iterar las llaves del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * @return un iterador para iterar las llaves del diccionario.
     */
    public Iterator<K> iteradorLlaves() {
        return new IteradorLlaves();
    }

    /**
     * Regresa un iterador para iterar los valores del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * @return un iterador para iterar los valores del diccionario.
     */
    @Override public Iterator<V> iterator() {
        return new IteradorValores();
    }
}
