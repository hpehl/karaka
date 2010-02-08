package name.pehl.tire.client.activity;

import name.pehl.tire.client.mvp.BasePresenter;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.Place;
import net.customware.gwt.presenter.client.place.PlaceRequest;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ActivityPresenter extends BasePresenter<ActivityDisplay>
{
    public static final Place PLACE = new Place("activity");


    public ActivityPresenter(ActivityDisplay display, EventBus eventBus)
    {
        super(display, eventBus);
    }


    @Override
    public Place getPlace()
    {
        return PLACE;
    }


    @Override
    protected void onPlaceRequest(PlaceRequest request)
    {
        // Grab the 'id' from the request and use it to get the relevant
        // activity from the server. This allows a tag of '#activity;id=23' to
        // load the activity.
        final String id = request.getParameter("id", null);
        if (id != null)
        {
            // TODO Load the activity from the server
        }
    }
}
