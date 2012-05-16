package name.pehl.tire.shared.model;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Years are sorted descending.
 * 
 * @author $Author$
 * @version $Revision$
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Years
{
    SortedSet<Year> years;


    public Years()
    {
        this.years = new TreeSet<Year>();
    }


    public Years(SortedSet<Year> years)
    {
        super();
        this.years = years;
    }


    @Override
    public String toString()
    {
        return years.toString();
    }


    public SortedSet<Year> getYears()
    {
        return years;
    }


    public void setYears(SortedSet<Year> years)
    {
        this.years = years;
    }
}
