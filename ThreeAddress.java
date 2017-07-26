public class ThreeAdress {
	public static void main(String[] args) {
		int i = 0;
		int j = i + fun(i);
		if(!(fun(j) > i)) {
			System.out.println(i + j);
		}

			i = 0;
		if(i>0)
			if(j>0)
				if(i<1)
					j  = 0;
		while (i >j)
			i--;
		for(int k = 0; k < 2; k++)
			System.out.println(k);
	}
	public static int fun(int x) {
		return x;
	}
}