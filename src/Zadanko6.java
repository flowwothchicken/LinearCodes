import java.util.LinkedList;

public abstract class Zadanko6 {
	public static void solve() {
		int[][] m = {{1, 0, 0, 2, 4}, {0, 1, 0, 1, 0}, {0, 0, 1, 5, 6}};
		LinkedList<Vector> base = VectorOperations.matrixToListOfVectors(m, 7);
		
		LinkedList<Vector> words = new LinkedList<Vector>();
		for(int i = 0; i < 7; i++)
			for(int j = 0; j < 7; j++)
				for(int k = 0; k < 7; k++) {
					Vector v1 = VectorOperations.scaleVectorModulo(i, base.get(0));
					Vector v2 = VectorOperations.scaleVectorModulo(j, base.get(1));
					Vector v3 = VectorOperations.scaleVectorModulo(k, base.get(2));
					
					Vector v = VectorOperations.addVectorsModulo(v1, v2);
					Vector w = VectorOperations.addVectorsModulo(v, v3);
					
					if(VectorOperations.listDoesNotContainVector(words, w))
						words.add(w);
				}
		System.out.println(words.size());
		for(Vector v : words)
			System.out.println(v);
	}
}
