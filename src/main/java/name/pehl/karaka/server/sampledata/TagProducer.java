package name.pehl.karaka.server.sampledata;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Produces;

import name.pehl.karaka.server.tag.entity.Tag;

class TagProducer
{
    static String[] names = new String[] {"test", "research", "bugfixing", "review", "qa"};


    @Produces
    public List<Tag> produceTags()
    {
        List<Tag> tags = new ArrayList<Tag>();
        for (String name : names)
        {
            Tag tag = new Tag(name);
            tags.add(tag);
        }
        return tags;
    }
}
