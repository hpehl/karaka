package name.pehl.tire.client.application;

import static java.util.logging.Level.INFO;
import name.pehl.tire.client.activity.event.ActivitiesChangedEvent;
import name.pehl.tire.client.activity.event.ActivitiesChangedEvent.ActivitiesChangedHandler;
import name.pehl.tire.client.application.ShowMessageEvent.ShowMessageHandler;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class MessagePresenter extends PresenterWidget<MessagePresenter.MyView> implements ShowMessageHandler,
        ActivitiesChangedHandler
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
        getEventBus().addHandler(ActivitiesChangedEvent.getType(), this);
    }


    @Override
    public void onShowMessage(ShowMessageEvent event)
    {
        getView().show(event.getMessage());
    }


    @Override
    public void onActivitiesChanged(ActivitiesChangedEvent event)
    {
        if (!event.isSilent())
        {
            getView()
                    .show(new Message(INFO, "Activities successfully loaded for " + event.getActivities() + ".", true));
        }
    }
}
