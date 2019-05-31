public class iterador {
	public static void main(String[] args) {
		Integer[] i = new Integer[15];
		for (int j = 0; j < 10; j++)
			i[j] = j;
		for (Integer j : i)
			System.out.println(j);
	}
}