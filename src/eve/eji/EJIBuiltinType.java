package eve.eji;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Signifies to the EJI scanner that this EJIType should be considered built-in,
 * and not external. This annotation should only be used by the built-in types
 * (int, string, etc). Classes annotated with this will go directly to the type
 * pool, rather than the external types pool.
 * @author jeff
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EJIBuiltinType {

}
