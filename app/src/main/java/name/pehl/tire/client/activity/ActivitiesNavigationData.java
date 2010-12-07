package name.pehl.tire.client.activity;

/**
 * Simple data holder for navigation over activities by week / month. Month and
 * week are <b>not</b> synced!
 * 
 * @author $Author$
 * @version $Date$ $Revision: 180
 *          $
 */
public class ActivitiesNavigationData
{
    private int year;
    private int month;
    private int week;


    /**
     * Construct a new instance of this class
     * 
     * @param year
     * @param month
     * @param week
     */
    public ActivitiesNavigationData(int year, int month, int week)
    {
        super();
        this.year = year;
        this.month = month;
        this.week = week;
    }


    public int getYear()
    {
        return year;
    }


    public void increaseYear()
    {
        year++;
    }


    public void decreaseYear()
    {
        year--;
    }


    public int getMonth()
    {
        return month;
    }


    public void increaseMonth()
    {
        month++;
        if (month > 12)
        {
            month = 1;
            increaseYear();
        }
    }


    public void decreaseMonth()
    {
        month--;
        if (month < 1)
        {
            month = 12;
            decreaseYear();
        }
    }


    public int getWeek()
    {
        return week;
    }


    public void increaseWeek()
    {
        week++;
        if (week > 52)
        {
            week = 1;
            increaseYear();
        }
    }


    public void decreaseWeek()
    {
        week--;
        if (week < 1)
        {
            week = 52;
            decreaseYear();
        }
    }
}
