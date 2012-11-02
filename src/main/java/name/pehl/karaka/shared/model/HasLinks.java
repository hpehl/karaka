package name.pehl.karaka.shared.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class HasLinks
{
    public static final String SELF = "self";
    public static final String PREV = "prev";
    public static final String NEXT = "next";

    List<Link> links;


    public HasLinks()
    {
        this.links = new ArrayList<Link>();
    }


    public List<Link> getLinks()
    {
        return links;
    }


    public void addLink(String rel, String url)
    {
        if (rel != null && url != null)
        {
            Link link = new Link(rel, url);
            if (!this.links.contains(link))
            {
                this.links.add(link);
            }
        }
    }


    public void setLinks(List<Link> links)
    {
        this.links = links;
    }


    public boolean hasPrev()
    {
        return findRel(PREV) != null;
    }


    public String getPrev()
    {
        Link prev = findRel(PREV);
        if (prev != null)
        {
            return prev.getUrl();
        }
        return null;
    }


    public boolean hasNext()
    {
        return findRel(NEXT) != null;
    }


    public String getNext()
    {
        Link next = findRel(NEXT);
        if (next != null)
        {
            return next.getUrl();
        }
        return null;
    }


    private Link findRel(String rel)
    {
        for (Link link : links)
        {
            if (rel.equals(link.getRel()))
            {
                return link;
            }
        }
        return null;
    }
}
