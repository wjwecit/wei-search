/**
 * 
 */
package wei.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Definite the auto increace fields corresponding to the column of the data base table.
 * 
 * @author wei
 * @since 2014-06-08
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TableAIKeyAnnotation {
	public String columnName() default "";
}
