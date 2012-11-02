package name.pehl.karaka.client.gin;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This annotation is used in {@link name.pehl.karaka.client.KarakaPlaceManager} and is bind in
 * {@link KarakaModule}. It's purpose is to bind the default place to a default
 * presenter.
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2011-05-16 12:54:26 +0200 (Mo, 16. Mai 2011) $ $Revision: 208
 *          $
 */
@BindingAnnotation
@Target({FIELD, PARAMETER, METHOD})
@Retention(RUNTIME)
public @interface DefaultPlace
{
}
