package threeAddress;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;

public class ToBlockVisitor extends ASTVisitor {
	private AST ast;
	private ASTRewrite rewrite;
	@Override
	public boolean visit(IfStatement node) {
		Statement thenStmt = node.getThenStatement();
		if(!(thenStmt instanceof Block)) {
			Block block = ast.newBlock();
			ListRewrite thenRewrite = rewrite.getListRewrite(block, Block.STATEMENTS_PROPERTY);
			thenRewrite.insertAt(thenStmt, 0, null);
			rewrite.replace(thenStmt, block, null);
		}
		Statement elseStmt = node.getElseStatement();
		if(elseStmt != null && !(elseStmt instanceof Block)) {
			Block block = ast.newBlock();
			ListRewrite elseRewrite = rewrite.getListRewrite(block, Block.STATEMENTS_PROPERTY);
			elseRewrite.insertAt(elseStmt, 0, null);
			rewrite.replace(elseStmt, block, null);
		}
		return true;
	}
	
	@Override
	public boolean visit(WhileStatement node) {
		Statement body = node.getBody();
		if(!(body instanceof Block)) {
			Block block = ast.newBlock();
			ListRewrite bodyRewrite = rewrite.getListRewrite(block, Block.STATEMENTS_PROPERTY);
			bodyRewrite.insertAt(body, 0, null);
			rewrite.replace(body, block, null);
		}
		return true;
	}

	@Override
	public boolean visit(ForStatement node) {
		Statement body = node.getBody();
		if(!(body instanceof Block)) {
			Block block = ast.newBlock();
			ListRewrite bodyRewrite = rewrite.getListRewrite(block, Block.STATEMENTS_PROPERTY);
			bodyRewrite.insertAt(body, 0, null);
			rewrite.replace(body, block, null);
		}
		return true;
	}

	public void setAST(CompilationUnit unit) {
		ast = unit.getAST();
		rewrite = ASTRewrite.create(ast);
	}
	public AST getAST() {
		return ast;
	}
	public ASTRewrite getRewrite() {
		return rewrite;
	}
}
