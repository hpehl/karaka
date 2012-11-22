package name.pehl.karaka.client.bootstrap;

import com.google.gwt.user.client.Command;
import com.google.inject.Inject;
import name.pehl.karaka.client.tag.TagsCache;

import java.util.Iterator;

/**
 * @author Harald Pehl
 * @date 11/08/2012
 */
public class LoadTags extends BootstrapStep
{
    private final TagsCache tagsCache;

    @Inject
    public LoadTags(final TagsCache tagsCache)
    {
        this.tagsCache = tagsCache;
    }

    @Override
    public void execute(Iterator<BootstrapStep> iterator, final Command command)
    {
        tagsCache.refresh();
        next(iterator, command);
    }
}
