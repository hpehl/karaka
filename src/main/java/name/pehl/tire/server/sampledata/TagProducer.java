package name.pehl.tire.server.sampledata;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import name.pehl.tire.server.tag.entity.Tag;

public class TagProducer
{
    @Inject
    IdGenerator idGenerator;

    @Inject
    RandomString randomString;


    public List<Tag> tags(int count)
    {
        List<Tag> tags = new ArrayList<Tag>();
        if (count > 0 && count < 100)
        {
            for (int i = 0; i < count; i++)
            {
                Tag tag = new Tag("Tag " + randomString.next(5));
                tag.setId(idGenerator.nextId());
                tags.add(tag);
            }
        }
        return tags;
    }
}
