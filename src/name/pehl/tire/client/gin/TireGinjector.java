package name.pehl.tire.client.gin;

import name.pehl.tire.client.application.ApplicationPresenter;
import net.customware.gwt.presenter.client.place.PlaceManager;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@GinModules(TireModule.class)
public interface TireGinjector extends Ginjector
{
    ApplicationPresenter getApplicationPresenter();


    PlaceManager getPlaceManager();
}
