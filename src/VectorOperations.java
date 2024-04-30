import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public abstract class VectorOperations{

	public static Vector addVectorsModulo(Vector vector1, Vector vector2) throws RuntimeException {
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
	
	public static int HammingDistance(Vector vector1, Vector vector2) throws RuntimeException{
		if(vector1.mod != vector2.mod || vector1.dim != vector2.dim)
			throw new RuntimeException("improper vectors");
		
		int distance = 0;
		for(int i = 0; i < vector1.dim; i++)
			if(vector1.cords[i] != vector2.cords[i])
				distance++;
		
		return distance;
	}
	
	public static LinkedList<Vector> matrixToListOfVectors(int[][] m, int mod) throws RuntimeException{
		checkMatrixModAndDim(m, mod);
		
		LinkedList<Vector> listOfVectors = new LinkedList<>();
		for(int[] vector : m)
			listOfVectors.add(new Vector(mod, vector));
		
		return listOfVectors;
	}
	
	private static void checkMatrixModAndDim(int[][] m, int mod) throws RuntimeException{
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
