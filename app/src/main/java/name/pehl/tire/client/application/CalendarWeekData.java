package name.pehl.tire.client.application;

import java.util.Date;

class CalendarWeekData
{
    final private int index;
    final private int minutes;
    final private Date date;


    public CalendarWeekData(int index)
    {
        this(index, 0, null);
    }


    public CalendarWeekData(int index, int minutes, Date date)
    {
        this.index = index;
        this.minutes = minutes;
        this.date = date;
    }


    public int getIndex()
    {
        return index;
    }


    public int getMinutes()
    {
        return minutes;
    }


    public Date getDate()
    {
        return date;
    }
}
