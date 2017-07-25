public class IfTest {
	public static void main(String[] args) {
		int i = j = k = x = y = 0;
		if(i >= 0) {
			i --;
			j++;
			k = i + j;
			if(i == j)
				++x;
			if(k == 0) {
				x = k - j;
				y = x;
				x ++;
				i ++;
			}
			else {
				x --;
				y ++;
			}
		}
	}
}