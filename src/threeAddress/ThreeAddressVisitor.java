package threeAddress;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;

public class ThreeAddressVisitor extends ASTVisitor {
	private AST ast;
	private ASTRewrite rewrite;
	
	private String varName = "tmpVar_";
	private int varNo = 0;
	//public boolean visit(IfStatement node);
	//public boolean visit(VariableDeclarationStatement node);
	
	
	@Override
	public boolean visit(IfStatement node) {
		//Expression exp = node.getExpression();
		//ArrayList<VariableDeclarationStatement> decList = splitExpression(exp);
		
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
	
	/**
	 * assume only InfixExpression, PrefixExpression and MethodInvocation in the declaration
	 * assume only one variable declared in one VariableDeclarationStatement
	 */
	@Override
	public boolean visit(VariableDeclarationStatement node) {
		ArrayList<VariableDeclarationStatement> decList = splitDeclaration(node);
		Block block = (Block) node.getParent();
		for(VariableDeclarationStatement stmt : decList) {
			ListRewrite addRewrite = rewrite.getListRewrite(block, Block.STATEMENTS_PROPERTY);
			addRewrite.insertBefore(stmt, node, null);
		}
		return true;
	}
	
	public ArrayList<VariableDeclarationStatement> splitDeclaration(VariableDeclarationStatement node) {
		ArrayList<VariableDeclarationStatement> result = new ArrayList<VariableDeclarationStatement>();
		VariableDeclarationFragment fragment = (VariableDeclarationFragment) node.fragments().get(0);
		Expression initializer = fragment.getInitializer();
		if(initializer instanceof InfixExpression) {
			Expression left = ((InfixExpression) initializer).getLeftOperand();
			if(left instanceof InfixExpression || left instanceof PrefixExpression) {
				SimpleName leftName = ast.newSimpleName(varName + varNo);
				varNo++;
				rewrite.replace(left, leftName, null);
				result.addAll(splitExpression(leftName, node.getType(), left));
			}
			Expression right = ((InfixExpression) initializer).getRightOperand();
			if(right instanceof InfixExpression || right instanceof PrefixExpression) {
				SimpleName rightName = ast.newSimpleName(varName + varNo);
				varNo++;
				rewrite.replace(right, rightName, null);
				result.addAll(splitExpression(rightName, node.getType(), right));
			}
			if(((InfixExpression) initializer).hasExtendedOperands()) {
				// e.g. a = b - c - d - ... - z
				// STEP 1: generate "tempVar_i = b - c";
				SimpleName tempName = ast.newSimpleName(varName + varNo);
				varNo++;
				Type tempType = (Type) ASTNode.copySubtree(ast, node.getType());
				VariableDeclarationFragment tempFragment = ast.newVariableDeclarationFragment();
				tempFragment.setName(tempName);
				InfixExpression newExp = (InfixExpression) ASTNode.copySubtree(ast, initializer);
				newExp.extendedOperands().clear();
				tempFragment.setInitializer(newExp);
				VariableDeclarationStatement tempStmt = ast.newVariableDeclarationStatement(tempFragment);
				tempStmt.setType(tempType);
				result.add(tempStmt);
				// STEP 2: generate "tempVar_k = tempVar_j - (d,e,f,..., y)"
				//              and generate "a = tempVar_k - z"
				List<?> extend = ((InfixExpression) initializer).extendedOperands();
				for(int i = 0; i < extend.size(); ++i) {
					if(i == extend.size() - 1) {
						tempName = (SimpleName) ASTNode.copySubtree(ast, fragment.getName());
					}
					else {
						tempName = ast.newSimpleName(varName + varNo);
						varNo++;
					}
					tempType = (Type) ASTNode.copySubtree(ast, node.getType());
					tempFragment = ast.newVariableDeclarationFragment();
					tempFragment.setName(tempName);
					InfixExpression tempExp = ast.newInfixExpression();
					tempExp.setOperator(((InfixExpression) initializer).getOperator());
					tempExp.setLeftOperand(ast.newSimpleName(varName + (varNo - 2)));
					Expression extendExp = (Expression) extend.get(i);
					if(extendExp instanceof MethodInvocation) {
						SimpleName tempName2 = ast.newSimpleName(varName + varNo);
						varNo++;
						Type tempType2 = (Type)ASTNode.copySubtree(ast, node.getType());
						VariableDeclarationFragment tempFragment2 = ast.newVariableDeclarationFragment();
						tempFragment2.setName(tempName2);
						MethodInvocation tempExp2 = (MethodInvocation) ASTNode.copySubtree(ast, (MethodInvocation)extendExp);
						tempFragment2.setInitializer(tempExp2);
						VariableDeclarationStatement tempStmt2 = ast.newVariableDeclarationStatement(tempFragment2);
						tempStmt2.setType(tempType2);
						result.add(tempStmt2);
						
						tempExp.setRightOperand((SimpleName) ASTNode.copySubtree(ast, tempName2));
					}
					else if(extendExp instanceof SimpleName)
						tempExp.setRightOperand((SimpleName)ASTNode.copySubtree(ast, extendExp));
					else
						try {
							throw new Exception("Unhandled extended operand type in InfixExpression!");
						} catch (Exception e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
					tempFragment.setInitializer(tempExp);
					tempStmt = ast.newVariableDeclarationStatement(tempFragment);
					tempStmt.setType(tempType);
					result.add(tempStmt);
				}
			}
		}
		else if(initializer instanceof PrefixExpression) {
			
		}
		else if(initializer instanceof MethodInvocation) {
			
		}
		return result;
	}
	
	/**
	 * not complete
	 * @param exp - the expression to be split
	 * @return the list of the split result
	 */
	public ArrayList<VariableDeclarationStatement> splitExpression(SimpleName pname, Type ptype, Expression exp) {
		ArrayList<VariableDeclarationStatement> result = new ArrayList<VariableDeclarationStatement>();
		if(exp instanceof PrefixExpression) {
			
		}
		else if(exp instanceof InfixExpression) {
			//SimpleName name = ast.newSimpleName(varName + varNo);
			//PrimitiveType type = ast.newPrimitiveType(PrimitiveType.INT);
			SimpleName name = (SimpleName) ASTNode.copySubtree(ast, pname);
			Type type = (Type) ASTNode.copySubtree(ast, ptype);
			VariableDeclarationFragment fragment = ast.newVariableDeclarationFragment();
			fragment.setName(name);
			Expression newExp = (Expression) ASTNode.copySubtree(ast, exp);
			fragment.setInitializer(newExp);
			VariableDeclarationStatement stmt = ast.newVariableDeclarationStatement(fragment);
			stmt.setType(type);
			result.add(stmt);
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
