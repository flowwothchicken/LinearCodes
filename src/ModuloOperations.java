
public abstract class ModuloOperations {
	
	
	public static int addModulo(int n1, int n2, int mod) {
		int res = n1 + n2;
		return res % mod;
	}
	
	public static int multiplyModulo(int n1, int n2, int mod) {
		int res = n1 * n2;
		return res % mod;
	}
}
