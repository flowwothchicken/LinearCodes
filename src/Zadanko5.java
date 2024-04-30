import java.util.LinkedList;

public abstract class Zadanko5 {
	
	public static void solve() {
//		Vector v = new Vector(3, new int[] {1, 2, 0, 1});
//		Vector w = new Vector(3, new int[] {0, 0, 0, 1});
//		
//		System.out.println(VectorOperations.HammingDistance(v, w));
		
		int[][] m = {{1, 2, 1, 2, 0}, {1, 1, 1, 1, 1}, {0, 0, 2, 1, 1}, {2, 2, 2, 1, 0}};
		LinkedList<Vector> vectors = VectorOperations.matrixToListOfVectors(m, 3);
		
		int firstId = 0;
		int secondId = 0;
		int minDistance = 6;
		for(int i = 0; i < vectors.size(); i++)
			for(int j = i+1; j < vectors.size(); j++) {
				Vector u = vectors.get(i);
				Vector e = vectors.get(j);
				if(VectorOperations.HammingDistance(u, e) < minDistance) {
					minDistance = VectorOperations.HammingDistance(u, e);
					firstId = i;
					secondId = j;
				}
			}
		
		System.out.println("wektory\n" + vectors.get(firstId) + "oraz\n" + vectors.get(secondId) + "znajduja sie na odleglosci " + minDistance + " w sensie Hamminga");
	}
}