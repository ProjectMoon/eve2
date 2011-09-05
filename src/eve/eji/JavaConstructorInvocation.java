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
	
	public JavaConstructorInvocation(Class<?> type) {
		this.type = type;
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
				throw new EveError("could not find constructor for java type " + type.getName());
			}
			
			Object[] initArgs = EJIHelper.mapArguments(ctor.getParameterTypes(), args);
			Object obj = ctor.newInstance(initArgs);
			
			if (obj instanceof EveObject) {
				return (EveObject)obj;
			}
			else {
				return EJIHelper.createEJIType(obj);
			}
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
