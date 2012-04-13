package name.pehl.tire.server.tag.control;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import biz.accelsis.taima.business.store.entity.Tag;

public class TagRepository
{
    @Inject
    EntityManager em;


    public void store(Tag tag)
    {
        em.persist(tag);
    }
}
