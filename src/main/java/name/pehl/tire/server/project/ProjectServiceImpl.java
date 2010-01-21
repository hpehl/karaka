package name.pehl.tire.server.project;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import name.pehl.tire.server.persistence.TransactionTemplate;
import name.pehl.tire.shared.project.Project;

import com.google.inject.Inject;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public class ProjectServiceImpl implements ProjectService
{
    private final PersistenceManager persistenceManager;


    @Inject
    public ProjectServiceImpl(PersistenceManager persistenceManager)
    {
        this.persistenceManager = persistenceManager;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Project> list()
    {
        Query query = persistenceManager.newQuery(Project.class);
        return (List<Project>) query.execute();
    }


    @Override
    public void save(final Project project)
    {
        new TransactionTemplate(persistenceManager)
        {
            @Override
            protected void perform()
            {
                persistenceManager.makePersistent(project);
            }
        }.run();
    }
}
