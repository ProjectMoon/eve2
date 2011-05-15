package eve.eji;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import eve.core.EveError;
import eve.core.EveObject;

class EJIField extends DynamicField {
	private Object context;
	private PropertyDescriptor pd;
	
	public EJIField(Object context, PropertyDescriptor pd) {
		this.context = context;
		this.pd = pd;
	}
	
	@Override
	public EveObject get() {
		try {
			Object o = pd.getReadMethod().invoke(context, (Object[])null);

			if (o != null) {
				if (o instanceof EveObject) {
					return (EveObject)o;
				}
				else {
					return EJIHelper.createEJIType(o);
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
			throw new EveError(e.getMessage());
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void set(EveObject value) {
		try {
			pd.getWriteMethod().invoke(context, new Object[] { value.getObjectValue() });
		}
		catch (IllegalArgumentException e) {
			throw new EveError(e.getMessage());
		}
		catch (IllegalAccessException e) {
			throw new EveError(e.getMessage());
		}
		catch (InvocationTargetException e) {
			throw new EveError(e.getMessage());
		}			
	}
}