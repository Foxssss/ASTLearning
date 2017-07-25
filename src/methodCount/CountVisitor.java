package methodCount;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;

public class CountVisitor extends ASTVisitor {
	public int main = 0, abs = 0, con = 0;
	@Override
	public boolean visit(MethodDeclaration node) {
		if(node.isConstructor()) {
			con ++;
		}
		else if(node.getName().getIdentifier().equals("main")) {
			main ++;
		}
		else {
			for(Object o:node.modifiers())
				if(((Modifier)o).isAbstract())
					abs++;
		}
		return true;
	}
}
