package eve.statements;

import java.util.Deque;
import java.util.List;

import eve.core.EveObject;
import eve.statements.expressions.ExpressionStatement;

public class PrintStatement extends AbstractStatement implements EveStatement {
	private ExpressionStatement expression;
	private boolean printNewline = false;

	public PrintStatement(ExpressionStatement expression) {
		this.expression = expression;
	}
	
	@Override
	public EveObject execute() {
		if (printNewline) {
			if (expression != null) {
				EveObject eo = expression.execute();
				System.out.println("eo type is " + eo.getType());
				System.out.println("eo is " + eo.getClass());
				System.out.println("has? " + eo.hasField("toString"));
				System.out.println(eo);
			}
			else {
				System.out.println();
			}
		}
		else {
			System.out.print(expression.execute());
		}
		return null;
	}
	
	@Override
	public List<String> getIdentifiers() {
		return expression.getIdentifiers();		
	}

	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		expression.closureAnalysis(closureList);
	}

	public void setPrintNewline(boolean printNewline) {
		this.printNewline = printNewline;
	}

	public boolean isPrintNewline() {
		return printNewline;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((expression == null) ? 0 : expression.hashCode());
		result = prime * result + (printNewline ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrintStatement other = (PrintStatement) obj;
		if (expression == null) {
			if (other.expression != null)
				return false;
		} else if (!expression.equals(other.expression))
			return false;
		if (printNewline != other.printNewline)
			return false;
		return true;
	}
}
