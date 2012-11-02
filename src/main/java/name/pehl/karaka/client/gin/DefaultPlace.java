package name.pehl.karaka.client.gin;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import name.pehl.karaka.client.TirePlaceManager;

import com.google.inject.BindingAnnotation;

/**
 * This annotation is used in {@link TirePlaceManager} and is bind in
 * {@link TireModule}. It's purpose is to bind the default place to a default
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
