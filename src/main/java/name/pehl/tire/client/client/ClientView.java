package name.pehl.tire.client.client;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class ClientView extends ViewImpl implements ClientPresenter.MyView
{
    public interface Binder extends UiBinder<Widget, ClientView>
    {
    }

    private final Widget widget;


    @Inject
    public ClientView(final Binder binder)
    {
        widget = binder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }
}
