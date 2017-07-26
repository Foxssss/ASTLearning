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

public class ThreeAddressVisitor extends ASTVisitor {
	private AST ast;
	private ASTRewrite rewrite;
	
	@Override
	public boolean visit(IfStatement node) {
		ASTNode parent = node.getParent();
		if(parent instanceof Block) {
			Block block = (Block)parent;
			ListRewrite ifRewrite = rewrite.getListRewrite(block, Block.STATEMENTS_PROPERTY);
			Statement emptyStmt = ast.newEmptyStatement();
			ifRewrite.insertBefore(emptyStmt, node, null);
			System.out.println();
		}
		else
			try {
				throw new Exception("The process of transferring to block is not complete!");
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
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
