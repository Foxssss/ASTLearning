public class ThreeAdress {
	public static void main(String[] args) {
		int i = -1, a = (1-i) * i * i + 1;
		int j = -i - (i - fun(i)) + i - fun(i) + (-1);
		double b = -(i + j);
		if(!(j > i))
			System.out.println(i + j);
		if(i>0)
			if(!(i>j))
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