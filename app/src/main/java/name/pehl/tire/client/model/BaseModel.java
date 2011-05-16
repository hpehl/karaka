package name.pehl.tire.client.model;

import name.pehl.piriti.property.client.Setter;

/**
 * @author $Author$
 * @version $Date$ $Revision: 117
 *          $
 */
public abstract class BaseModel
{
    @Setter(BaseModelIdSetter.class)
    private Long id;


    /**
     * Based on the id.
     * 
     * @return
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (id == null ? 0 : id.hashCode());
        return result;
    }


    /**
     * Based on the id.
     * 
     * @param obj
     * @return
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
        if (getClass() != obj.getClass())
        {
            return false;
        }
        BaseModel other = (BaseModel) obj;
        if (id == null)
        {
            if (other.id != null)
            {
                return false;
            }
        }
        else if (!id.equals(other.id))
        {
            return false;
        }
        return true;
    }


    /**
     * @return
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getName()).append("[").append(id).append("]");
        return builder.toString();
    }


    public Long getId()
    {
        return id;
    }


    /**
     * Necessary for the Piriti mapping.
     * 
     * @param id
     *            The id to set.
     */
    void setId(Long id)
    {
        this.id = id;
    }
}
