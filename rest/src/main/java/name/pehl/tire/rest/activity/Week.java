package name.pehl.tire.rest.activity;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.collect.ComparisonChain;

class Week implements Comparable<Week>
{
    final int week;
    final SortedSet<Day> days;


    Week(int week)
    {
        this.week = week;
        days = new TreeSet<Day>();
    }


    public boolean add(Day day)
    {
        return days.add(day);
    }


    public boolean addAll(Collection<? extends Day> c)
    {
        return days.addAll(c);
    }


    @Override
    public int compareTo(Week that)
    {
        return ComparisonChain.start().compare(that.week, this.week).result();
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + week;
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
        if (getClass() != obj.getClass())
        {
            return false;
        }
        Week other = (Week) obj;
        if (week != other.week)
        {
            return false;
        }
        return true;
    }


    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Week [week=").append(week).append(", days=").append(days).append("]");
        return builder.toString();
    }
}
