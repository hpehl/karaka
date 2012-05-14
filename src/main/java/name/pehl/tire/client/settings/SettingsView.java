package name.pehl.tire.client.settings;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class SettingsView extends ViewImpl implements SettingsPresenter.MyView
{
    public interface Binder extends UiBinder<Widget, SettingsView>
    {
    }

    private final Widget widget;


    @Inject
    public SettingsView(final Binder binder)
    {
        widget = binder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }
}
