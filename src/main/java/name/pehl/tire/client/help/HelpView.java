package name.pehl.tire.client.help;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class HelpView extends ViewImpl implements HelpPresenter.MyView
{
    public interface Binder extends UiBinder<Widget, HelpView>
    {
    }

    private final Widget widget;


    @Inject
    public HelpView(Binder binder)
    {
        widget = binder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }
}
