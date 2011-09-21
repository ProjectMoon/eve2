package eve.core.builtins;

import java.beans.IntrospectionException;
import java.util.HashMap;
import java.util.Map;

import eve.core.EveObject;
import eve.core.EveObjectFactory;
import eve.eji.EJIField;
import eve.eji.EJIHelper;
import eve.eji.EJIType;

/**
 * The function prototype. This type is not annotated with EJIType because
 * it must exist before the EJIScanner runs. It is added to the type pool
 * immediately. Functions are a bit special in the way they are created.
 * They do not use {@link EJIHelper#createEJIObject(Object)} like the other
 * builtin types. Doing so results in infinite recursion.
 * <br><br>
 * Thus, the builtin function type is split into a prototype and an instance
 * class, with instances only being creatable from the prototype class. The
 * prototype class records all of the instance class's methods, adding them
 * to each new function instance, as a lightweight emulation of what EJIHelper
 * does. This prevents the recursion and still gives us the same magic of
 * defining Java methods and having them turn into Eve functions.
 * @author jeff
 *
 */
public class EveFunctionPrototype extends EveObject {
	private static Map<String, EveObject> instanceProps = new HashMap<String, EveObject>();
	
	static {
		EveObject eo = new EveFunction();
		try {
			eo = EJIHelper.createEJIObject(eo);
			
			for (Map.Entry<String, EveObject> entry : eo.getFields().entrySet()) {
				instanceProps.put(entry.getKey(), entry.getValue());
			}
		}
		catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * For prototypes, the empty constructor is used when cloning from it.
	 * Usually used by literals (sometimes other stuff). Eve functions
	 * cannot be created by invoking the type name.
	 */
	public EveFunctionPrototype() {
		setInternalType(EveType.PROTOTYPE);
		setTypeName("function");
	}
	
	@Override
	public EveObject eveClone() {
		EveObject clone = new EveFunctionPrototype();
		clone.cloneFrom(this);
		return clone;
	}
	
	public static EveFunction createInstance() {
		EveFunction func = new EveFunction();
		
		//Notice we do not clone here (would cause recursion because of EJIHelper).
		//Even though every EveFunction has a reference to the same Method,
		//JavaMethodInvocation can pull the correct "this" context for any given
		//invocation. Thus, we are safe doing this.
		for (Map.Entry<String, EveObject> entry : instanceProps.entrySet()) {
			func.putField(entry.getKey(), entry.getValue());
		}

		return func;
	}
		
}
