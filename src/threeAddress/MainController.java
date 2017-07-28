package threeAddress;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

public class MainController {
	private static ASTParser astParser;
	private static CompilationUnit compilationUnit;
	private static IDocument doc;
	public static void main(String[] args) {
		byte[] input = null;
		try {
			BufferedInputStream bufferedInputStrream = new BufferedInputStream(new FileInputStream("ThreeAddress.java"));
			input = new byte[bufferedInputStrream.available()];
			bufferedInputStrream.read(input);
			bufferedInputStrream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		astParser = ASTParser.newParser(AST.JLS8);
		astParser.setKind(ASTParser.K_COMPILATION_UNIT);
		astParser.setSource(new String(input).toCharArray());
		
		compilationUnit = (CompilationUnit)astParser.createAST(null);
		doc = new Document(new String(input));
		
		ToBlockVisitor toBlockVisitor = new ToBlockVisitor();
		toBlockVisitor.setAST(compilationUnit);
		compilationUnit.accept(toBlockVisitor);
		acceptRewrite(toBlockVisitor.getRewrite());
		
		ThreeAddressVisitor threeAddressVisitor = new ThreeAddressVisitor();
		threeAddressVisitor.setAST(compilationUnit);
		compilationUnit.accept(threeAddressVisitor);
		acceptRewrite(threeAddressVisitor.getRewrite());
		
	}
	
	public static void acceptRewrite(ASTRewrite rewrite) {
		TextEdit edit = rewrite.rewriteAST(doc, null);
		try {
			edit.apply(doc);
		} catch (MalformedTreeException | BadLocationException e) {
			e.printStackTrace();
		}
		System.out.println(doc.get());		
		astParser.setSource(doc.get().toCharArray());
		compilationUnit = (CompilationUnit)astParser.createAST(null);
	}
}
