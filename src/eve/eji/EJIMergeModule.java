package eve.eji;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that this EJI Module type should be merged into an existing Eve type,
 * rather than created as a unique type. This is useful for adding functions
 * to the global type, or spreading implementation of a module across many
 * classes.
 * @author jeff
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EJIMergeModule {
	public String value();
}
