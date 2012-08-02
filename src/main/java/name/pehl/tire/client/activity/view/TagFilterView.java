package name.pehl.tire.client.activity.view;

import java.util.List;

import name.pehl.tire.client.activity.presenter.TagFilterPresenter;
import name.pehl.tire.client.activity.presenter.TagFilterUiHandlers;
import name.pehl.tire.shared.model.Tag;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-07 16:33:54 +0100 (Di, 07. Dez 2010) $ $Revision: 102
 *          $
 */
public class TagFilterView extends ViewWithUiHandlers<TagFilterUiHandlers> implements TagFilterPresenter.MyView
{
    public interface Binder extends UiBinder<Widget, TagFilterView>
    {
    }

    private final Widget widget;


    @Inject
    public TagFilterView(final Binder binder)
    {
        this.widget = binder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    @Override
    public void refresh(List<Tag> tags)
    {
    }
}
