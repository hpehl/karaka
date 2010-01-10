package name.pehl.tire.server.project;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import name.pehl.tire.server.persistence.TransactionTemplate;

import com.google.inject.Inject;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public class ProjectServiceImpl implements ProjectService
{
    private final EntityManager entityManager;


    @Inject
    public ProjectServiceImpl(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Project> list()
    {
        Query query = entityManager.createQuery("select from " + Project.class.getSimpleName());
        return query.getResultList();
    }


    @Override
    public void save(final Project project)
    {
        new TransactionTemplate(entityManager)
        {
            @Override
            protected void perform()
            {
                entityManager.persist(project);
            }
        }.run();
    }
}
