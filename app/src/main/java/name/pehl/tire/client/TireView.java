package name.pehl.tire.client;

import name.pehl.tire.client.resources.Resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
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
    ScrollPanel mainPanel;

    @UiField
    FlowPanel statusPanel;

    @UiField
    InlineHyperlink dashboard;

    @UiField
    InlineHyperlink projects;

    @UiField
    InlineHyperlink clients;

    @UiField
    InlineHyperlink tags;

    @UiField
    InlineHyperlink reports;

    private final Widget widget;
    private final Resources resources;
    private final InlineHyperlink[] navigationLinks;


    @Inject
    public TireView(Resources resources)
    {
        this.widget = uiBinder.createAndBindUi(this);
        this.resources = resources;
        StyleInjector.inject(resources.navigation().getText(), true);
        this.navigationLinks = new InlineHyperlink[] {dashboard, projects, clients, tags, reports};
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
            setContent(mainPanel, content);
        }
        else if (slot == TirePresenter.SLOT_StatusContent)
        {
            setContent(statusPanel, content);
        }
        else
        {
            super.setInSlot(slot, content);
        }
    }


    private void setContent(Panel container, Widget content)
    {
        container.clear();
        if (content != null)
        {
            container.add(content);
        }
    }


    @Override
    public void highlight(String token)
    {
        if (token != null)
        {
            for (InlineHyperlink link : navigationLinks)
            {
                if (token.equals(link.getTargetHistoryToken()))
                {
                    GWT.log(token + " selected");
                    link.addStyleName(resources.navigation().selected());
                }
                else
                {
                    link.removeStyleName(resources.navigation().selected());
                }
            }
        }
    }
}
