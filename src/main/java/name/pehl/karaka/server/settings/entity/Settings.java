package name.pehl.karaka.server.settings.entity;

import name.pehl.karaka.server.entity.BaseEntity;
import org.joda.time.DateTimeZone;

import javax.persistence.Embedded;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

public class Settings extends BaseEntity
{
    private static final long serialVersionUID = 5319841453603258758L;

    private boolean formatHoursAsFloatingPointNumber;
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

    public boolean isFormatHoursAsFloatingPointNumber()
    {
        return formatHoursAsFloatingPointNumber;
    }

    public void setFormatHoursAsFloatingPointNumber(boolean formatHoursAsFloatingPointNumber)
    {
        this.formatHoursAsFloatingPointNumber = formatHoursAsFloatingPointNumber;
    }

    public DateTimeZone getTimeZone()
    {
        return timeZone;
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
