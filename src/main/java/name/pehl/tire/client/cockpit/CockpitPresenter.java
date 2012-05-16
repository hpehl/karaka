package name.pehl.tire.client.cockpit;

import java.util.logging.Logger;

import name.pehl.tire.client.activity.event.GetActivitiesAction;
import name.pehl.tire.client.activity.event.GetActivitiesResult;
import name.pehl.tire.client.activity.model.ActivitiesRequest;
import name.pehl.tire.client.dispatch.TireCallback;
import name.pehl.tire.client.rest.UrlBuilder;
import name.pehl.tire.shared.model.Activities;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

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


        void updateMonth(Activities activities);


        void updateWeek(Activities activities);


        void updateToday(Activities activities);
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
        Scheduler.get().scheduleDeferred(new InitCockpitCommand());
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

    class InitCockpitCommand implements ScheduledCommand
    {
        @Override
        public void execute()
        {
            final ActivitiesRequest currentMonth = new ActivitiesRequest(new UrlBuilder().module("rest")
                    .path("activities", "currentMonth").toUrl());
            dispatcher.execute(new GetActivitiesAction(currentMonth), new TireCallback<GetActivitiesResult>(
                    getEventBus())
            {
                @Override
                public void onSuccess(GetActivitiesResult result)
                {
                    getView().updateMonth(result.getActivities());
                }


                @Override
                public void onFailure(Throwable caught)
                {
                    logger.warning("Cannot load activities for " + currentMonth);
                }
            });

            final ActivitiesRequest currentWeek = new ActivitiesRequest(new UrlBuilder().module("rest")
                    .path("activities", "currentWeek").toUrl());
            dispatcher.execute(new GetActivitiesAction(currentWeek), new TireCallback<GetActivitiesResult>(
                    getEventBus())
            {
                @Override
                public void onSuccess(GetActivitiesResult result)
                {
                    getView().updateWeek(result.getActivities());
                }


                @Override
                public void onFailure(Throwable caught)
                {
                    logger.warning("Cannot load activities for " + currentWeek);
                }
            });

            final ActivitiesRequest today = new ActivitiesRequest(new UrlBuilder().module("rest")
                    .path("activities", "today").toUrl());
            dispatcher.execute(new GetActivitiesAction(today), new TireCallback<GetActivitiesResult>(getEventBus())
            {
                @Override
                public void onSuccess(GetActivitiesResult result)
                {
                    getView().updateToday(result.getActivities());
                }


                @Override
                public void onFailure(Throwable caught)
                {
                    logger.warning("Cannot load activities for " + today);
                }
            });
        }
    }
}
