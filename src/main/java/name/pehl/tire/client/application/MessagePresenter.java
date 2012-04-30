package name.pehl.tire.client.application;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class MessagePresenter extends PresenterWidget<MessagePresenter.MyView>
{
    public interface MyView extends View
    {
        void show(Message message);


        void hide();


        boolean isVisible();
    }


    @Inject
    public MessagePresenter(final EventBus eventBus, final MyView view)
    {
        super(eventBus, view);
    }


    @Override
    protected void onBind()
    {
        super.onBind();
    }
}
