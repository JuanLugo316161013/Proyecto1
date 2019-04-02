public class pruebaSplit{
    public static void main(String[] args) {
        String cadena = "1 2 3 4 5 6 7 8 9 10 11 12 13";
        String[] numero = (cadena.split(" "));
        for (String n: numero)
            System.out.println(n);
    }
}
