package toBlock;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Statement;

public class ThenBlockVisitor extends ASTVisitor {
	@Override
	public boolean visit(IfStatement node) {
		Statement thenStmt = node.getThenStatement();
		if(!(thenStmt instanceof Block)) {
			AST ast = node.getAST();
			Block block = ast.newBlock();
			node.setThenStatement(block);
			block.statements().add(thenStmt);
		}
		return true;
	}
}
