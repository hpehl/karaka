package name.pehl.tire.client.project;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class ProjectView extends ViewImpl implements ProjectPresenter.MyView
{
    public interface Binder extends UiBinder<Widget, ProjectView>
    {
    }

    private final Widget widget;


    @Inject
    public ProjectView(final Binder binder)
    {
        widget = binder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }
}
