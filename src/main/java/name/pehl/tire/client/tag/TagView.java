package name.pehl.tire.client.tag;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class TagView extends ViewImpl implements TagPresenter.MyView
{
    public interface Binder extends UiBinder<Widget, TagView>
    {
    }

    private final Widget widget;


    @Inject
    public TagView(Binder binder)
    {
        widget = binder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }
}
