package name.pehl.karaka.client.bootstrap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import name.pehl.karaka.client.tag.TagsCache;

import static java.lang.Boolean.TRUE;

/**
 * @author Harald Pehl
 * @date 11/08/2012
 */
public class LoadTags implements BootstrapCommand<Boolean>
{
    private final TagsCache tagsCache;

    @Inject
    public LoadTags(final TagsCache tagsCache)
    {
        this.tagsCache = tagsCache;
    }

    @Override
    public void execute(final AsyncCallback<Boolean> callback)
    {
        tagsCache.refresh();
        callback.onSuccess(TRUE);
    }
}
