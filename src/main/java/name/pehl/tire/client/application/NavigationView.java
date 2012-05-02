package name.pehl.tire.client.application;

import name.pehl.tire.client.resources.Resources;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.Label;
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
    private final InlineHyperlink[] navigationLinks;
    @UiField InlineHyperlink dashboard;
    @UiField InlineHyperlink projects;
    @UiField InlineHyperlink clients;
    @UiField InlineHyperlink tags;
    @UiField InlineHyperlink reports;
    @UiField InlineHyperlink help;
    @UiField InlineHyperlink settings;
    @UiField Label messageHolder;


    @Inject
    public NavigationView(final Binder binder, final Resources resources)
    {
        this.resources = resources;
        this.resources.navigation().ensureInjected();
        this.widget = binder.createAndBindUi(this);
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
                    link.addStyleName(resources.navigation().selectedNavigationEntry());
                }
                else
                {
                    link.removeStyleName(resources.navigation().selectedNavigationEntry());
                }
            }
        }
    }


    @Override
    public void showMessage(Message message)
    {
        messageHolder.setText(message.getText());
    }
}
