package name.pehl.tire.client.project;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class ProjectView extends ViewImpl implements ProjectPresenter.MyView
{
    interface ProjectUi extends UiBinder<Widget, ProjectView>
    {
    }

    private static ProjectUi uiBinder = GWT.create(ProjectUi.class);

    private final Widget widget;


    public ProjectView()
    {
        widget = uiBinder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }
}
