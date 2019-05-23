public class K {
	public static void main(String[] args) {
		int n = Integer.valueOf(args[0]);
		System.out.println("Grafica\n");
		for (int i = 1; i <= n; i++)
			for (int j = i; j <= n; j++)
				if (i == j)
					continue;
				else 
					System.out.print(i+" "+j+" ");
	}
}