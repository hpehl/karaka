package name.pehl.karaka.server.search;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

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
}
