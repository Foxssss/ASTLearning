package astCompare.kit;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class TreeToString {
	// transform Java File to String by parsing AST
	public static String getStringByJava(String filePath) {
		byte[] input = null;
		try {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(filePath));
			input = new byte[bufferedInputStream.available()];
			bufferedInputStream.read(input);
			bufferedInputStream.close();
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		ASTParser astParser = ASTParser.newParser(AST.JLS8);
		astParser.setSource(new String(input).toCharArray());
		astParser.setKind(ASTParser.K_COMPILATION_UNIT);

		CompilationUnit compilationUnit = (CompilationUnit) astParser.createAST(null);
		if(compilationUnit == null)
			System.out.println("File IfTest.java is not a standard java file!");
		MarkVisitor markVisitor = new MarkVisitor();
		compilationUnit.accept(markVisitor);
//		System.out.println(markVisitor.seq);
		return markVisitor.seq.toString();
	}
}
