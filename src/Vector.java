
public class Vector {
	public int dim;
	public int mod;
	public int[] cords;
	
	
	public Vector(int mod, int[] cords) throws RuntimeException {
		this.dim = cords.length;
		this.mod = mod;
		this.cords = cords;
		for(int i : cords) {
			if(i >= mod)
				throw new RuntimeException("imptoper arguments");
		}
	}
	
	@Override
	public String toString()
	{
		String res = "⎛" + cords[0] + "⎞\n";
		for(int i = 1; i < dim - 1; i++)
			res += "⎜" + cords[i] + "⎜\n";
		res += "⎝" + cords[dim - 1] + "⎠\n";
		return res;
	}
}
