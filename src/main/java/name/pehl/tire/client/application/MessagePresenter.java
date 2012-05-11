package name.pehl.tire.client.application;

import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.tire.client.application.ShowMessageEvent.ShowMessageHandler;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class MessagePresenter extends PresenterWidget<MessagePresenter.MyView> implements ShowMessageHandler,
        ActivitiesLoadedHandler
{
    public interface MyView extends View
    {
        void show(Message message);
    }


    @Inject
    public MessagePresenter(final EventBus eventBus, final MyView view)
    {
        super(eventBus, view);
        getEventBus().addHandler(ShowMessageEvent.getType(), this);
        getEventBus().addHandler(ActivitiesLoadedEvent.getType(), this);
    }


    @Override
    public void onShowMessage(ShowMessageEvent event)
    {
        getView().show(event.getMessage());
    }


    @Override
    public void onActivitiesLoaded(ActivitiesLoadedEvent event)
    {
        getView().show(new Message("Activities successfully loaded for " + event.getActivities() + "."));
    }
}
