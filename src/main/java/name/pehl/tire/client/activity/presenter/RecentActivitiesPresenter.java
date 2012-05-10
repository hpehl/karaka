package name.pehl.tire.client.activity.presenter;

import java.util.logging.Logger;

import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.tire.client.activity.event.GetYearsAction;
import name.pehl.tire.client.activity.event.GetYearsResult;
import name.pehl.tire.client.activity.model.ActivitiesNavigator;
import name.pehl.tire.client.dispatch.TireCallback;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Activity;
import name.pehl.tire.shared.model.TimeUnit;
import name.pehl.tire.shared.model.Years;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

/**
 * Presenter which shows details about the recent activities by week / month
 * 
 * @todo Enable / disable previous / next links based on the links in the
 *       current activities
 * @todo Add popups for year / month / week selection
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-23 16:27:42 +0100 (Do, 23. Dez 2010) $ $Revision: 180
 *          $
 */
public class RecentActivitiesPresenter extends PresenterWidget<RecentActivitiesPresenter.MyView> implements
        RecentActivitiesUiHandlers, ActivitiesLoadedHandler
{
    public interface MyView extends View, HasUiHandlers<RecentActivitiesUiHandlers>
    {
        void mask();


        void unmask();


        void updateYears(Years years);


        void updateActivities(Activities activities);
    }

    private static Logger logger = Logger.getLogger(RecentActivitiesPresenter.class.getName());

    private final DispatchAsync dispatcher;
    private final PlaceManager placeManager;
    private final EditActivityPresenter editActivityPresenter;
    private Activities activities;
    private ActivitiesNavigator activitiesNavigator;


    @Inject
    public RecentActivitiesPresenter(final DispatchAsync dispatcher, final EventBus eventBus,
            final RecentActivitiesPresenter.MyView view, final PlaceManager placeManager,
            final EditActivityPresenter editActivityPresenter)
    {
        super(eventBus, view);
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        this.editActivityPresenter = editActivityPresenter;
        this.activitiesNavigator = new ActivitiesNavigator();
        getView().setUiHandlers(this);
        getEventBus().addHandler(ActivitiesLoadedEvent.getType(), this);
    }


    @Override
    protected void onReset()
    {
        super.onReset();
        dispatcher.execute(new GetYearsAction(), new TireCallback<GetYearsResult>(getEventBus())
        {
            @Override
            public void onSuccess(GetYearsResult result)
            {
                Years years = result.getYears();
                getView().updateYears(years);
            }
        });
    }


    @Override
    public final void onActivitiesLoaded(ActivitiesLoadedEvent event)
    {
        activitiesNavigator = ActivitiesNavigator.fromEvent(event);
        activities = event.getActivities();
        getView().updateActivities(activities);
    }


    @Override
    public void onGoto(int year, int monthOrWeek, TimeUnit unit)
    {
        activitiesNavigator = activitiesNavigator.goTo(year, monthOrWeek, unit);
        placeManager.revealPlace(activitiesNavigator.toPlaceRequest());
    }


    @Override
    public void onPrev()
    {
        activitiesNavigator = activitiesNavigator.decrease();
        placeManager.revealPlace(activitiesNavigator.toPlaceRequest());
    }


    @Override
    public void onCurrent()
    {
        activitiesNavigator = activitiesNavigator.current();
        placeManager.revealPlace(activitiesNavigator.toPlaceRequest());
    }


    @Override
    public void onNext()
    {
        activitiesNavigator = activitiesNavigator.increase();
        placeManager.revealPlace(activitiesNavigator.toPlaceRequest());
    }


    @Override
    public void changeUnit(TimeUnit unit)
    {
        activitiesNavigator = activitiesNavigator.changeUnit(unit);
    }


    @Override
    public void onEdit(int rowIndex, Activity activity)
    {
        logger.fine("Edit " + activity + " in row #" + rowIndex);
        editActivityPresenter.getView().setActivity(activity);
        addToPopupSlot(editActivityPresenter);
    }


    @Override
    public void onCopy(int rowIndex, Activity activity)
    {
        logger.fine("Copy " + activity + " in row #" + rowIndex);
    }


    @Override
    public void onGoon(int rowIndex, Activity activity)
    {
        logger.fine("Continue " + activity + " in row #" + rowIndex);
    }


    @Override
    public void onDelete(int rowIndex, Activity activity)
    {
        logger.fine("Delete " + activity + " in row #" + rowIndex);
    }
}
