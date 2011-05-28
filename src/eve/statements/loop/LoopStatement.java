package eve.statements.loop;

import java.util.Deque;
import java.util.List;

import eve.core.EveObject;
import eve.statements.AbstractStatement;
import eve.statements.EveStatement;

public abstract class LoopStatement extends AbstractStatement implements EveStatement {

	@Override
	public abstract void closureAnalysis(Deque<List<String>> closureList);

	@Override
	public abstract EveObject execute();

	@Override
	public abstract List<String> getIdentifiers();

}
