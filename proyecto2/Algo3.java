public class Algo3 extends Algo2 {

	@Override public int numero() {return super.super.numero();}

	public static void main(String[] args) {
		Algo3 algo = new Algo3();
		System.out.println(algo.numero());
	}
}