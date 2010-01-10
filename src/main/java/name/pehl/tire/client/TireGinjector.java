package name.pehl.tire.client;

import name.pehl.tire.client.project.ProjectModule;
import net.customware.gwt.presenter.client.place.PlaceManager;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules( {TireModule.class, ProjectModule.class})
public interface TireGinjector extends Ginjector
{
    AppPresenter getAppPresenter();


    PlaceManager getPlaceManager();
}
