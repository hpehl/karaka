package name.pehl.tire.server.sampledata;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import name.pehl.tire.server.tag.entity.Tag;

class TagProducer
{
    @Inject IdGenerator idGenerator;

    @Inject RandomString randomString;


    @Count
    @Produces
    public List<Tag> produceTags(InjectionPoint ip)
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
