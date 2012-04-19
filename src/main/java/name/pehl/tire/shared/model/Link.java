package name.pehl.tire.shared.model;

public class Link
{
    String rel;
    String url;


    public Link()
    {
        super();
    }


    public Link(String relation, String url)
    {
        super();
        this.rel = relation;
        this.url = url;
    }


    /**
     * Base on link and relation.
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((rel == null) ? 0 : rel.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        return result;
    }


    /**
     * Base on link and relation.
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof Link))
        {
            return false;
        }
        Link other = (Link) obj;
        if (rel == null)
        {
            if (other.rel != null)
            {
                return false;
            }
        }
        else if (!rel.equals(other.rel))
        {
            return false;
        }
        if (url == null)
        {
            if (other.url != null)
            {
                return false;
            }
        }
        else if (!url.equals(other.url))
        {
            return false;
        }
        return true;
    }


    @Override
    public String toString()
    {
        return new StringBuilder("<").append(url).append(">; rel=\"").append(rel).append("\"").toString();
    }


    public String getRel()
    {
        return rel;
    }


    public void setRel(String relation)
    {
        this.rel = relation;
    }


    public String getUrl()
    {
        return url;
    }


    public void setUrl(String url)
    {
        this.url = url;
    }
}
