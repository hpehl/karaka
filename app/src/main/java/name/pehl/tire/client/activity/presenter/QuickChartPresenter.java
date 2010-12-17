package name.pehl.tire.client.activity.presenter;

import name.pehl.tire.client.NameTokens;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.event.GetActivitiesAction;
import name.pehl.tire.client.activity.event.GetActivitiesResult;
import name.pehl.tire.client.activity.model.Activities;
import name.pehl.tire.client.activity.model.ActivitiesNavigationData;
import name.pehl.tire.client.activity.view.ActivitiesNavigationView;
import name.pehl.tire.client.dispatch.TireCallback;

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

    private final DispatchAsync dispatcher;


    @Inject
    public QuickChartPresenter(final EventBus eventBus, final QuickChartPresenter.MyView view,
            final DispatchAsync dispatcher, final PlaceManager placeManager)
    {
        super(eventBus, view, placeManager);
        this.dispatcher = dispatcher;
    }


    @Override
    protected void onReveal()
    {
        if (!dashboardVisible())
        {
            if (currentAnd == null)
            {
                currentAnd = new ActivitiesNavigationData();
            }
            loadActivities(currentAnd);
        }
    }


    private boolean dashboardVisible()
    {
        return NameTokens.dashboard.equals(placeManager.getCurrentPlaceRequest().getNameToken());
    }


    private void loadActivities(final ActivitiesNavigationData and)
    {
        dispatcher.execute(new GetActivitiesAction(and), new TireCallback<GetActivitiesResult>(placeManager)
        {
            @Override
            public void onSuccess(GetActivitiesResult result)
            {
                Activities activities = result.getActivities();
                if (activities != null)
                {
                    ActivitiesLoadedEvent.fire(QuickChartPresenter.this, activities, and.getUnit());
                }
            }
        });
    }


    @Override
    public void onPrev()
    {
        if (dashboardVisible())
        {
            super.onPrev();
        }
        else
        {
            loadActivities(currentAnd.decrease());
        }
    }


    @Override
    public void onCurrent()
    {
        if (dashboardVisible())
        {
            super.onCurrent();
        }
        else
        {
            loadActivities(currentAnd.current());
        }
    }


    @Override
    public void onNext()
    {
        if (dashboardVisible())
        {
            super.onNext();
        }
        else
        {
            loadActivities(currentAnd.increase());
        }
    }
}
