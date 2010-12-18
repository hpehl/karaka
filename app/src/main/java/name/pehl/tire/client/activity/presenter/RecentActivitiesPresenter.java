package name.pehl.tire.client.activity.presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

/**
 * Presenter which shows details about the recent activities by week / month
 * 
 * @author $Author$
 * @version $Date$ $Revision: 180
 *          $
 */
public class RecentActivitiesPresenter extends ActivitiesNavigationPresenter<RecentActivitiesPresenter.MyView>
{
    public interface MyView extends ActivitiesNavigationPresenter.MyView
    {
    }


    @Inject
    public RecentActivitiesPresenter(final EventBus eventBus, final RecentActivitiesPresenter.MyView view,
            final PlaceManager placeManager)
    {
        super(eventBus, view, placeManager);
    }
}
