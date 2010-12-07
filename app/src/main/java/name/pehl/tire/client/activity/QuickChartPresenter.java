package name.pehl.tire.client.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

/**
 * Presenter for the quick chart showing the activites by week / month.
 * 
 * @author $Author$
 * @version $Date$ $Revision: 102
 *          $
 */
public class QuickChartPresenter extends ActivitiesNavigationPresenter<QuickChartPresenter.MyView>
{
    public interface MyView extends ActivitiesNavigationView
    {
    }


    @Inject
    public QuickChartPresenter(final EventBus eventBus, final QuickChartPresenter.MyView view,
            final DispatchAsync dispatcher, final PlaceManager placeManager)
    {
        super(eventBus, view, dispatcher, placeManager);
    }
}
