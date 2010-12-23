package name.pehl.tire.client.activity.presenter;

import java.util.logging.Logger;

import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.tire.client.activity.model.Activities;
import name.pehl.tire.client.activity.model.ActivitiesNavigationData;
import name.pehl.tire.client.activity.model.ActivitiesNavigationDataAdapter;
import name.pehl.tire.client.activity.model.Activity;
import name.pehl.tire.model.TimeUnit;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

/**
 * Presenter which shows details about the recent activities by week / month
 * 
 * @author $Author$
 * @version $Date$ $Revision: 180
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
    private final ActivitiesNavigationDataAdapter anda;
    private final EditActivityPresenter editActivityPresenter;
    private Activities currentActivities;
    private ActivitiesNavigationData currentAnd;


    @Inject
    public RecentActivitiesPresenter(final EventBus eventBus, final RecentActivitiesPresenter.MyView view,
            final PlaceManager placeManager, final EditActivityPresenter editActivityPresenter)
    {
        super(eventBus, view);
        this.placeManager = placeManager;
        this.anda = new ActivitiesNavigationDataAdapter();
        this.editActivityPresenter = editActivityPresenter;

        getView().setUiHandlers(this);
        getEventBus().addHandler(ActivitiesLoadedEvent.getType(), this);
    }


    @Override
    public final void onActivitiesLoaded(ActivitiesLoadedEvent event)
    {
        currentAnd = anda.fromEvent(event);
        currentActivities = event.getActivities();
        getView().updateActivities(currentActivities);
    }


    @Override
    public void onRelative(int offset)
    {
        placeManager.revealPlace(anda.toPlaceRequest(currentAnd.relative(offset)));
    }


    @Override
    public void onPrev()
    {
        placeManager.revealPlace(anda.toPlaceRequest(currentAnd.decrease()));
    }


    @Override
    public void onCurrent()
    {
        placeManager.revealPlace(anda.toPlaceRequest(currentAnd.current()));
    }


    @Override
    public void onNext()
    {
        placeManager.revealPlace(anda.toPlaceRequest(currentAnd.increase()));
    }


    @Override
    public void changeUnit(TimeUnit unit)
    {
        currentAnd = currentAnd.changeUnit(unit);
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
