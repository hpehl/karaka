package name.pehl.karaka.shared.model;

import name.pehl.piriti.commons.client.Transient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class HasLinks
{
    public static final String SELF = "self";
    public static final String PREV = "prev";
    public static final String NEXT = "next";
    /**
     * The links are uni-directional: On the server side the links are encodes into the response header.
     * On the client side they are parsed and then assigned to this field.
     */
    @Transient List<Link> links;


    public HasLinks()
    {
        this.links = new ArrayList<Link>();
    }

    public void setLinks(List<Link> links)
    {
        this.links = links;
    }

    public boolean hasPrev()
    {
        return findRel(PREV) != null;
    }

    public boolean hasNext()
    {
        return findRel(NEXT) != null;
    }

    public String get(String rel)
    {
        Link link = findRel(rel);
        return link != null ? link.getUrl() : null;
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
