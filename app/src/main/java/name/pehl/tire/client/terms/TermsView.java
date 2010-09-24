package name.pehl.tire.client.terms;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class TermsView extends ViewImpl implements TermsPresenter.MyView
{
    interface TermsUi extends UiBinder<Widget, TermsView>
    {
    }

    private static TermsUi uiBinder = GWT.create(TermsUi.class);

    private final Widget widget;


    public TermsView()
    {
        widget = uiBinder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }
}
