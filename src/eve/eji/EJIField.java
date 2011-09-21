package eve.eji;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import javassist.Modifier;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.core.EveObjectFactory;

public class EJIField {
	private boolean isStatic = false;
	private boolean readOnly = false;
	
	//For non-static.
	private PropertyDescriptor pd;
	
	//For static.
	private Method method;
	
	/**
	 * Creates a new EJI field from a bean context and property descriptor.
	 * @param context
	 * @param pd
	 */
	public EJIField(PropertyDescriptor pd) {
		this.pd = pd;
	}
	
	/**
	 * Creates a read-only property from a static method. Used for native namespaces.
	 * @param method
	 */
	public EJIField(Method method) {	
		this.method = method;
		this.isStatic = true;
		this.readOnly = true;
	}
	
	public EveObject get() {
		try {
			Object o = null;
			
			if (isStatic) {
				if (!Modifier.isStatic(method.getModifiers())) {
					throw new EveError("method for EJI field is not static.");
				}
				
				o = method.invoke(null, (Object[])null);
			}
			else {
				Object context = EJIHelper.self();
				o = pd.getReadMethod().invoke(context, (Object[])null);
			}

			if (o != null) {
				if (o instanceof EveObject) {
					return (EveObject)o;
				}
				else {
					return EJIHelper.createEJIObject(o);
				}
			}
		}
		catch (IllegalArgumentException e) {
			throw new EveError(e.getMessage());
		}
		catch (IllegalAccessException e) {
			throw new EveError(e.getMessage());
		}
		catch (InvocationTargetException e) {
			throw new EveError(e.getCause().getMessage());
		}
		catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public void set(EveObject value) {
		if (readOnly) {
			throw new EveError("This property is read-only.");
		}
		
		try {
			Method writeMethod = pd.getWriteMethod();
			if (writeMethod == null) {
				throw new EveError("This property is read-only.");
			}
			
			Object context = EJIHelper.self();
			writeMethod.invoke(context, new Object[] { value.getValue() });
		}
		catch (IllegalArgumentException e) {
			throw new EveError(e.getMessage());
		}
		catch (IllegalAccessException e) {
			throw new EveError(e.getMessage());
		}
		catch (InvocationTargetException e) {
			//an error happened in the java code.
			if (e.getCause() != null) {
				throw new EveError(e.getCause().getMessage());
			}
			else {
				throw new EveError(e.getMessage());
			}
		}			
	}
	
	public EveObject createObject() {
		class DynamicGetter extends EJIFunction {
			@Override
			public EveObject execute(Map<String, EveObject> parameters) {
				return EJIField.this.get();
			}
		}
		
		class DynamicSetter extends EJIFunction {
			public DynamicSetter() {
				setParameters("value");
			}

			@Override
			public EveObject execute(Map<String, EveObject> parameters) {
				EveObject value = parameters.get("value");
				EJIField.this.set(value);
				return null;
			}
		}
		
		//TODO: investigate why using customType (which calls ejiInit) causes an
		//infinite loop here.
		EveObject eo = EveObjectFactory.empty();
		eo.setTypeName("eji_field");
		eo.putField("get", EveObjectFactory.create(new DynamicGetter()));
		eo.putField("set", EveObjectFactory.create(new DynamicSetter()));
		return eo;
	}
}
