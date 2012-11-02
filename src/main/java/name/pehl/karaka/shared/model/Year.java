package name.pehl.karaka.shared.model;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author $Author$
 * @version $Revision$
 */
public class Year implements Comparable<Year>
{
    int year;
    SortedSet<Integer> months;
    SortedSet<Integer> weeks;


    public Year()
    {
        this(0);
    }


    public Year(int year)
    {
        super();
        this.year = year;
        this.months = new TreeSet<Integer>();
        this.weeks = new TreeSet<Integer>();
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + year;
        return result;
    }


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
        if (!(obj instanceof Year))
        {
            return false;
        }
        Year other = (Year) obj;
        if (year != other.year)
        {
            return false;
        }
        return true;
    }


    @Override
    public int compareTo(Year that)
    {
        return that.year - this.year;
    }


    @Override
    public String toString()
    {
        return year + " / " + months.toString() + " / cw" + weeks.toString();
    }


    public int getYear()
    {
        return year;
    }


    public void setYear(int year)
    {
        this.year = year;
    }


    public SortedSet<Integer> getMonths()
    {
        return months;
    }


    public void setMonths(SortedSet<Integer> months)
    {
        this.months = months;
    }


    public void addMonth(int month)
    {
        this.months.add(month);
    }


    public SortedSet<Integer> getWeeks()
    {
        return weeks;
    }


    public void setWeeks(SortedSet<Integer> weeks)
    {
        this.weeks = weeks;
    }


    public void addWeek(int week)
    {
        this.weeks.add(week);
    }
}
