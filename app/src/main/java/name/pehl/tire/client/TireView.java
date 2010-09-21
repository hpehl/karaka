package name.pehl.tire.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class TireView extends ViewImpl implements TirePresenter.View
{
    interface MainPageViewUiBinder extends UiBinder<Widget, TireView>
    {
    }

    private static MainPageViewUiBinder uiBinder = GWT.create(MainPageViewUiBinder.class);

    public final Widget widget;

    @UiField
    FlowPanel mainContentPanel;


    public TireView()
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
        if (slot == TirePresenter.TYPE_SetMainContent)
        {
            setMainContent(content);
        }
        else
        {
            super.setInSlot(slot, content);
        }
    }


    private void setMainContent(Widget content)
    {
        mainContentPanel.clear();

        if (content != null)
        {
            mainContentPanel.add(content);
        }
    }
}
