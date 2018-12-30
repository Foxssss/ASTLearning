package astCompare.kit;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class MarkVisitor extends ASTVisitor {
	public StringBuffer seq = new StringBuffer();
		
	private void addMark_old(int type) {
		seq.append(" x").append(Integer.toHexString(type)).append(" (");
	}
	
	private void addMark(int type) {
		seq.append((char)(type));
	}
	
	@Override 
	public boolean visit(CompilationUnit node) {
		addMark(node.getNodeType());
		return true;
	}
	@Override
	public boolean visit(TypeDeclaration node) {
		addMark(node.getNodeType());
		return true;
	}
/*	@Override
	public boolean visit(Modifier node) {
		addMark(node.getNodeType());
		return true;
	}
	@Override
	public boolean visit(SimpleName node) {
		addMark(node.getNodeType());
		return true;
	}
	@Override
	public boolean visit(FieldDeclaration node) {
		addMark(node.getNodeType());
		return true;
	}
	@Override
	public boolean visit(ClassInstanceCreation node) {
		addMark(node.getNodeType());
		return true;
	}
	@Override
	public boolean visit(SingleVariableDeclaration node) {
		addMark(node.getNodeType());
		return true;
	}
	@Override
	public boolean visit(MethodInvocation node) {
		addMark(node.getNodeType());
		return true;
	}
	@Override
	public boolean visit(Assignment node) {
		addMark(node.getNodeType());
		return true;
	}
	@Override
	public boolean visit(VariableDeclarationExpression node) {
		addMark(node.getNodeType());
		return true;
	}
	@Override
	public boolean visit(CastExpression node) {
		addMark(node.getNodeType());
		return true;
	}
	@Override
	public boolean visit(ArrayCreation node) {
		addMark(node.getNodeType());
		return true;
	}
	@Override
	public boolean visit(ArrayInitializer node) {
		addMark(node.getNodeType());
		return true;
	}
	@Override
	public boolean visit(ArrayAccess node) {
		addMark(node.getNodeType());
		return true;
	}
	@Override
	public boolean visit(FieldAccess node) {
		addMark(node.getNodeType());
		return true;
	}
	@Override
	public boolean visit(PrefixExpression node) {
		addMark(node.getNodeType());
		return true;
	}
	@Override
	public boolean visit(InfixExpression node) {
		addMark(node.getNodeType());
		return true;
	}
	@Override
	public boolean visit(PostfixExpression node) {
		addMark(node.getNodeType());
		return true;
	}
	@Override
	public boolean visit(NullLiteral node) {
		addMark(node.getNodeType());
		return true;
	}
	@Override
	public boolean visit(NumberLiteral node) {
		addMark(node.getNodeType());
		return true;
	}
	@Override
	public boolean visit(StringLiteral node) {
		addMark(node.getNodeType());
		return true;
	}
	@Override
	public boolean visit(BooleanLiteral node) {
		addMark(node.getNodeType());
		return true;
	}*/

	@Override
	public boolean visit(ImportDeclaration node) {
		addMark(node.getNodeType());
		return true;
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		addMark(node.getNodeType());
		return true;
	}
	
	@Override
	public boolean visit(BreakStatement node) {
		addMark(node.getNodeType());
		return true;
	}
	
	@Override
	public boolean visit(ConstructorInvocation node) {
		addMark(node.getNodeType());
		return true;
	}
	
	@Override
	public boolean visit(ContinueStatement node) {
		addMark(node.getNodeType());
		return true;
	}
	
	@Override
	public boolean visit(DoStatement node) {
		addMark(node.getNodeType());
		return true;
	}
	
	@Override
	public boolean visit(EnhancedForStatement node) {
		addMark(node.getNodeType());
		return true;
	}
	
	@Override
	public boolean visit(ExpressionStatement node) {
		addMark(node.getNodeType());
		return true;
	}
	
	@Override
	public boolean visit(ForStatement node) {
		addMark(node.getNodeType());
		return true;
	}
	
	@Override
	public boolean visit(IfStatement node) {
		addMark(node.getNodeType());
		return true;
	}
	
	@Override
	public boolean visit(LabeledStatement node) {
		addMark(node.getNodeType());
		return true;
	}
	
	@Override
	public boolean visit(ReturnStatement node) {
		addMark(node.getNodeType());
		return true;
	}
	
	@Override
	public boolean visit(SuperConstructorInvocation node) {
		addMark(node.getNodeType());
		return true;
	}
	
	@Override
	public boolean visit(SwitchCase node) {
		addMark(node.getNodeType()); 	//		case:...; default:...;
		return true;
	}
	
	@Override
	public boolean visit(SwitchStatement node) {
		addMark(node.getNodeType());
		return true;
	}
	
	@Override
	public boolean visit(ThrowStatement node) {
		addMark(node.getNodeType());
		return true;
	}
	
	@Override
	public boolean visit(TryStatement node) {
		addMark(node.getNodeType());
		return true;
	}
	
	@Override
	public boolean visit(TypeDeclarationStatement node) {
		addMark(node.getNodeType());
		return true;
	}
	
	@Override
	public boolean visit(VariableDeclarationStatement node) {
		addMark(node.getNodeType());
		return true;
	}
	
	@Override
	public boolean visit(WhileStatement node) {
		addMark(node.getNodeType());
		return true;
	}
	
//	@Override
//	public void endVisit(TypeDeclaration node) {
//		if (seq.lastIndexOf("(") == seq.length()-1) {
//			seq.deleteCharAt(seq.length() - 1);
//		} else {
//			seq.append(")");
//		}
//	}
/*	@Override
	public void endVisit(Modifier node) {
		if (seq.lastIndexOf("(") == seq.length()-1) {
			seq.deleteCharAt(seq.length() - 1);
		} else {
			seq.append(")");
		}
	}
	@Override
	public void endVisit(SimpleName node) {
		if (seq.lastIndexOf("(") == seq.length()-1) {
			seq.deleteCharAt(seq.length() - 1);
		} else {
			seq.append(")");
		}
		
	}
	@Override
	public void endVisit(FieldDeclaration node) {
		if (seq.lastIndexOf("(") == seq.length()-1) {
			seq.deleteCharAt(seq.length() - 1);
		} else {
			seq.append(")");
		}
		
	}
	@Override
	public void endVisit(ClassInstanceCreation node) {
		if (seq.lastIndexOf("(") == seq.length()-1) {
			seq.deleteCharAt(seq.length() - 1);
		} else {
			seq.append(")");
		}
		
	}
	@Override
	public void endVisit(SingleVariableDeclaration node) {
		if (seq.lastIndexOf("(") == seq.length()-1) {
			seq.deleteCharAt(seq.length() - 1);
		} else {
			seq.append(")");
		}
		
	}
	@Override
	public void endVisit(MethodInvocation node) {
		if (seq.lastIndexOf("(") == seq.length()-1) {
			seq.deleteCharAt(seq.length() - 1);
		} else {
			seq.append(")");
		}
		
	}
	@Override
	public void endVisit(Assignment node) {
		if (seq.lastIndexOf("(") == seq.length()-1) {
			seq.deleteCharAt(seq.length() - 1);
		} else {
			seq.append(")");
		}
		
	}
	@Override
	public void endVisit(VariableDeclarationExpression node) {
		if (seq.lastIndexOf("(") == seq.length()-1) {
			seq.deleteCharAt(seq.length() - 1);
		} else {
			seq.append(")");
		}
		
	}
	@Override
	public void endVisit(CastExpression node) {
		if (seq.lastIndexOf("(") == seq.length()-1) {
			seq.deleteCharAt(seq.length() - 1);
		} else {
			seq.append(")");
		}
		
	}
	@Override
	public void endVisit(ArrayCreation node) {
		if (seq.lastIndexOf("(") == seq.length()-1) {
			seq.deleteCharAt(seq.length() - 1);
		} else {
			seq.append(")");
		}
		
	}
	@Override
	public void endVisit(ArrayInitializer node) {
		if (seq.lastIndexOf("(") == seq.length()-1) {
			seq.deleteCharAt(seq.length() - 1);
		} else {
			seq.append(")");
		}
		
	}
	@Override
	public void endVisit(ArrayAccess node) {
		if (seq.lastIndexOf("(") == seq.length()-1) {
			seq.deleteCharAt(seq.length() - 1);
		} else {
			seq.append(")");
		}
		
	}
	@Override
	public void endVisit(FieldAccess node) {
		if (seq.lastIndexOf("(") == seq.length()-1) {
			seq.deleteCharAt(seq.length() - 1);
		} else {
			seq.append(")");
		}
		
	}
	@Override
	public void endVisit(PrefixExpression node) {
		if (seq.lastIndexOf("(") == seq.length()-1) {
			seq.deleteCharAt(seq.length() - 1);
		} else {
			seq.append(")");
		}
		
	}
	@Override
	public void endVisit(InfixExpression node) {
		if (seq.lastIndexOf("(") == seq.length()-1) {
			seq.deleteCharAt(seq.length() - 1);
		} else {
			seq.append(")");
		}
		
	}
	@Override
	public void endVisit(PostfixExpression node) {
		if (seq.lastIndexOf("(") == seq.length()-1) {
			seq.deleteCharAt(seq.length() - 1);
		} else {
			seq.append(")");
		}
		
	}
	@Override
	public void endVisit(NullLiteral node) {
		if (seq.lastIndexOf("(") == seq.length()-1) {
			seq.deleteCharAt(seq.length() - 1);
		} else {
			seq.append(")");
		}
		
	}
	@Override
	public void endVisit(NumberLiteral node) {
		if (seq.lastIndexOf("(") == seq.length()-1) {
			seq.deleteCharAt(seq.length() - 1);
		} else {
			seq.append(")");
		}
		
	}
	@Override
	public void endVisit(StringLiteral node) {
		if (seq.lastIndexOf("(") == seq.length()-1) {
			seq.deleteCharAt(seq.length() - 1);
		} else {
			seq.append(")");
		}
		
	}
	@Override
	public void endVisit(BooleanLiteral node) {
		if (seq.lastIndexOf("(") == seq.length()-1) {
			seq.deleteCharAt(seq.length() - 1);
		} else {
			seq.append(")");
		}
		
	}
*/
	
	/**
	// **********************************************************************
	// *********no need to use "(" and ")" since no zhangshasha *******
	// **********************************************************************
	 * 
	 */
//
//	@Override
//	public void endVisit(CompilationUnit node) {
//		if (seq.lastIndexOf("(") == seq.length()-1) {
//			seq.deleteCharAt(seq.length() - 1);
//		} else {
//			seq.append(")");
//		}
//	}
//	@Override
//	public void endVisit(ImportDeclaration node) {
//		if (seq.lastIndexOf("(") == seq.length()-1) {
//			seq.deleteCharAt(seq.length() - 1);
//		} else {
//			seq.append(")");
//		}
//	}
//	@Override
//	public void endVisit(MethodDeclaration node) {
//		if (seq.lastIndexOf("(") == seq.length()-1) {
//			seq.deleteCharAt(seq.length() - 1);
//		} else {
//			seq.append(")");
//		}
//		
//	}
//	
//	@Override
//	public void endVisit(BreakStatement node) {
//		if (seq.lastIndexOf("(") == seq.length()-1) {
//			seq.deleteCharAt(seq.length() - 1);
//		} else {
//			seq.append(")");
//		}
//	}
//	
//	@Override
//	public void endVisit(ConstructorInvocation node) {
//		if (seq.lastIndexOf("(") == seq.length()-1) {
//			seq.deleteCharAt(seq.length() - 1);
//		} else {
//			seq.append(")");
//		}
//	}
//	
//	@Override
//	public void endVisit(ContinueStatement node) {
//		if (seq.lastIndexOf("(") == seq.length()-1) {
//			seq.deleteCharAt(seq.length() - 1);
//		} else {
//			seq.append(")");
//		}
//	}
//	
//	@Override
//	public void endVisit(DoStatement node) {
//		if (seq.lastIndexOf("(") == seq.length()-1) {
//			seq.deleteCharAt(seq.length() - 1);
//		} else {
//			seq.append(")");
//		}
//	}
//	
//	@Override
//	public void endVisit(EnhancedForStatement node) {
//		if (seq.lastIndexOf("(") == seq.length()-1) {
//			seq.deleteCharAt(seq.length() - 1);
//		} else {
//			seq.append(")");
//		}
//		
//	}
//	
//	@Override
//	public void endVisit(ExpressionStatement node) {
//		if (seq.lastIndexOf("(") == seq.length()-1) {
//			seq.deleteCharAt(seq.length() - 1);
//		} else {
//			seq.append(")");
//		}
//		
//	}
//	
//	@Override
//	public void endVisit(ForStatement node) {
//		if (seq.lastIndexOf("(") == seq.length()-1) {
//			seq.deleteCharAt(seq.length() - 1);
//		} else {
//			seq.append(")");
//		}
//		
//	}
//	
//	@Override
//	public void endVisit(IfStatement node) {
//		if (seq.lastIndexOf("(") == seq.length()-1) {
//			seq.deleteCharAt(seq.length() - 1);
//		} else {
//			seq.append(")");
//		}
//		
//	}
//	
//	@Override
//	public void endVisit(LabeledStatement node) {
//		if (seq.lastIndexOf("(") == seq.length()-1) {
//			seq.deleteCharAt(seq.length() - 1);
//		} else {
//			seq.append(")");
//		}
//		
//	}
//	
//	@Override
//	public void endVisit(ReturnStatement node) {
//		if (seq.lastIndexOf("(") == seq.length()-1) {
//			seq.deleteCharAt(seq.length() - 1);
//		} else {
//			seq.append(")");
//		}
//		
//	}
//	
//	@Override
//	public void endVisit(SuperConstructorInvocation node) {
//		if (seq.lastIndexOf("(") == seq.length()-1) {
//			seq.deleteCharAt(seq.length() - 1);
//		} else {
//			seq.append(")");
//		}
//		
//	}
//	
//	@Override
//	public void endVisit(SwitchCase node) {
//		seq.append(")"); 	//		case:...; default:...;
//		
//	}
//	
//	@Override
//	public void endVisit(SwitchStatement node) {
//		if (seq.lastIndexOf("(") == seq.length()-1) {
//			seq.deleteCharAt(seq.length() - 1);
//		} else {
//			seq.append(")");
//		}
//		
//	}
//	
//	@Override
//	public void endVisit(ThrowStatement node) {
//		if (seq.lastIndexOf("(") == seq.length()-1) {
//			seq.deleteCharAt(seq.length() - 1);
//		} else {
//			seq.append(")");
//		}
//		
//	}
//	
//	@Override
//	public void endVisit(TryStatement node) {
//		if (seq.lastIndexOf("(") == seq.length()-1) {
//			seq.deleteCharAt(seq.length() - 1);
//		} else {
//			seq.append(")");
//		}
//		
//	}
//	
//	@Override
//	public void endVisit(TypeDeclarationStatement node) {
//		if (seq.lastIndexOf("(") == seq.length()-1) {
//			seq.deleteCharAt(seq.length() - 1);
//		} else {
//			seq.append(")");
//		}
//		
//	}
//	
//	@Override
//	public void endVisit(VariableDeclarationStatement node) {
//		if (seq.lastIndexOf("(") == seq.length()-1) {
//			seq.deleteCharAt(seq.length() - 1);
//		} else {
//			seq.append(")");
//		}
//		
//	}
//	
//	@Override
//	public void endVisit(WhileStatement node) {
//		if (seq.lastIndexOf("(") == seq.length()-1) {
//			seq.deleteCharAt(seq.length() - 1);
//		} else {
//			seq.append(")");
//		}
//	}

}
