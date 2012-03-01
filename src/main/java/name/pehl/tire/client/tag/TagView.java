package name.pehl.tire.client.tag;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class TagView extends ViewImpl implements TagPresenter.MyView
{
    interface TagUi extends UiBinder<Widget, TagView>
    {
    }

    private static TagUi uiBinder = GWT.create(TagUi.class);

    private final Widget widget;


    public TagView()
    {
        widget = uiBinder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }
}
