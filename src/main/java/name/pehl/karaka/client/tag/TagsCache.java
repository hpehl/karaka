package name.pehl.karaka.client.tag;

import com.google.gwt.core.client.Scheduler;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import name.pehl.karaka.client.dispatch.KarakaCallback;
import name.pehl.karaka.client.model.AbstractModelCache;
import name.pehl.karaka.client.model.ModelCache;
import name.pehl.karaka.client.tag.RefreshTagsEvent.RefreshTagsHandler;
import name.pehl.karaka.shared.model.Tag;
import org.fusesource.restygwt.client.FailedStatusCodeException;

import static name.pehl.karaka.client.logging.Logger.Category.cache;
import static name.pehl.karaka.client.logging.Logger.info;
import static name.pehl.karaka.client.logging.Logger.warn;

public class TagsCache extends AbstractModelCache<Tag> implements ModelCache<Tag>, RefreshTagsHandler
{
    @Inject
    public TagsCache(EventBus eventBus, Scheduler scheduler, DispatchAsync dispatcher)
    {
        super(eventBus, dispatcher);
        eventBus.addHandler(RefreshTagsEvent.getType(), this);
    }


    @Override
    public void refresh()
    {
        info(cache, "About to refresh tags...");
        dispatcher.execute(new GetTagsAction(), new KarakaCallback<GetTagsResult>(eventBus)
        {
            @Override
            public void onSuccess(GetTagsResult result)
            {
                models.clear();
                models.addAll(result.getTags());
                info(cache, "Tags refreshed.");
            }

            @Override
            public void onNotFound(final FailedStatusCodeException caught)
            {
                warn(cache, "No tags found.");
            }
        });
    }


    @Override
    public void onRefreshTags(RefreshTagsEvent event)
    {
        refresh();
    }
}
