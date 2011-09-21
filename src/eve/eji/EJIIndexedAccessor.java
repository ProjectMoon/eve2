package eve.eji;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a method should provide results for a integer-based indexed
 * property access (e.g myvariable[0] or myvariable["0"]). Only one such method
 * can exist in an EJI object. Otherwise, it will throw an error.
 * @author jeff
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EJIIndexedAccessor {
	
}
