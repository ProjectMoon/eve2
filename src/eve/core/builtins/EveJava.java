package eve.core.builtins;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import eve.core.EveObject;
import eve.eni.NativeFunction;
import eve.eni.NativeCode;
import eve.eni.NativeHelper;
import eve.scope.ScopeManager;

/**
 * 	The java prototype.
 * @author jeff
 *
 */
public class EveJava extends EveObject {
	private static final EveJava proto = new EveJava();
	
	public static EveJava getPrototype() {
		return proto;
	}
	
	private EveJava() {
		this.setTypeName("java");
		this.setType(EveType.PROTOTYPE);
		this.putField("create", javaFunction());
	}
	
	private static EveObject javaFunction() {
		final NativeCode nc = new NativeCode() {
			@Override
			public EveObject execute() {
				EveObject cname = ScopeManager.getVariable("cname");
				String className = cname.getStringValue();
				EveObject ctorArgs = ScopeManager.getVariable("ctorArgs");
							
				try {
					Constructor<?> ctor = NativeHelper.findConstructor(Class.forName(className), ctorArgs.getListValue());
					Object o = ctor.newInstance(NativeHelper.mapToJava(ctorArgs.getListValue()));
					EveObject eo = EveObject.javaType(o);
					eo.putField("class", new EveObject(o.getClass().getName()));
					eo.setTypeName(o.getClass().getName());
					NativeHelper.mapJavaMethods(eo);
					return eo;
				}
				catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}		
		};
		
		NativeFunction nfunc = new NativeFunction(nc);
		nfunc.addParameter("cname");
		nfunc.addParameter("ctorArgs");
		nfunc.setVarargs(true);
		nfunc.setVarargsIndex(1); //ctorArgs ...
		return new EveObject(nfunc);
	}
}
