package name.pehl.tire.server.rest.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.inject.BindingAnnotation;

/**
 * Bind this annotation on the string you want to use as a security token:
 * 
 * <pre>
 * bindConstant().annotatedWith(SecurityToken.class).to(&quot;mytoken&quot;);
 * </pre>
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-10-18 17:12:52 +0200 (Mo, 18. Okt 2010) $ $Revision: 175 $
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@BindingAnnotation
public @interface SecurityToken
{
}
