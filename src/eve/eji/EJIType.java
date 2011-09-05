package eve.eji;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares that a class is to become an external Eve type. This is generally used
 * with POJOs (plain old Java objects) in order to magically turn them into eve
 * types. However, it can be used on classes that extend EveObject as well.
 * If a class extends EveObject, no special magic will be applied to it; it will
 * simply be dumped into the type pool as its EveObject self. That means no properties
 * of the class will be added to the prototype. Extending EveObject and applying
 * EJIType should only be done by true built-ins.
 * @author jeff
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EJIType {
	public String value();
}
