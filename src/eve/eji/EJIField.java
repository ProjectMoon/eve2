package eve.eji;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javassist.Modifier;

import eve.core.EveError;
import eve.core.EveObject;

class EJIField extends DynamicField {
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
	
	@Override
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

	@Override
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
}