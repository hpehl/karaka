package name.pehl.tire.rest.activity;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import name.pehl.tire.server.model.Activity;

import com.google.common.collect.ComparisonChain;

class Day implements Comparable<Day>
{
    final int day;
    final SortedSet<Activity> activities;


    Day(int day)
    {
        this.day = day;
        this.activities = new TreeSet<Activity>();
    }


    public boolean add(Activity activity)
    {
        return activities.add(activity);
    }


    public boolean addAll(Collection<? extends Activity> c)
    {
        return activities.addAll(c);
    }


    @Override
    public int compareTo(Day that)
    {
        return ComparisonChain.start().compare(that.day, this.day).result();
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + day;
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
        Day other = (Day) obj;
        if (day != other.day)
        {
            return false;
        }
        return true;
    }


    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Day [day=").append(day).append(", activities=").append(activities).append("]");
        return builder.toString();
    }
}
