import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;

public abstract class Zadanko8 {
	
	public static void solve(){
		int[][] A = new int[4][10];
		Random random = new Random();
		
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 10; j++)
				A[i][j] = random.nextInt(5);
		
		double[][] B = normalize(A);
	
		image(B, "image.png");
		
		LinkedList<Vector> vectors = VectorOperations.matrixToListOfVectors(VectorOperations.transpose(A), 5);
		int[][] G = {{1, 0, 0, 0, 0, 4, 4, 2, 0, 1, 1}, 
				    {0, 1, 0, 0, 0, 3, 0, 2, 2, 1, 0},
				    {0, 0, 1, 0, 0, 2, 0, 1, 1, 1, 1}, 
				    {0, 0, 0, 1, 1, 0, 0, 0, 4, 3, 0}};
		LinkedList<Vector> encryptedVectors = VectorOperations.encryptListOfVectors(vectors, G);
		LinkedList<Vector> base = VectorOperations.matrixToListOfVectors(G, 5);
		encryptedVectors = addNoise(encryptedVectors);
		LinkedList<Vector> decryptedVectors = new LinkedList<>();
		
		for(Vector v : encryptedVectors)
			decryptedVectors.add(minimizeHammingDistance(base, v));
		
		for(int i = 0; i < vectors.size(); i++)
			System.out.println(VectorOperations.hammingDistance(vectors.get(i), decryptedVectors.get(i)));
		
		int [][] C = VectorOperations.baseToMatrix(decryptedVectors);
		double D[][] = normalize(VectorOperations.transpose(C));
		image(D, "imageAfterTransmission.png");
	}

	private static double[][] normalize(int[][] A){
		double[][] B = new double[A.length][A[0].length];
		for(int i = 0; i < A.length; i++)
			for(int j = 0; j < A[0].length; j++)
				B[i][j] = A[i][j] * 1.0 / 4;
		
		return B;
	}
	
	private static void image(double[][]  m, String path) {
		BufferedImage image = new BufferedImage(m[0].length * 100, m.length * 100, BufferedImage.TYPE_INT_RGB);
		
		for(int x = 0; x < m[0].length; x++)
			for(int y = 0; y < m.length; y++) {
				Color color = new Color((int)(m[y][x] * 255), (int)(m[y][x] * 255),(int)(m[y][x] * 255));
				for(int i = 0; i < 100; i++)
					for(int j = 0; j < 100; j++)
						image.setRGB(x*100 + i, y*100 + j, color.getRGB());
			}
		
		try {
			ImageIO.write(image, "png", new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static LinkedList<Vector> addNoise(LinkedList<Vector> vectors){
		LinkedList<Vector> res = new LinkedList<>();
		for(Vector v : vectors) {
			int[] cords = new int[v.dim];
			for(int i = 0; i < v.dim; i++) {
				Random random = new Random();
				int chance = random.nextInt(20);
				cords[i] = v.cords[i];
				if(chance == 1)
					cords[i] = ModuloOperations.addModulo(cords[i], 3, v.mod);
			}
			res.add(new Vector(v.mod, cords));
		}
		return res;
	}
	
	private static Vector minimizeHammingDistance(LinkedList<Vector> base, Vector v) {
		LinkedList<Vector> L = new LinkedList<Vector>();
		int minDistance = 12;
		
		for(int a = 0; a < 5; a++)
			for(int b = 0; b < 5; b++)
				for(int c = 0; c < 5; c++)
					for(int d = 0; d < 5; d++) {
						Vector v1 = VectorOperations.scaleVectorModulo(a, base.get(0));
						Vector v2 = VectorOperations.scaleVectorModulo(b, base.get(1));
						Vector v3 = VectorOperations.scaleVectorModulo(c, base.get(2));
						Vector v4 = VectorOperations.scaleVectorModulo(d, base.get(3));
						
						Vector u = VectorOperations.addVectorsModulo(v1, v2);
						u = VectorOperations.addVectorsModulo(u, v3);
						u = VectorOperations.addVectorsModulo(u, v4);
						
						if(VectorOperations.hammingDistance(v, u) < minDistance) {
							L.clear();
							L.add(u);
							minDistance = VectorOperations.hammingDistance(v, u);
						}
						else if(VectorOperations.hammingDistance(v, u) == minDistance)
							L.add(u);
					}
		
		Random random = new Random();
		int id = random.nextInt(L.size());
		Vector e = L.get(id);
		
		int[] cords = {0, 0, 0, 0};
		
		for(int a = 0; a < 5; a++)
			for(int b = 0; b < 5; b++)
				for(int c = 0; c < 5; c++)
					for(int d = 0; d < 5; d++) {
						Vector v1 = VectorOperations.scaleVectorModulo(a, base.get(0));
						Vector v2 = VectorOperations.scaleVectorModulo(b, base.get(1));
						Vector v3 = VectorOperations.scaleVectorModulo(c, base.get(2));
						Vector v4 = VectorOperations.scaleVectorModulo(d, base.get(3));
						
						Vector u = VectorOperations.addVectorsModulo(v1, v2);
						u = VectorOperations.addVectorsModulo(u, v3);
						u = VectorOperations.addVectorsModulo(u, v4);
						
						if(VectorOperations.hammingDistance(u, e) == 0) {
							cords[0] = a;
							cords[1] = b;
							cords[2] = c;
							cords[3] = d;
						}
					}
		
		return new Vector(5, cords);
	}
}