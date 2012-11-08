package name.pehl.karaka.client.application;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import name.pehl.karaka.client.resources.Resources;
import name.pehl.karaka.client.ui.UiUtils;

public class ApplicationView extends ViewImpl implements ApplicationPresenter.MyView
{
    public interface Binder extends UiBinder<Widget, ApplicationView>
    {
    }

    private final Widget widget;
    @UiField HasWidgets navigationPanel;
    @UiField HasWidgets mainPanel;
    @UiField HasWidgets cockpitPanel;
    @UiField HasWidgets quickChartPanel;


    @Inject
    public ApplicationView(final Binder binder, final Resources resources)
    {
        resources.karaka().ensureInjected();
        resources.widgets().ensureInjected();
        this.widget = binder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    @Override
    public void setInSlot(Object slot, Widget content)
    {
        if (slot == ApplicationPresenter.TYPE_SetMainContent)
        {
            UiUtils.setContent(mainPanel, content);
        }
        else if (slot == ApplicationPresenter.SLOT_Navigation)
        {
            UiUtils.setContent(navigationPanel, content);
        }
        else if (slot == ApplicationPresenter.SLOT_Cockpit)
        {
            UiUtils.setContent(cockpitPanel, content);
        }
        else if (slot == ApplicationPresenter.SLOT_QuickChart)
        {
            UiUtils.setContent(quickChartPanel, content);
        }
        else
        {
            super.setInSlot(slot, content);
        }
    }
}
