package eve.eji;

import java.beans.IntrospectionException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eve.core.EveError;
import eve.core.EveObject;

class JavaConstructorInvocation extends EJIFunction {
	private Class<?> type;
	private boolean bypassTypeCoercion;
	
	public JavaConstructorInvocation(Class<?> type, boolean bypassTypeCoercion) {
		this.type = type;
		this.bypassTypeCoercion = bypassTypeCoercion;
		setParameters("args");
		setVarargs(true);
		setVarargsIndex(0);
		setName(type.getSimpleName() + "_Ctor");
	}
	
	@Override
	public EveObject execute(Map<String, EveObject> parameters) {
		List<EveObject> args = new ArrayList<EveObject>(0);
		EveObject argObj = parameters.get("args");
		if (argObj != null) args = argObj.getListValue();
		
		try {
			Constructor<?> ctor = EJIHelper.findConstructor(type, args);
			if (ctor == null) {
				throw new EveError("could not find constructor for " + type.getName() + " with arguments " + args);
			}
			
			Object[] initArgs = EJIHelper.mapArguments(ctor.getParameterTypes(), args);
			Object obj = ctor.newInstance(initArgs);
			return EJIHelper.createEJIObject(obj, bypassTypeCoercion);
		}
		catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			throw new EveError(e.getCause().getMessage());
		}
		catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
