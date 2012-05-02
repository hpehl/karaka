package name.pehl.tire.client.about;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class AboutView extends ViewImpl implements AboutPresenter.MyView
{
    public interface Binder extends UiBinder<Widget, AboutView>
    {
    }

    private final Widget widget;


    @Inject
    public AboutView(final Binder binder)
    {
        widget = binder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }
}
