package name.pehl.tire.client.activity.presenter;

import name.pehl.tire.client.activity.event.ActivitiesChangedEvent;
import name.pehl.tire.client.activity.event.ActivitiesChangedEvent.ActivitiesChangedHandler;
import name.pehl.tire.shared.model.Activities;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * Presenter for the quick chart showing the activites by week / month.
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-22 16:43:49 +0100 (Mi, 22. Dez 2010) $ $Revision: 102
 *          $
 */
public class QuickChartPresenter extends PresenterWidget<QuickChartPresenter.MyView> implements
        ActivitiesChangedHandler
{
    public interface MyView extends View
    {
        void updateActivities(Activities activities);
    }


    @Inject
    public QuickChartPresenter(final EventBus eventBus, final QuickChartPresenter.MyView view)
    {
        super(eventBus, view);
        getEventBus().addHandler(ActivitiesChangedEvent.getType(), this);
    }


    @Override
    public final void onActivitiesChanged(ActivitiesChangedEvent event)
    {
        getView().updateActivities(event.getActivities());
    }
}
