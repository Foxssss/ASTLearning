package astCompare;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class MarkVisitor extends ASTVisitor {
	public StringBuffer seq = new StringBuffer();
	
//	@Override
//	public boolean visit(TypeDeclaration node) {
//		seq.append("y");
//		return true;
//	}

	@Override
	public boolean visit(MethodDeclaration node) {
		seq.append("m");
		return true;
	}
	
	@Override
	public boolean visit(Block node) {
		seq.append("{");
		return true;
	}
	
	@Override
	public void endVisit(Block node) {
		seq.append("}");
	}
	
	@Override
	public boolean visit(BreakStatement node) {
		seq.append("b");
		return false;
	}
	
	@Override
	public boolean visit(ConstructorInvocation node) {
		seq.append("c");
		return false;
	}
	
	@Override
	public boolean visit(ContinueStatement node) {
		seq.append("C");
		return false;
	}
	
	@Override
	public boolean visit(DoStatement node) {
		seq.append("d");
		return false;
	}
	
	@Override
	public boolean visit(EnhancedForStatement node) {
		seq.append("e");
		return true;
	}
	
	@Override
	public boolean visit(ExpressionStatement node) {
		seq.append("E");
		return true;
	}
	
	@Override
	public boolean visit(ForStatement node) {
		seq.append("f");
		return true;
	}
	
	@Override
	public boolean visit(IfStatement node) {
		seq.append("i");
		return true;
	}
	
	@Override
	public boolean visit(LabeledStatement node) {
		seq.append("l");
		return false;
	}
	
	@Override
	public boolean visit(ReturnStatement node) {
		seq.append("r");
		return false;
	}
	
	@Override
	public boolean visit(SuperConstructorInvocation node) {
		seq.append("s");
		return false;
	}
	
	@Override
	public boolean visit(SwitchCase node) {
		seq.append("D"); 	//		case:...; default:...;
		return true;
	}
	
	@Override
	public boolean visit(SwitchStatement node) {
		seq.append("S");
		return true;
	}
	
	@Override
	public boolean visit(ThrowStatement node) {
		seq.append("O");
		return false;
	}
	
	@Override
	public boolean visit(TryStatement node) {
		seq.append("t");
		return false;
	}
	
	@Override
	public boolean visit(TypeDeclarationStatement node) {
		seq.append("T");
		return false;
	}
	
	@Override
	public boolean visit(VariableDeclarationStatement node) {
		seq.append("v");
		return false;
	}
	
	@Override
	public boolean visit(WhileStatement node) {
		seq.append("w");
		return true;
	}

}
