package eve.eji;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * When an EJIType or EJIModule is marked with this annotation, the interpreter will
 * not perform type coercion from certain Java types to Eve types. Normally, any Java
 * representation of the builtin Eve types (int, string, double, etc) will be automatically
 * converted from its respective Java type (int, Integer, String, etc) into the corresponding
 * Eve type (EveInteger, EveString, etc). When this annotation is present, this will not
 * occur.
 * @author jeff
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EJINoCoerce {

}
