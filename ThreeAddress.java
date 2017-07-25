public class ThreeAdress {
	public static void main(String[] args) {
		int i = 0;
		int j = i + fun(i);
		if(!(fun(j) > i)) {
			System.out.println(i + j);
		}
		if(i>0)
			if(j>0)
				if(i<1)
					j  = 0;
	}
	public static int fun(int x) {
		return x;
	}
}