import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public abstract class VectorOperations{

	public static Vector addVectorsModulo(Vector vector1, Vector vector2) {
		if(vector1.mod != vector2.mod || vector1.dim != vector2.dim)
			throw new RuntimeException("improper vectors");
		
		int[] cords = new int[vector1.dim];
		for(int i = 0; i < vector1.dim; i++)
			cords[i] = ModuloOperations.addModulo(vector1.cords[i], vector2.cords[i], vector1.mod);
		return new Vector(vector1.mod, cords);
	}
	
	public static Vector scaleVectorModulo(int scale, Vector vector) {
		int[] cords = new int[vector.dim];
		for(int i = 0; i < vector.dim; i++)
			cords[i] = ModuloOperations.multiplyModulo(scale, vector.cords[i], vector.mod);
		return new Vector(vector.mod, cords);
	}
	
	public static int hammingDistance(Vector vector1, Vector vector2) {
		if(vector1.mod != vector2.mod || vector1.dim != vector2.dim)
			throw new RuntimeException("improper vectors");
		
		int distance = 0;
		for(int i = 0; i < vector1.dim; i++)
			if(vector1.cords[i] != vector2.cords[i])
				distance++;
		
		return distance;
	}
	
	public static LinkedList<Vector> matrixToListOfVectors(int[][] m, int mod) {
		checkMatrixModAndDim(m, mod);
		
		LinkedList<Vector> listOfVectors = new LinkedList<>();
		for(int[] vector : m)
			listOfVectors.add(new Vector(mod, vector));
		
		return listOfVectors;
	}
	
	public static boolean listDoesNotContainVector(LinkedList<Vector> list, Vector v) {
		for(Vector e : list)
		{
			boolean vectorsAreEqual = true;
			for(int i = 0; i < v.dim; i++)
				if(e.cords[i] != v.cords[i]) {
					vectorsAreEqual = false;
					break;
				}
			if(vectorsAreEqual)
				return false;
		}
		return true;
	}
	
	
	public static int[][] baseToMatrix(LinkedList<Vector> base){
		int[][] g = new int[base.size()][base.get(0).dim];
		
		for(int i = 0; i < base.size(); i++)
			for(int j = 0; j < base.get(0).dim; j++)
				g[i][j] = base.get(i).cords[j];
		
		return g;
	}
	
	public static LinkedList<Vector> encryptListOfVectors(LinkedList<Vector> vectors, int[][] G){
		LinkedList<Vector> res = new LinkedList<>();
		for(Vector v : vectors)
			res.add(encryptVector(v, G));
		
		return res;
	}
	
	public static int[][] transpose(int[][] m){
		int [][] t = new int[m[0].length][m.length];
		for(int i = 0; i < m.length; i++)
			for(int j = 0; j < m[0].length; j++)
				t[j][i] = m[i][j];
		return t;
	}
	
	private static Vector encryptVector(Vector v, int[][] G) {
		int[] cords = new int[G[0].length];
		for(int i = 0; i < G[0].length; i++) {
			int value = 0;
			for(int j = 0; j < v.dim; j++)
				value = ModuloOperations.addModulo(value, ModuloOperations.multiplyModulo(v.cords[j], G[j][i], v.mod), v.mod);
			cords[i] = value;
		}
		
		return new Vector(v.mod, cords);
	}
	
	private static void checkMatrixModAndDim(int[][] m, int mod){
		for(int[] row : m)
			for(int k : row)
				if(k >= mod)
					throw new RuntimeException("invalid values");
		
		Set<Integer> dims = new HashSet<Integer>();
		for(int[] row : m)
			dims.add(row.length);
		if(dims.size() != 1)
			throw new RuntimeException("improper dimensions");
	}
}
