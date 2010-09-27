package name.pehl.tire.client.application;

import name.pehl.tire.client.resources.Resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * @author $Author$
 * @version $Date$ $Revision$
 */
public class NavigationView extends ViewImpl implements NavigationPresenter.MyView
{
    interface NavigationUi extends UiBinder<Widget, NavigationView>
    {
    }

    private static NavigationUi uiBinder = GWT.create(NavigationUi.class);

    // @formatter:off
    @UiField InlineHyperlink dashboard;
    @UiField InlineHyperlink projects;
    @UiField InlineHyperlink clients;
    @UiField InlineHyperlink tags;
    @UiField InlineHyperlink reports;
    @UiField InlineHyperlink help;
    @UiField InlineHyperlink settings;
    private final InlineHyperlink[] navigationLinks;
    // @formatter:on

    private final Widget widget;
    private final Resources resources;


    @Inject
    public NavigationView(final Resources resources)
    {
        this.resources = resources;
        this.resources.navigation().ensureInjected();
        this.widget = uiBinder.createAndBindUi(this);
        this.navigationLinks = new InlineHyperlink[] {dashboard, projects, clients, tags, reports, help, settings,};
    }


    @Override
    public Widget asWidget()
    {
        return widget;
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
