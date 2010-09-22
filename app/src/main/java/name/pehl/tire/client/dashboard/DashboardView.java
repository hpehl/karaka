package name.pehl.tire.client.dashboard;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class DashboardView extends ViewImpl implements DashboardPresenter.MyView
{
    interface DashboardUi extends UiBinder<Widget, DashboardView>
    {
    }

    private static DashboardUi uiBinder = GWT.create(DashboardUi.class);

    private final Widget widget;


    public DashboardView()
    {
        widget = uiBinder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }
}
