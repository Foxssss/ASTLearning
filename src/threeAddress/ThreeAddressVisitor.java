package threeAddress;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;

public class ThreeAddressVisitor extends ASTVisitor {
	private AST ast;
	private ASTRewrite rewrite;
	
	private String varName = "tmpVar_";
	private int varNo = -1;
	
	/*
	 * ********************* Method  List ***************************
	 * public boolean visit(IfStatement node);                                 *
	 * public boolean visit(ExpressionStatement node);                 *
	 * public boolean visit(VariableDeclarationStatement node);  *
	 * **************************************************************
	 */	
	
	/**
	 * assume only boolean type variable in the condition expression of IfStatement
	 */
	@Override
	public boolean visit(IfStatement node) {
		Expression exp = node.getExpression();
		SimpleName finalName = ast.newSimpleName("finalName");
		ArrayList<VariableDeclarationStatement> decList = 
				splitExpression(finalName, ast.newPrimitiveType(PrimitiveType.BOOLEAN), exp);		
		ASTNode parent = node.getParent();
		if(parent instanceof Block) {
			int size = decList.size();
			if(size > 1) {
				for(int i = 0; i < size - 1; ++i) {
					VariableDeclarationStatement stmt = decList.get(i);
					Block block = (Block)parent;
					ListRewrite ifRewrite = rewrite.getListRewrite(block, Block.STATEMENTS_PROPERTY);
					ifRewrite.insertBefore(stmt, node, null);
				}
				rewrite.replace(exp, ((VariableDeclarationFragment)decList.get(size - 1).fragments().get(0)).getInitializer(), null);
			}
		}
		else
			try {
				throw new Exception("The process of transferring to block is not complete!");
			} catch (Exception e) {
				e.printStackTrace();
			}		
		return true;
	}
	
	/**
	 * assume only InfixExpression, PrefixExpression, ParenthesizedExpression in the RightHandSize of assignment
	 * 			////////////////////////////////////////////////////////////////////////
	 *			//                                 Unsolvable Here !!!                               //
	 *			// the type of leftHandSide variable can not be defined      //
	 *			// might need other method to solve this problem              //
	 *			///////////////////////////////////////////////////////////////////////
	 */
	@Override
	public boolean visit(ExpressionStatement node) {
		Expression exp = node.getExpression();
		if(exp instanceof Assignment) {
			Expression right = ((Assignment) exp).getRightHandSide();
			Expression left = ((Assignment) exp).getLeftHandSide();
			if(right instanceof InfixExpression || right instanceof PrefixExpression || 
					right instanceof ParenthesizedExpression) {
				varNo++;
				SimpleName rightName = ast.newSimpleName(varName + varNo);
				if(left instanceof SimpleName) {
					////////////////////////////////////////////////////////////////////////
					//                                 Unsolvable Here !!!                               //
					// the type of leftHandSide variable can not be defined      //
					// might need other method to solve this problem              //
					///////////////////////////////////////////////////////////////////////
				}
				Type tmptype = ast.newPrimitiveType(PrimitiveType.INT);
				ArrayList<VariableDeclarationStatement> decList = splitExpression(rightName, tmptype, right);
				Block block = (Block) node.getParent();
				ListRewrite addRewrite = rewrite.getListRewrite(block, Block.STATEMENTS_PROPERTY);
				for(VariableDeclarationStatement stmt : decList) {
					addRewrite.insertBefore(stmt, node, null);
				}
				
				Assignment finalAssign = (Assignment) ASTNode.copySubtree(ast, exp);
				finalAssign.setRightHandSide(rightName);
				ExpressionStatement finalStmt = ast.newExpressionStatement(exp);
				rewrite.replace(node, finalStmt, null);
			}
		}
		return true;
	}
	
	/**
	 * 1 - assume only InfixExpression, PrefixExpression, ParenthesizedExpression and MethodInvocation in the declaration
	 * 2 - assume only one variable declared in one VariableDeclarationStatement
	 */
	@Override
	public boolean visit(VariableDeclarationStatement node) {
		for(Object o :  node.fragments()) {
			VariableDeclarationFragment fragment = (VariableDeclarationFragment) o;
			Expression initializer = fragment.getInitializer();
			ArrayList<VariableDeclarationStatement> decList = splitExpression(fragment.getName(), node.getType(), initializer);
			Block block = (Block) node.getParent();
			ListRewrite addRewrite = rewrite.getListRewrite(block, Block.STATEMENTS_PROPERTY);
			for(VariableDeclarationStatement stmt : decList) {
				addRewrite.insertBefore(stmt, node, null);
			}
			addRewrite.remove(node, null);
		}
		return true;
	}
		
