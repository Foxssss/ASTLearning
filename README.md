## ASTLearning
有5个包：

test - 用来熟悉AST框架的，没什么用

methodCount - 可以统计一个java文件中有多少main方法、抽象方法以及构造方法

stmtCount - 统计一个if-then语句的then语句块里面有多少条语句

toBlock - 将if-then语句中非block的语句块转为block，为下面的包功能做铺垫

threeAddress - 这次学习的主要完成目标，将变量声明赋值的语句转为3地址码类型的语句，有一些原本打算完成但结果未完成的目标（如，将if、while中的条件也改为3地址码类型，由于涉及到符号表的建立就没有实现，但还是可以实现的）。


## 示例：
原类：

```
public class ThreeAdress {
	public static void main(String[] args) {
		int i = -1, a = (1-i) * i * i + 1;
		int j = -i - (i - fun(i)) + i - fun(i) + (-1);
		double b = -(i + j);
		if(!(fun(j) > i))
			System.out.println(i + j);
		boolean tmp = i>j;
		if(i>0)
			if (!tmp)
				if (i < 1)
					j = 0;
		while (i >j)
			i--;
		for(int k = 0; k < 2; k++)
			System.out.println(k);
	}
	public static int fun(int x) {
		return x;
	}
}
```


经过threeAddress.java处理后：
（变量的定义转为3地址码类型，在每个if语句前加了一个空语句，原本是打算在if语句前加上一些中间变量的定义的，并没有实现因此就放弃了）

```
public class ThreeAdress {
	public static void main(String[] args) {
		int i = -1;
		int tmpVar_1 = 1 - i;
		int tmpVar_2 = tmpVar_1 * i;
		int tmpVar_0 = tmpVar_2 * i;
		int a = tmpVar_0 + 1;
		int tmpVar_6 = -i;
		int tmpVar_8 = fun(i);
		int tmpVar_7 = i - tmpVar_8;
		int tmpVar_5 = tmpVar_6 - tmpVar_7;
		int tmpVar_4 = tmpVar_5 + i;
		int tmpVar_9 = fun(i);
		int tmpVar_3 = tmpVar_4 - tmpVar_9;
		int tmpVar_10 = -1;
		int j = tmpVar_3 + tmpVar_10;
		double tmpVar_11 = i + j;
		double b = -tmpVar_11;
		;
		if(!(fun(j) > i)) {
			System.out.println(i + j);
		}
		boolean tmp = i > j;
		;
		if(i>0) {
			;
			if (!tmp) {
				;
				if (i < 1) {
					j = 0;
				}
			}
		}
		while (i >j) {
			i--;
		}
		for(int k = 0; k < 2; k++) {
			System.out.println(k);
		}
	}
	public static int fun(int x) {
		return x;
	}
}
```
