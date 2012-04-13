package name.pehl.tire.server.tag.control;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import name.pehl.tire.server.config.Count;
import name.pehl.tire.server.config.RandomString;

import biz.accelsis.taima.business.store.entity.Tag;

public class TagProducer
{
    @Inject
    RandomString randomString;


    @Produces
    @Count
    public List<Tag> generateClients(InjectionPoint ip)
    {
        List<Tag> tags = new ArrayList<Tag>();
        Count count = ip.getAnnotated().getAnnotation(Count.class);
        if (count != null && count.value() > 0)
        {
            for (int i = 0; i < count.value(); i++)
            {
                Tag tag = new Tag("Tag " + randomString.next(5));
                tags.add(tag);
            }
        }
        return tags;
    }
}