	/**
	 * @param pname - name of the final variable
	 * @param ptype - type of the final variable
	 * @param exp - the expression to be split
	 * @return the list of the split result
	 */
	public ArrayList<VariableDeclarationStatement> splitExpression(SimpleName pname, Type ptype, Expression exp) {
		ArrayList<VariableDeclarationStatement> result = new ArrayList<VariableDeclarationStatement>();
		if(exp instanceof PrefixExpression) {
			VariableDeclarationFragment finalFragment = ast.newVariableDeclarationFragment();
			finalFragment.setName((SimpleName)ASTNode.copySubtree(ast, pname));
			Expression operand = ((PrefixExpression) exp).getOperand();
			//if(operand instanceof NumberLiteral)
			//	return result;
			if(!(operand instanceof SimpleName || operand instanceof NumberLiteral)) {
				varNo++;
				SimpleName opName = ast.newSimpleName(varName + varNo);
				((PrefixExpression) exp).setOperand(opName);
				result.addAll(splitExpression(opName, ptype, operand));
			}
			finalFragment.setInitializer((PrefixExpression)ASTNode.copySubtree(ast, exp));
			VariableDeclarationStatement finalStmt = ast.newVariableDeclarationStatement(finalFragment);
			finalStmt.setType((Type) ASTNode.copySubtree(ast, ptype));
			result.add(finalStmt);
		}
		else if(exp instanceof InfixExpression) {
			Expression left = ((InfixExpression) exp).getLeftOperand();
			if(!(left instanceof SimpleName || left instanceof NumberLiteral)) {
			//if(left instanceof InfixExpression || left instanceof PrefixExpression || left instanceof MethodInvocation 
			//		|| left instanceof ParenthesizedExpression) {
				varNo++;
				SimpleName leftName = ast.newSimpleName(varName + varNo);
				((InfixExpression) exp).setLeftOperand(leftName);
				result.addAll(splitExpression(leftName, ptype, left));
			}
			Expression right = ((InfixExpression) exp).getRightOperand();
			if(!(right instanceof SimpleName || right instanceof NumberLiteral)) {
			//if(right instanceof InfixExpression || right instanceof PrefixExpression || right instanceof MethodInvocation 
			//		|| right instanceof ParenthesizedExpression) {
				varNo++;
				SimpleName rightName = ast.newSimpleName(varName + varNo);
				((InfixExpression) exp).setRightOperand(rightName);
				result.addAll(splitExpression(rightName, ptype, right));
			}
			if(((InfixExpression) exp).hasExtendedOperands()) {
				// e.g. a = b - c - d - ... - z
				// STEP 1: generate "tempVar_i = b - c";
				varNo++;
				SimpleName tempName = ast.newSimpleName(varName + varNo);
				Type tempType = (Type) ASTNode.copySubtree(ast, ptype);
				VariableDeclarationFragment tempFragment = ast.newVariableDeclarationFragment();
				tempFragment.setName(tempName);
				InfixExpression newExp = (InfixExpression) ASTNode.copySubtree(ast, exp);
				newExp.extendedOperands().clear();
				tempFragment.setInitializer(newExp);
				VariableDeclarationStatement tempStmt = ast.newVariableDeclarationStatement(tempFragment);
				tempStmt.setType(tempType);
				result.add(tempStmt);
				// STEP 2: generate "tempVar_k = tempVar_j - (d,e,f,..., y)"
				//              and generate "a = tempVar_k - z"
				List<?> extend = ((InfixExpression) exp).extendedOperands();
				for(int i = 0; i < extend.size(); ++i) {
					if(i == extend.size() - 1) {
						tempName = (SimpleName) ASTNode.copySubtree(ast, pname);
					}
					else {
						varNo++;
						tempName = ast.newSimpleName(varName + varNo);
					}
					tempType = (Type) ASTNode.copySubtree(ast, ptype);
					tempFragment = ast.newVariableDeclarationFragment();
					tempFragment.setName(tempName);
					InfixExpression tempExp = ast.newInfixExpression();
					tempExp.setOperator(((InfixExpression) exp).getOperator());
					tempExp.setLeftOperand(ast.newSimpleName(varName + varNo));
					Expression extendExp = (Expression) extend.get(i);
					if(extendExp instanceof SimpleName)
						tempExp.setRightOperand((SimpleName)ASTNode.copySubtree(ast, extendExp));
					else {
						varNo++;
						SimpleName tempName2 = ast.newSimpleName(varName + varNo);
						result.addAll(splitExpression(tempName2, ptype, extendExp));
						tempExp.setRightOperand((SimpleName) ASTNode.copySubtree(ast, tempName2));						
					}
					tempFragment.setInitializer(tempExp);
					tempStmt = ast.newVariableDeclarationStatement(tempFragment);
					tempStmt.setType(tempType);
					result.add(tempStmt);
				}
				return result;
			}
			VariableDeclarationFragment finalFragment = ast.newVariableDeclarationFragment();
			finalFragment.setName((SimpleName) ASTNode.copySubtree(ast, pname));
			finalFragment.setInitializer((Expression) ASTNode.copySubtree(ast, exp));
			VariableDeclarationStatement finalStmt = ast.newVariableDeclarationStatement(finalFragment);
			finalStmt.setType((Type) ASTNode.copySubtree(ast, ptype));
			result.add(finalStmt);
		}
		else if(exp instanceof MethodInvocation) {
			VariableDeclarationFragment finalFragment = ast.newVariableDeclarationFragment();
			finalFragment.setName((SimpleName)ASTNode.copySubtree(ast, pname));
			MethodInvocation finalExp = (MethodInvocation) ASTNode.copySubtree(ast, (MethodInvocation)exp);
			finalFragment.setInitializer(finalExp);
			VariableDeclarationStatement finalStmt = ast.newVariableDeclarationStatement(finalFragment);
			finalStmt.setType((Type)ASTNode.copySubtree(ast, ptype));
			result.add(finalStmt);
		}
		else if(exp instanceof ParenthesizedExpression) {
			Expression inExp = ((ParenthesizedExpression) exp).getExpression();
			result.addAll(splitExpression(pname, ptype, inExp));
		}
		else
			try {
				throw new Exception("Unhandled type of Expression!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return result;
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
