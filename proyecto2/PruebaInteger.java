import java.util.Scanner;

public class PruebaInteger {

	public static void main(String[] args) {
		int n = (int) (Math.random()*100);
		System.out.println("Grafica");
		
		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= n; j++)
				if (i == j)
					continue;
				else
					System.out.print(i + " " + j +" ");
	}
}