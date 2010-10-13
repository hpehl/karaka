package name.pehl.tire.client.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

class CalendarWeekData implements Iterable<Integer>
{
    final private Date start;
    final private Date end;
    final private int calendarWeek;
    final private List<Integer> minutes;


    public CalendarWeekData(Date start, Date end, int calendarWeek, int... minutes)
    {
        this.start = start;
        this.end = end;
        this.calendarWeek = calendarWeek;
        this.minutes = new ArrayList<Integer>();
        if (minutes != null)
        {
            for (int m : minutes)
            {
                this.minutes.add(m);
            }
        }
    }


    @Override
    public Iterator<Integer> iterator()
    {
        return minutes.iterator();
    }


    public void add(int index, int minutes)
    {
        if (index >= 0 && index < this.minutes.size())
        {
            int m = this.minutes.get(index);
            m += minutes;
            this.minutes.set(index, m);
        }
    }


    public Date getStart()
    {
        return start;
    }


    public Date getEnd()
    {
        return end;
    }


    public int getCalendarWeek()
    {
        return calendarWeek;
    }


    public List<Integer> getMinutes()
    {
        return minutes;
    }
}
