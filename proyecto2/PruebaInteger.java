import java.util.Scanner;

public class PruebaInteger {

	public static void main(String[] args) {
		int n = (int) (Math.random()*100);
		System.out.println("Grafica");

		for (int i = 1; i <= 100; i++)
			for (int j = 1; j <= n; j++)
				if (i == j)
					continue;
				else
					System.out.print(i + " " + j +" ");
		/*System.out.println("MonticuloArreglo");
		for (int i = 1; i < 101;i++ ) {
			System.out.println((int) (Math.random()*100));
		}*/
	}
}
