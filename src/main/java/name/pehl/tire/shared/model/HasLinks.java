package name.pehl.tire.shared.model;

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
}
