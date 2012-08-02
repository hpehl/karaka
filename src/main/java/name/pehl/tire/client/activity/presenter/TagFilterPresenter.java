package name.pehl.tire.client.activity.presenter;

import java.util.List;

import name.pehl.tire.shared.model.Tag;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class TagFilterPresenter extends PresenterWidget<TagFilterPresenter.MyView> implements TagFilterUiHandlers
{
    public interface MyView extends View, HasUiHandlers<TagFilterUiHandlers>
    {
        void refresh(List<Tag> tags);
    }


    @Inject
    public TagFilterPresenter(final EventBus eventBus, final MyView view)
    {
        super(eventBus, view);
        getView().setUiHandlers(this);
    }


    @Override
    public void onAll()
    {
    }


    @Override
    public void onFilter(Tag tag)
    {
    }
}
