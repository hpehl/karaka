package name.pehl.karaka.client.application;

import java.util.Map;

import name.pehl.karaka.client.NameTokens;
import name.pehl.karaka.client.resources.Resources;
import name.pehl.karaka.client.ui.UiUtils;
import name.pehl.karaka.shared.model.User;

import com.google.common.collect.ImmutableMap;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-16 10:41:46 +0100 (Do, 16. Dez 2010) $ $Revision: 175
 *          $
 */
public class NavigationView extends ViewImpl implements NavigationPresenter.MyView
{
    public interface Binder extends UiBinder<Widget, NavigationView>
    {
    }

    private final Widget widget;
    private final Resources resources;
    private final Map<String, Hyperlink> navigationLinks;
    @UiField InlineHyperlink dashboard;
    @UiField InlineHyperlink projects;
    @UiField InlineHyperlink clients;
    @UiField InlineHyperlink tags;
    @UiField InlineHyperlink reports;
    @UiField InlineHyperlink help;
    @UiField InlineHyperlink settings;
    @UiField Anchor logout;
    @UiField SpanElement username;
    @UiField HasOneWidget messagePanel;


    @Inject
    public NavigationView(final Binder binder, final Resources resources)
    {
        this.resources = resources;
        this.resources.navigation().ensureInjected();
        this.widget = binder.createAndBindUi(this);
        this.navigationLinks = new ImmutableMap.Builder<String, Hyperlink>().put(NameTokens.dashboard, dashboard)
                .put(NameTokens.projects, projects).put(NameTokens.clients, clients).put(NameTokens.tags, tags)
                .put(NameTokens.reports, reports).put(NameTokens.help, help).put(NameTokens.settings, settings).build();
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    @Override
    public void setInSlot(Object slot, Widget content)
    {
        if (slot == NavigationPresenter.SLOT_Message)
        {
            UiUtils.setContent(messagePanel, content);
        }
        else
        {
            super.setInSlot(slot, content);
        }
    }


    @Override
    public void highlight(String token)
    {
        if (token != null)
        {
            for (Map.Entry<String, Hyperlink> entry : navigationLinks.entrySet())
            {
                String currentToken = entry.getKey();
                Hyperlink currentLink = entry.getValue();
                if (token.equals(currentToken))
                {
                    currentLink.addStyleName(resources.navigation().selectedNavigationEntry());
                }
                else
                {
                    currentLink.removeStyleName(resources.navigation().selectedNavigationEntry());
                }
            }
        }
    }


    @Override
    public void setDashboardToken(String token)
    {
        if (token != null)
        {
            dashboard.setTargetHistoryToken(token);
        }
    }


    @Override
    public void updateUser(User user)
    {
        if (user != null)
        {
            username.setInnerText(user.getUsername());
        }
    }
}
