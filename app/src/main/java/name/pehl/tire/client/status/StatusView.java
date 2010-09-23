package name.pehl.tire.client.status;

import name.pehl.tire.client.status.StatusPresenter.MyView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class StatusView extends ViewImpl implements MyView
{
    interface StatusUi extends UiBinder<Widget, StatusView>
    {
    }

    private static StatusUi uiBinder = GWT.create(StatusUi.class);

    private final Widget widget;


    public StatusView()
    {
        widget = uiBinder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }
}
