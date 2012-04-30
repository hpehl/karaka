package name.pehl.tire.client.application;

import name.pehl.tire.client.resources.Resources;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class MessageView extends ViewImpl implements MessagePresenter.MyView
{
    private final Widget widget;

    public interface Binder extends UiBinder<Widget, MessageView>
    {
    }

    private final Resources resources;


    @Inject
    public MessageView(final Resources resources, final Binder binder)
    {
        this.resources = resources;
        widget = binder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    @Override
    public void show(Message message)
    {
    }


    @Override
    public void hide()
    {
    }


    @Override
    public boolean isVisible()
    {
        return false;
    }
}
