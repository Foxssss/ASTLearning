package astCompare;

import java.io.IOException;

public class ZhangMain {
	public static int compair(String str1, String str2) throws IOException {
		Tree tree1 = new Tree(str1);
		Tree tree2 = new Tree(str2);
		return Tree.ZhangShasha(tree1, tree2);
	}
	public static void main(String[] args) throws IOException {
		// Sample trees (in preorder).
		String tree1Str1 = " xf ( x1a  x1a  x1a  x1a  x1a  x1a  x1a  x1a  x1a x37 ( x1f ( x15 ) x1f ( x3c  x19 ( x29 ) x29 ) x1f ( x15 ) x1f ( x19 ( x29 ) x15  x15 ) x1f ( x3c  x46 ( x15 ) x29 ) x1f ( x3c  x46 ( x19 ( x15 )) x19 ( x29 ) x29 ) x1f ( x3c  x46 ( x19 ( x15 )) x15 ) x1f ( x3c  x46 ( x19 ( x3c  x19 ( x15 ) x15 )) x15 ) x1f ( x18 ( x3c  x19 ( x19 ( x29 ))) x29 ) x37 ( x1f ( x15  x15 ) x1f ( x3c  x15  x29 ))))";
		String tree1Str2 = "  xf ( x1a  x1a  x1a  x1a  x1a  x1a  x1a  x1a x37 ( x1f ( x15 ) x1f ( x3c  x19 ( x29 ) x29 ) x1f ( x15 ) x1f ( x19 ( x29 ) x15  x15 ) x1f ( x3c  x46 ( x15 ) x29 ) x1f ( x3c  x46 ( x19 ( x15 )) x19 ( x29 ) x29 ) x1f ( x3c  x46 ( x19 ( x15 )) x15 ) x1f ( x3c  x46 ( x19 ( x3c  x19 ( x15 ) x15 )) x15 ) x1f ( x18 ( x3c  x19 ( x19 ( x29 ))) x29 ) x37 ( x1f ( x15  x15 ) x1f ( x3c  x15  x29 )) x1f ( x15 )))";
		// Distance: 2 (main example used in the Zhang-Shasha paper)

		String tree1Str3 = "a(b(c d) e(f g(i)))";
		String tree1Str4 = "a(b(c d) e(f g(h)))";
		// Distance: 1

		String tree1Str5 = "d";
		String tree1Str6 = "g(h)";
		// Distance: 2

		Tree tree1 = new Tree(tree1Str1);
		Tree tree2 = new Tree(tree1Str2);

		Tree tree3 = new Tree(tree1Str3);
		Tree tree4 = new Tree(tree1Str4);

		Tree tree5 = new Tree(tree1Str5);
		Tree tree6 = new Tree(tree1Str6);

		int distance1 = Tree.ZhangShasha(tree1, tree2);
		System.out.println("Difference:  " + distance1);

		int distance2 = Tree.ZhangShasha(tree3, tree4);
		System.out.println("Expected 1; got " + distance2);

		int distance3 = Tree.ZhangShasha(tree5, tree6);
		System.out.println("Expected 2; got " + distance3);
	}

}
