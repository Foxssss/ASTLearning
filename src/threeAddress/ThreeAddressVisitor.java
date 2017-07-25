package threeAddress;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
//import org.eclipse.jface.text.BadLocationException;
//import org.eclipse.jface.text.Document;
//import org.eclipse.jface.text.IDocument;
//import org.eclipse.text.edits.MalformedTreeException;
//import org.eclipse.text.edits.TextEdit;

public class ThreeAddressVisitor extends ASTVisitor {
//	private CompilationUnit unit;
	private AST ast;
	private ASTRewrite rewrite;
	
	@Override
	public boolean visit(IfStatement node) {
		Statement thenStmt = node.getThenStatement();
		Block block;
		if(thenStmt instanceof Block) {
			block = (Block)thenStmt;
		}
		else {
			block = ast.newBlock();
			ListRewrite thenRewrite = rewrite.getListRewrite(block, Block.STATEMENTS_PROPERTY);
			thenRewrite.insertAt(thenStmt, 0, null);
			rewrite.replace(thenStmt, block, null);
		}
		
//		IDocument doc = new Document(unit.toString());
//		TextEdit edit = rewrite.rewriteAST(doc, null);
//		try {
//			edit.apply(doc);
//		System.out.println(doc.get());
//		} catch (MalformedTreeException | BadLocationException e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		}		
		
		return true;
	}
//	@Override
//	public void endVisit(IfStatement node) {
//		ASTNode parent = node.getParent();
//		if(parent instanceof Block) {
//			
//		}
//		else
//			try {
//				throw new Exception("The process of transferring to block is not complete!");
//			} catch (Exception e) {
//				// TODO 自动生成的 catch 块
//				e.printStackTrace();
//			}
//	}
	
	public void setAST(CompilationUnit unit) {
		ast = unit.getAST();
//		this.unit = unit;
		rewrite = ASTRewrite.create(ast);
	}
	public AST getAST() {
		return ast;
	}
	public ASTRewrite getRewrite() {
		return rewrite;
	}
}
