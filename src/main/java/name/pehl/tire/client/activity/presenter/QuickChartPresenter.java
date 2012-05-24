package name.pehl.tire.client.activity.presenter;

import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.tire.shared.model.Activities;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * Presenter for the quick chart showing the activites by week / month.
 * <p>
 * Ideas
 * </p>
 * <ul>
 * <li>When unit == month and clicking on a week, switch / animate to this week.
 * <li>When unit == week and clicking on a day, switch / animate to this day.
 * <li>Provide links to the parent level
 * <li>Provide previous / next links to go to the previous / next day / week
 * </ul>
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-22 16:43:49 +0100 (Mi, 22. Dez 2010) $ $Revision: 102
 *          $
 */
public class QuickChartPresenter extends PresenterWidget<QuickChartPresenter.MyView> implements ActivitiesLoadedHandler
{
    public interface MyView extends View
    {
        void updateActivities(Activities activities);
    }


    @Inject
    public QuickChartPresenter(final EventBus eventBus, final QuickChartPresenter.MyView view)
    {
        super(eventBus, view);
        getEventBus().addHandler(ActivitiesLoadedEvent.getType(), this);
    }


    @Override
    public final void onActivitiesLoaded(ActivitiesLoadedEvent event)
    {
        getView().updateActivities(event.getActivities());
    }
}
