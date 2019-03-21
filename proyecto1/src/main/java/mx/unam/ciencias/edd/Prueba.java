package mx.unam.ciencias.edd;
public class Prueba {
	public static void main(String[] args) {
		ArbolRojinegro<Integer> arbol = new ArbolRojinegro<Integer>();
		for (int i = 1; i < 8; i++)
			arbol.agrega(i);
		System.out.println(arbol.toString());

		ArbolBinarioOrdenado<Integer> a = new ArbolBinarioOrdenado<>();
		for (int i = 1; i < 8; i++)
			a.agrega(i);

		System.out.println(a.toString());

		a.giraIzquierda(a.raiz());
		System.out.println(a.toString());

		ArbolRojinegro<String> arbol1 = new ArbolRojinegro<String>();
		String[] cadena = new String[29];
		cadena[0] = "Hombres necios que acusáis";
		cadena[1] = "    a la mujer sin razón,";
		cadena[2] = "sin ver que sois la ocasión";
		cadena[3] = "    de lo mismo que culpáis.";
		cadena[4] = "\n";
		cadena[5] = "Si con ansia sin igual";
		cadena[6] = "    solicitáis su desdén,";
		cadena[7] = "¿por qué queréis que obren bien";
		cadena[8] = "    si las incitáis al mal?";
		cadena[9] = "\n";
		cadena[10] = "Combatís su resistencia";
		cadena[11] = "    y luego con gravedad";
		cadena[12] = "decís que fue liviandad";
		cadena[13] = "    lo que hizo la diligencia.";
		cadena[14] = "\n";
		cadena[15] = "Parecer quiere el denuedo";
		cadena[16] = "    de vuestro parecer loco";
		cadena[17] = "al niño que pone el coco";
		cadena[18] = "    y luego le tiene miedo.";
		cadena[19] = "\n";
		cadena[20] = "Queréis con presunción necia";
		cadena[21] = "    hallar a la que buscáis,";
		cadena[22] = "para pretendida, Tais,";
		cadena[23] = "    y en la posesión, Lucrecia.";
		cadena[24] = "\n";
		cadena[25] = "¿Qué humor puede ser más raro";
		cadena[26] = "    que el que, falto de consejo,";
		cadena[27] = "él mismo empaña el espejo";
		cadena[28] = "    y siente que no esté claro?";

		for(int i = 0; i < 3; i++)
			arbol1.agrega(cadena[i]);
		
		arbol1.dfsInOrder(v -> System.out.println(v.get()));

	}
}