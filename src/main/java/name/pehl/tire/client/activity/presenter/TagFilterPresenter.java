package name.pehl.tire.client.activity.presenter;

import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Activity;
import name.pehl.tire.shared.model.Tag;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class TagFilterPresenter extends PresenterWidget<TagFilterPresenter.MyView> implements TagFilterUiHandlers,
        ActivitiesLoadedHandler
{
    public interface MyView extends View, HasUiHandlers<TagFilterUiHandlers>
    {
        void refresh(Multiset<Tag> tags);
    }


    @Inject
    public TagFilterPresenter(final EventBus eventBus, final MyView view)
    {
        super(eventBus, view);
        getView().setUiHandlers(this);
        getEventBus().addHandler(ActivitiesLoadedEvent.getType(), this);
    }


    @Override
    public void onAll()
    {
    }


    @Override
    public void onFilter(Tag tag)
    {
    }


    @Override
    public void onActivitiesLoaded(ActivitiesLoadedEvent event)
    {
        Multiset<Tag> tags = HashMultiset.create();
        Activities activities = event.getActivities();
        for (Activity activity : activities.activities())
        {
            tags.addAll(activity.getTags());
        }
        getView().refresh(tags);
    }
}
