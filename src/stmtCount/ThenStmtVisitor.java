package stmtCount;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Block;

public class ThenStmtVisitor extends ASTVisitor {
	@Override
	public boolean visit(IfStatement node) {
		Block block = null;

		Statement thenStmt = node.getThenStatement();
		if(thenStmt instanceof Block) {
			block = (Block)thenStmt;
			System.out.println(block.statements().size());
		}
		else if(thenStmt instanceof ExpressionStatement)
			System.out.println(1);
		else
			System.out.println(-1);
		return true;
	}
}
