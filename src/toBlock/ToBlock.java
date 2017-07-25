package toBlock;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ASTParser;

public class ToBlock {
	public static void main(String[] args) {
		byte[] input = null;
		try {
			BufferedInputStream bufferedInpubStream = new BufferedInputStream(new FileInputStream("ThenTransfer.java"));
			input = new byte[bufferedInpubStream.available()];
			bufferedInpubStream.read(input);
			bufferedInpubStream.close();
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
//		CompilationUnit diff = (CompilationUnit)ASTNode.copySubtree(compilationUnit.getAST(), compilationUnit);
		ThenBlockVisitor tbVisitor = new ThenBlockVisitor();
		compilationUnit.accept(tbVisitor);
		
		System.out.print(compilationUnit.toString());
//		System.out.print(diff);
	}
}
