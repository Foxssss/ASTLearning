package threeAddress;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

public class MainController {
	public static void main(String[] args) {
		byte[] input = null;
		try {
			BufferedInputStream bufferedInputStrream = new BufferedInputStream(new FileInputStream("ThreeAddress.java"));
			input = new byte[bufferedInputStrream.available()];
			bufferedInputStrream.read(input);
			bufferedInputStrream.close();
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		ASTParser astParser = ASTParser.newParser(AST.JLS8);
		astParser.setKind(ASTParser.K_COMPILATION_UNIT);
		astParser.setSource(new String(input).toCharArray());
		
		CompilationUnit compilationUnit = (CompilationUnit)astParser.createAST(null);
		ThreeAddressVisitor visitor = new ThreeAddressVisitor();
		visitor.setAST(compilationUnit);
		compilationUnit.accept(visitor);
		System.out.print(compilationUnit.toString());
		
		IDocument doc = new Document(new String(input));
		TextEdit edit = visitor.getRewrite().rewriteAST(doc, null);
		try {
			edit.apply(doc);
		} catch (MalformedTreeException | BadLocationException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		System.out.println(doc.get());
	}
}
