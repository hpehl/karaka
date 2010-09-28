package name.pehl.tire.client.application;

import name.pehl.tire.client.resources.Resources;
import name.pehl.tire.client.ui.UiUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class ApplicationView extends ViewImpl implements ApplicationPresenter.MyView
{
    interface ApplicationUi extends UiBinder<Widget, ApplicationView>
    {
    }

    private static ApplicationUi uiBinder = GWT.create(ApplicationUi.class);

    // ------------------------------------------------ panels for nested slots

    // @formatter:off
    @UiField FlowPanel navigationPanel;
    @UiField ScrollPanel mainPanel;
    @UiField FlowPanel controlPanel;
    @UiField FlowPanel chartPanel;
    // @formatter:on

    // -------------------------------------------------------- private members

    private final Widget widget;


    @Inject
    public ApplicationView(final Resources resources)
    {
        resources.common().ensureInjected();
        this.widget = uiBinder.createAndBindUi(this);
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
        else if (slot == ApplicationPresenter.SLOT_Control)
        {
            UiUtils.setContent(controlPanel, content);
        }
        else if (slot == ApplicationPresenter.SLOT_Chart)
        {
            UiUtils.setContent(chartPanel, content);
        }
        else
        {
            super.setInSlot(slot, content);
        }
    }
}