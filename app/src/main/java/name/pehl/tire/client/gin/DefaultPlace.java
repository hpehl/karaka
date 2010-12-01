package name.pehl.tire.client.gin;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import name.pehl.tire.client.TirePlaceManager;

import com.google.inject.BindingAnnotation;

/**
 * This annotation is used in {@link TirePlaceManager} and is bind in
 * {@link TireModule}. It's purpose is to bind the default place to a default
 * presenter.
 * 
 * @author $Author$
 * @version $Date$ $Revision$
 */
@BindingAnnotation
@Target({FIELD, PARAMETER, METHOD})
@Retention(RUNTIME)
public @interface DefaultPlace
{
}
