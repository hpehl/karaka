package name.pehl.tire.client;

import name.pehl.tire.client.resources.Resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class TireView extends ViewImpl implements TirePresenter.MyView
{
    interface TireUi extends UiBinder<Widget, TireView>
    {
    }

    private static TireUi uiBinder = GWT.create(TireUi.class);

    @UiField
    FlowPanel mainContent;
    private final Widget widget;


    @Inject
    public TireView(Resources resources)
    {
        StyleInjector.inject(resources.backgrounds().getText(), true);
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
        mainContent.clear();
        if (content != null)
        {
            mainContent.add(content);
        }
    }
}
