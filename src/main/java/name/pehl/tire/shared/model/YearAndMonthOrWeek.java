package name.pehl.tire.shared.model;

import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;

/**
 * Data holder for the selection of a year and a month or week.
 * 
 * @author $Author$
 * @version $Revision$
 */
public class YearAndMonthOrWeek
{
    int year;
    int monthOrWeek;
    TimeUnit unit;


    public YearAndMonthOrWeek()
    {
        super();
        this.unit = WEEK;
    }


    @Override
    public String toString()
    {
        if (year == 0 && monthOrWeek == 0)
        {
            if (unit == MONTH)
            {
                return "current month";
            }
            else if (unit == WEEK)
            {
                return "current week";
            }
            else
            {
                return "undefined";
            }
        }
        else
        {
            if (unit == MONTH)
            {
                return monthOrWeek + " / " + year;
            }
            else if (unit == WEEK)
            {
                return "CW " + monthOrWeek + " / " + year;
            }
            else
            {
                return "undefined";
            }
        }
    }


    public int getYear()
    {
        return year;
    }


    public void setYear(int year)
    {
        this.year = year;
    }


    public int getMonthOrWeek()
    {
        return monthOrWeek;
    }


    public void setMonthOrWeek(int monthOrWeek)
    {
        this.monthOrWeek = monthOrWeek;
    }


    public TimeUnit getUnit()
    {
        return unit;
    }


    public void setUnit(TimeUnit unit)
    {
        this.unit = unit;
    }
}
