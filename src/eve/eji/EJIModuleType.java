package eve.eji;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The EJIModuleType creates a native "module" type. Module types can be thought
 * of as namespaces. They are a way to organize functionality into discrete
 * units. Module types cannot be created through invocation, as they have no
 * __create method.
 * @author jeff
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EJIModuleType {
	public String value();
}
