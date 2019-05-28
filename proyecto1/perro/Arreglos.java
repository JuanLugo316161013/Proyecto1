package mx.unam.ciencias.edd;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciación. */
    private Arreglos() {}

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void quickSort(T[] arreglo, Comparator<T> comparador) {
        // Aquí va su código.
        quickSort(arreglo,0,arreglo.length-1,comparador);
    }

    /**
     * Implementa el metodo de ordenamiento quickSort en un arreglo recursivamente
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo arreglo a a ordenar.
     * @param inicio primer posición del subarreglo.
     * @param fin ultima posicion del subarreglo.
     * @param comparador el comparador para ordenar el arreglo.
     */
    private static <T> void quickSort(T[] arreglo, int inicio, int fin, Comparator<T> comparador) {
        // Aquí va su código.
        if (fin <= inicio)
            return;

        int i = inicio + 1, j = fin;
        while(i < j)
            if (comparador.compare(arreglo[i],arreglo[inicio]) > 0 && comparador.compare(arreglo[j],arreglo[inicio]) <= 0)
                intercambia(arreglo,i++,j--);
             else
                if (comparador.compare(arreglo[i],arreglo[inicio]) <= 0)
                    i++;
                else
                    j--;

        if (comparador.compare(arreglo[i],arreglo[inicio]) > 0)
            i--;
        intercambia(arreglo,i,inicio);
        quickSort(arreglo,inicio,i-1,comparador);
        quickSort(arreglo,i+1,fin,comparador);
    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void selectionSort(T[] arreglo, Comparator<T> comparador) {
        // Aquí va su código.
        for (int i = 0; i < arreglo.length; i++) {
            int menor = i;
            for (int j = i + 1; j < arreglo.length; j++)
                if (comparador.compare(arreglo[j],arreglo[menor]) < 0)
                    menor = j;
            intercambia(arreglo,i,menor);
        }
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
        // Aquí va su código.
        if (elemento == null)
            throw new IllegalArgumentException("Elemento no valido");

        if (comparador.compare(arreglo[arreglo.length-1],elemento) == 0)
            return arreglo.length-1;

        if (comparador.compare(arreglo[0],elemento) == 0)
            return 0;

        return busquedaBinaria(arreglo,elemento,0,arreglo.length-1,comparador);
    }

    private static <T> int busquedaBinaria(T[] arreglo, T elemento,int inicio, int fin, Comparator<T> comparador) {
        if (inicio > fin)
            return -1;

        int mitad = (inicio + fin)/2;
        if (comparador.compare(arreglo[mitad],elemento) == 0)
            return mitad;

        if(comparador.compare(arreglo[mitad],elemento) > 0)
            return busquedaBinaria(arreglo,elemento,inicio,mitad-1,comparador);

        return busquedaBinaria(arreglo,elemento,mitad+1,fin,comparador);
    }


    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }

    /**
     * Intercambia lo elementos en dos posiciones de un arreglo
     * @param <T> tipo del que puede ser el arreglo
     * @param arreglo arreglo a intercambiar elementos
     * @param a posición de a donde esta elemento que estará en la posición b
     * @param b posición de a donde esta elemento que estará en la posición a
     */
    private static <T> void intercambia(T[] arreglo, int a, int b) {
        T elementoAuxiliar = arreglo[a];
        arreglo[a] = arreglo[b];
        arreglo[b] = elementoAuxiliar;
    }
}
