package name.pehl.tire.client.terms;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class TermsView extends ViewImpl implements TermsPresenter.MyView
{
    public interface Binder extends UiBinder<Widget, TermsView>
    {
    }

    private final Widget widget;


    @Inject
    public TermsView(final Binder binder)
    {
        widget = binder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }
}
