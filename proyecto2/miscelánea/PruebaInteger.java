public class PruebaInteger {
	
	public static String flecha(int x, int y) {
		String puntos = String.format("%d,%d %d,%d %d,%d %d,%d %d,%d %d,%d %d,%d %d,%d %d,%d %d,%d ",
		 x+5, y+15, x+15, y+7, x+15, y+13, x+35, y+13, x+35, y+7, x+45, y+15, x+35, y+23, x+35, y+17, x+15, y+17, x+15, y+23);
		return "<polygon points = ' " + puntos + " ' fill = 'black' />" ;
	}

	public static String elemento(int x, int y, int elemento) {
		return  String.format("<text fill='black' font-family='sans-serif' font-size='20' x='%d' y='%d' text-anchor='middle'>%d</text>",
			x+25, (y+30)-7, elemento);
	}

	public static void main(String[] args) {
		System.out.println(flecha(100,80));
		System.out.println(elemento(50,50,300));
	}
}