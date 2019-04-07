public class pruebaInteger {
	public static void main(String[] args) {
		Integer[] numeros = new Integer[10];
		Integer[] enteros = new Integer[10];

		for (int i = 0; i < 10; i++) {
			numeros[i] = new Integer(i);
			enteros[i] = new Integer(i);
		}

		for (int i = 0; i < 10; i++)
			if (enteros[i].equals(numeros[i]))
				System.out.println(i);
	}
}