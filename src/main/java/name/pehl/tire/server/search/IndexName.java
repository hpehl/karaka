package name.pehl.tire.server.search;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.xml.ws.BindingType;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@BindingType
@Retention(RUNTIME)
@Target({TYPE, METHOD, FIELD})
public @interface IndexName
{
    @Nonbinding
    String value();
}
