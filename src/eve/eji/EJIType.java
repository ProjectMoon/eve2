package eve.eji;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import eve.core.EveObject;

public abstract class EJIType {
	public EveObject createObject() throws IntrospectionException {
		EveObject eo = EveObject.customType(this.getClass().getName());
		
		BeanInfo info = Introspector.getBeanInfo(this.getClass());
		
		for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
			eo.putField(pd.getName(), new EJIField(this, pd));	
		}
		
		return eo;
	}
}
