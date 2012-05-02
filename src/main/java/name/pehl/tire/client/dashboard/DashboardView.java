package name.pehl.tire.client.dashboard;

import name.pehl.tire.client.ui.UiUtils;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class DashboardView extends ViewImpl implements DashboardPresenter.MyView
{
    public interface Binder extends UiBinder<Widget, DashboardView>
    {
    }

    private final Widget widget;
    @UiField HasWidgets newActivityPanel;
    @UiField HasWidgets recentActivitiesPanel;


    @Inject
    public DashboardView(final Binder binder)
    {
        widget = binder.createAndBindUi(this);
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
