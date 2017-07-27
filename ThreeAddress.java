public class ThreeAdress {
	public static void main(String[] args) {
		int i = -1;
		int j = -i - (i - fun(i)) + i - fun(i) + (-1);
		int a = -j + i;
		double b = -(i + j);
		if(!(fun(j) > i)) {
			System.out.println(i + j);
		}
		boolean tmp = i>j;
		if(i>0)
			if(!tmp)
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