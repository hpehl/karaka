package name.pehl.tire.client;

import name.pehl.tire.client.application.ApplicationPresenter;
import name.pehl.tire.client.gin.TireGinjector;
import net.customware.gwt.presenter.client.place.PlaceManager;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public class Tire implements EntryPoint
{
    private final TireGinjector injector = GWT.create(TireGinjector.class);


    @Override
    public void onModuleLoad()
    {
        final ApplicationPresenter appPresenter = injector.getApplicationPresenter();
        appPresenter.bind();
        RootLayoutPanel.get().add(appPresenter.getDisplay().asWidget());

        PlaceManager placeManager = injector.getPlaceManager();
        placeManager.fireCurrentPlace();
    }
}
