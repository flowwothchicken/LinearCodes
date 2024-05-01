import java.util.LinkedList;
import java.util.Random;

public abstract class Zadanko7 {
	public static void solve() {
		int[][] m = {{1, 0, 0, 2, 4}, {0, 1, 0, 1, 0}, {0, 0, 1, 5, 6}};
		LinkedList<Vector> base = VectorOperations.matrixToListOfVectors(m, 7);
		int[][] G = VectorOperations.baseToMatrix(base);
		
		System.out.println("macierz generujaca kodu C:");
		for(int[] i : G) {
			for(int j : i)
				System.out.print(j + " ");
			System.out.println("");
		}
		
		
		Vector v = new Vector(7, new int[] {1, 2, 3, 4, 5});
		Vector r = minimizeHammingDistance(base, v);
		System.out.println("po dekodowaniu wektora " + v + " otrzymalismy wektor " + r);
	}
	
	private static Vector minimizeHammingDistance(LinkedList<Vector> base, Vector v) {
		LinkedList<Vector> L = new LinkedList<Vector>();
		int minDistance = 7;
		
		for(int i = 0; i < 7; i++)
			for(int j = 0; j < 7; j++)
				for(int k = 0; k < 7; k++) {
					Vector v1 = VectorOperations.scaleVectorModulo(i, base.get(0));
					Vector v2 = VectorOperations.scaleVectorModulo(j, base.get(1));
					Vector v3 = VectorOperations.scaleVectorModulo(k, base.get(2));
					
					Vector u = VectorOperations.addVectorsModulo(v1, v2);
					Vector w = VectorOperations.addVectorsModulo(u, v3);
					
					if(VectorOperations.hammingDistance(v, w) < minDistance) {
						L.clear();
						L.add(w);
						minDistance = VectorOperations.hammingDistance(v, w);
					}
					else if(VectorOperations.hammingDistance(v, w) == minDistance)
						L.add(w);
				}
		
		Random random = new Random();
		int id = random.nextInt(L.size());
		Vector e = L.get(id);
		
		int[] cords = {0, 0, 0};
		
		for(int i = 0; i < 7; i++)
			for(int j = 0; j < 7; j++)
				for(int k = 0; k < 7; k++) {
					Vector v1 = VectorOperations.scaleVectorModulo(i, base.get(0));
					Vector v2 = VectorOperations.scaleVectorModulo(j, base.get(1));
					Vector v3 = VectorOperations.scaleVectorModulo(k, base.get(2));
					
					Vector u = VectorOperations.addVectorsModulo(v1, v2);
					Vector w = VectorOperations.addVectorsModulo(u, v3);
					
					if(VectorOperations.hammingDistance(w, e) == 0) {
						cords[0] = i;
						cords[1] = j;
						cords[2] = k;
					}
				}
		
		return new Vector(7, cords);
	}

}
