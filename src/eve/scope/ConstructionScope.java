package eve.scope;

import eve.statements.EveStatement;

/**
 * This interface allows us to not worry (as much) about where we are when creating
 * the script execution tree. We are always adding statements to the current ConstructionScope.
 * Currently, ConstructionScope may be Script, CreateProtoStatement, or FunctionDefExpression.
 * @author jeff
 *
 */
public interface ConstructionScope {
	public void addStatement(EveStatement statement);
}
