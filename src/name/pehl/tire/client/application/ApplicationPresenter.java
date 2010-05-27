package name.pehl.tire.client.application;

import name.pehl.tire.client.activity.ActivityPresenter;
import name.pehl.tire.client.project.ProjectPresenter;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.Place;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.widget.WidgetContainerPresenter;

import com.google.inject.Inject;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ApplicationPresenter extends WidgetContainerPresenter<ApplicationDisplay>
{
    @Inject
    public ApplicationPresenter(ApplicationDisplay display, EventBus eventBus, ProjectPresenter projectPresenter,
            ActivityPresenter activityPresenter)
    {
        super(display, eventBus, projectPresenter, activityPresenter);
    }


    /**
     * Empty implementation
     * 
     * @return <code>null</code>
     * @see net.customware.gwt.presenter.client.BasicPresenter#getPlace()
     */
    @Override
    public Place getPlace()
    {
        return null;
    }


    /**
     * Empty implementation
     * 
     * @param request
     * @see net.customware.gwt.presenter.client.BasicPresenter#onPlaceRequest(net.customware.gwt.presenter.client.place.PlaceRequest)
     */
    @Override
    protected void onPlaceRequest(PlaceRequest request)
    {
    }
}
