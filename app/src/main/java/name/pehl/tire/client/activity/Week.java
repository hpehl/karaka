package name.pehl.tire.client.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import name.pehl.piriti.client.json.JsonField;
import name.pehl.piriti.client.json.JsonReader;

import com.google.gwt.core.client.GWT;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class Week implements Iterable<Day>
{
    // @formatter:off
    interface WeekReader extends JsonReader<Week> {}
    public static final WeekReader JSON_READER = GWT.create(WeekReader.class);
    
    @JsonField int calendarWeek;
    @JsonField List<Day> days;
    // @formatter:on

    /**
     * Construct a new instance of this class
     */
    public Week()
    {
        days = new ArrayList<Day>();
    }


    /**
     * Based on calendarWeek.
     * 
     * @return
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + calendarWeek;
        return result;
    }


    /**
     * Based on calendarWeek.
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
        Week other = (Week) obj;
        if (calendarWeek != other.calendarWeek)
        {
            return false;
        }
        return true;
    }


    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("Week [").append(calendarWeek).append(", ").append(days).append("]");
        return builder.toString();
    }


    @Override
    public Iterator<Day> iterator()
    {
        return days.iterator();
    }


    public boolean isEmpty()
    {
        return days.isEmpty();
    }


    public Day getStart()
    {
        if (!days.isEmpty())
        {
            return days.get(0);
        }
        return null;
    }


    public Day getEnd()
    {
        if (!days.isEmpty())
        {
            return days.get(days.size() - 1);
        }
        return null;
    }


    public void addDay(Day day)
    {
        days.add(day);
    }


    public int getMinutes()
    {
        int minutes = 0;
        for (Day day : this)
        {
            minutes += day.getMinutes();
        }
        return minutes;
    }


    public int getCalendarWeek()
    {
        return calendarWeek;
    }


    public void setCalendarWeek(int calendarWeek)
    {
        this.calendarWeek = calendarWeek;
    }
}
