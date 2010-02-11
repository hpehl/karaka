package name.pehl.tire.client.application;

import name.pehl.tire.client.activity.ActivityPresenter;
import name.pehl.tire.client.project.ProjectPresenter;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.Place;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.widget.DeckDisplay;
import net.customware.gwt.presenter.client.widget.DeckPresenter;

import com.google.inject.Inject;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ApplicationPresenter extends DeckPresenter
{
    /**
     * Construct a new instance of this class
     * 
     * @param display
     * @param eventBus
     * @param presenters
     */
    @Inject
    public ApplicationPresenter(DeckDisplay display, EventBus eventBus, ProjectPresenter projectPresenter,
            ActivityPresenter activityPresenter)
    {
        super(display, eventBus, projectPresenter, activityPresenter);
    }


    /**
     * @return
     * @see net.customware.gwt.presenter.client.BasicPresenter#getPlace()
     */
    @Override
    public Place getPlace()
    {
        return null;
    }


    /**
     * @param request
     * @see net.customware.gwt.presenter.client.BasicPresenter#onPlaceRequest(net.customware.gwt.presenter.client.place.PlaceRequest)
     */
    @Override
    protected void onPlaceRequest(PlaceRequest request)
    {
    }
}
