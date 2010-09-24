package name.pehl.tire.client.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class ClientView extends ViewImpl implements ClientPresenter.MyView
{
    interface ClientUi extends UiBinder<Widget, ClientView>
    {
    }

    private static ClientUi uiBinder = GWT.create(ClientUi.class);

    private final Widget widget;


    public ClientView()
    {
        widget = uiBinder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }
}
