package name.pehl.taoki.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for resource methods which should be intercepted by
 * {@link SecurityInterceptor}.
 * 
 * @author $Author: harald.pehl $
 * @version $Revision: 180 $
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Secured
{
}
