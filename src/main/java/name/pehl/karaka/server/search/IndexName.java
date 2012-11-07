package name.pehl.karaka.server.search;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@Qualifier
@Retention(RUNTIME)
@Target({TYPE, METHOD, FIELD})
public @interface IndexName
{
    @Nonbinding
    String value();

    @Nonbinding
    int version() default 0;
}
