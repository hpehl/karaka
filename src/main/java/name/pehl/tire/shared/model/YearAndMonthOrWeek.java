package name.pehl.tire.shared.model;

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
