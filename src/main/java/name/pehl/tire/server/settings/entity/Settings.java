package name.pehl.tire.server.settings.entity;

import javax.persistence.Embedded;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import name.pehl.tire.server.entity.BaseEntity;

import org.joda.time.DateTimeZone;

public class Settings extends BaseEntity
{
    private static final long serialVersionUID = 5319841453603258758L;

    private int hoursPerMonth;
    private String timeZoneId;

    @Transient private DateTimeZone timeZone;

    @Embedded private User user;


    public Settings()
    {
        super();
        defaultTimeZone();
    }


    private void defaultTimeZone()
    {
        this.timeZone = DateTimeZone.getDefault();
        this.timeZoneId = this.timeZone.getID();
    }


    @PostLoad
    void restoreTimeZone()
    {
        setTimeZoneId(timeZoneId);
    }


    public void setTimeZoneId(String timeZoneId)
    {
        if (timeZoneId != null)
        {
            try
            {
                this.timeZone = DateTimeZone.forID(timeZoneId);
                this.timeZoneId = timeZone.getID();
            }
            catch (IllegalArgumentException e)
            {
                defaultTimeZone();
            }
        }
        else
        {
            defaultTimeZone();
        }
    }


    public DateTimeZone getTimeZone()
    {
        return timeZone;
    }


    public int getHoursPerMonth()
    {
        return hoursPerMonth;
    }


    public void setHoursPerMonth(int hoursPerMonth)
    {
        this.hoursPerMonth = hoursPerMonth;
    }


    public User getUser()
    {
        return user;
    }


    public void setUser(User user)
    {
        this.user = user;
    }
}
