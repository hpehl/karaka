package name.pehl.tire.client.activity.presenter;

import java.util.logging.Logger;

import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.tire.client.activity.model.ActivitiesNavigator;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Activity;
import name.pehl.tire.shared.model.TimeUnit;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;

/**
 * Presenter which shows details about the recent activities by week / month
 * 
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


        void updateActivities(Activities activities);
    }

    /**
     * Constant for the edit activity slot.
     */
    public static final Object SLOT_EditActivity = new Object();

    private static Logger logger = Logger.getLogger(RecentActivitiesPresenter.class.getName());

    private final PlaceManager placeManager;
    private final EditActivityPresenter editActivityPresenter;
    private Activities activities;
    private ActivitiesNavigator activitiesNavigator;


    @Inject
    public RecentActivitiesPresenter(final EventBus eventBus, final RecentActivitiesPresenter.MyView view,
            final PlaceManager placeManager, final EditActivityPresenter editActivityPresenter)
    {
        super(eventBus, view);
        this.placeManager = placeManager;
        this.editActivityPresenter = editActivityPresenter;

        getView().setUiHandlers(this);
        getEventBus().addHandler(ActivitiesLoadedEvent.getType(), this);
    }


    @Override
    public final void onActivitiesLoaded(ActivitiesLoadedEvent event)
    {
        activitiesNavigator = fromEvent(event);
        activities = event.getActivities();
        getView().updateActivities(activities);
    }


    private ActivitiesNavigator fromEvent(ActivitiesLoadedEvent event)
    {
        Activities activities = event.getActivities();
        if (activities != null)
        {
            TimeUnit unit = event.getUnit();
            if (unit == MONTH)
            {
                return ActivitiesNavigator.forMonth(activities.getYear(), activities.getMonth());
            }
            else if (unit == WEEK)
            {
                return ActivitiesNavigator.forWeek(activities.getYear(), activities.getWeek());
            }
        }
        return new ActivitiesNavigator();
    }


    @Override
    public void onRelative(int offset)
    {
        placeManager.revealPlace(activitiesNavigator.relative(offset).toPlaceRequest());
    }


    @Override
    public void onPrev()
    {
        placeManager.revealPlace(activitiesNavigator.decrease().toPlaceRequest());
    }


    @Override
    public void onCurrent()
    {
        placeManager.revealPlace(activitiesNavigator.current().toPlaceRequest());
    }


    @Override
    public void onNext()
    {
        placeManager.revealPlace(activitiesNavigator.increase().toPlaceRequest());
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
