package name.pehl.tire.server.project.control;

import javax.inject.Inject;

import name.pehl.tire.server.project.entity.Project;
import name.pehl.tire.server.search.DescriptiveEntityIndexSearch;
import name.pehl.tire.server.search.IndexName;
import name.pehl.tire.server.search.IndexSearch;

import com.google.appengine.api.search.Index;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ProjectIndexSearch extends DescriptiveEntityIndexSearch<Project> implements IndexSearch<Project>
{
    @Inject @IndexName("project") Index index;


    @Override
    protected Index getIndex()
    {
        return index;
    }
}
