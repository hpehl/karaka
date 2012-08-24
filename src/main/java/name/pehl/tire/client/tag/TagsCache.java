package name.pehl.tire.client.tag;

import static java.util.logging.Level.INFO;
import name.pehl.tire.client.dispatch.TireCallback;
import name.pehl.tire.client.model.AbstractModelCache;
import name.pehl.tire.client.model.ModelCache;
import name.pehl.tire.shared.model.Tag;

import com.google.gwt.core.client.Scheduler;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;

public class TagsCache extends AbstractModelCache<Tag> implements ModelCache<Tag>
{
    @Inject
    public TagsCache(EventBus eventBus, Scheduler scheduler, DispatchAsync dispatcher)
    {
        super(eventBus, dispatcher);
    }


    @Override
    public void refresh()
    {
        logger.log(INFO, "About to refresh tags...");
        dispatcher.execute(new GetTagsAction(), new TireCallback<GetTagsResult>(eventBus)
        {
            @Override
            public void onSuccess(GetTagsResult result)
            {
                models.clear();
                models.addAll(result.getTags());
                logger.log(INFO, "Tags refreshed.");
            }
        });
    }
}
