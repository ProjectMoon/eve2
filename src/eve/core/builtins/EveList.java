package eve.core.builtins;

import java.util.List;
import java.util.TreeMap;

import eve.core.EveError;
import eve.core.EveObject;
import eve.eji.DynamicField;
import eve.eji.EJIBuiltinType;
import eve.eji.EJIHelper;
import eve.eji.EJIIndexedAccessor;
import eve.eji.EJIIndexedMutator;
import eve.eji.EJIType;

@EJIType("list")
@EJIBuiltinType
public class EveList extends EveObject {
	public EveList() {}
	
	public EveList(List<EveObject> l) {
		if (l != null) {
			for (int c = 0; c < l.size(); c++) {
				this.putField(c, l.get(c));
			}
		}
	}
	
	@EJIIndexedAccessor
	public EveObject get(int index) {
		return this.getField(index);
	}
	
	@EJIIndexedMutator
	public void set(int index, EveObject eo) {
		this.putField(index, eo);
	}
	
	public int getLength() {
		return this.getListValue().size();
	}
	
	@Override
	public EveObject eveClone() {
		EveObject clone = new EveList();
		clone.cloneFrom(this);
		return clone;
	}
}
