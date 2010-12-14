package name.pehl.tire.rest.activity;

import java.util.Date;
import java.util.SortedSet;

import name.pehl.tire.model.Activity;

import com.google.common.collect.ComparisonChain;

class Day implements Comparable<Day>
{
    final Date date;
    SortedSet<Activity> activities;


    Day(Date date)
    {
        this.date = date;
    }


    @Override
    public int compareTo(Day that)
    {
        return ComparisonChain.start().compare(that.date, this.date).result();
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (date == null ? 0 : date.hashCode());
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
        if (date == null)
        {
            if (other.date != null)
            {
                return false;
            }
        }
        else if (!date.equals(other.date))
        {
            return false;
        }
        return true;
    }
}
