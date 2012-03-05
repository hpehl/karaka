package name.pehl.tire.client.dashboard;

import name.pehl.tire.client.ui.UiUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class DashboardView extends ViewImpl implements DashboardPresenter.MyView
{
    interface DashboardUi extends UiBinder<Widget, DashboardView>
    {
    }

    private static DashboardUi uiBinder = GWT.create(DashboardUi.class);

    @UiField
    FlowPanel newActivityPanel;

    @UiField
    FlowPanel recentActivitiesPanel;

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


    @Override
    public void setInSlot(Object slot, Widget content)
    {
        if (slot == DashboardPresenter.SLOT_NewActivity)
        {
            UiUtils.setContent(newActivityPanel, content);
        }
        else if (slot == DashboardPresenter.SLOT_RecentActivities)
        {
            UiUtils.setContent(recentActivitiesPanel, content);
        }
    }
}