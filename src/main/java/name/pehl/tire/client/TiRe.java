package name.pehl.tire.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public class TiRe implements EntryPoint
{
    private final TireGinjector injector = GWT.create(TireGinjector.class);


    @Override
    public void onModuleLoad()
    {
        final AppPresenter appPresenter = injector.getAppPresenter();
        appPresenter.go(RootPanel.get());
        injector.getPlaceManager().fireCurrentPlace();
    }
}
