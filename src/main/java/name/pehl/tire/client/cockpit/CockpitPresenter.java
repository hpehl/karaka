package name.pehl.tire.client.cockpit;

import java.util.logging.Logger;

import name.pehl.tire.client.activity.event.GetActivitiesAction;
import name.pehl.tire.client.activity.event.GetActivitiesResult;
import name.pehl.tire.client.activity.model.ActivitiesRequest;
import name.pehl.tire.client.dispatch.TireCallback;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.YearAndMonthOrWeek;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import static name.pehl.tire.shared.model.TimeUnit.MONTH;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-06 17:48:50 +0100 (Mo, 06. Dez 2010) $ $Revision: 175
 *          $
 */
public class CockpitPresenter extends PresenterWidget<CockpitPresenter.MyView> implements CockpitUiHandlers
{
    public interface MyView extends View, HasUiHandlers<CockpitUiHandlers>
    {
        void initializeRecording(boolean recording);


        void updateTimes(Activities activities);
    }

    private static final Logger logger = Logger.getLogger(CockpitPresenter.class.getName());

    private final DispatchAsync dispatcher;


    @Inject
    public CockpitPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher)
    {
        super(eventBus, view);
        this.dispatcher = dispatcher;
        getView().setUiHandlers(this);
        getView().initializeRecording(false);
    }


    @Override
    protected void onReveal()
    {
        super.onReveal();
        YearAndMonthOrWeek yearAndMonthOrWeek = new YearAndMonthOrWeek();
        yearAndMonthOrWeek.setUnit(MONTH);
        final ActivitiesRequest activitiesRequest = new ActivitiesRequest(yearAndMonthOrWeek);
        dispatcher.execute(new GetActivitiesAction(activitiesRequest), new TireCallback<GetActivitiesResult>(
                getEventBus())
        {
            @Override
            public void onSuccess(GetActivitiesResult result)
            {
                Activities activities = result.getActivities();
                if (activities != null)
                {
                    getView().updateTimes(activities);
                }
            }


            @Override
            public void onFailure(Throwable caught)
            {
                logger.severe("Cannot load activities for " + activitiesRequest);
            }
        });
    }


    @Override
    public void onStartRecording()
    {
        logger.info("Start recording");
    }


    @Override
    public void onStopRecording()
    {
        logger.info("Stop recording");
    }
}
