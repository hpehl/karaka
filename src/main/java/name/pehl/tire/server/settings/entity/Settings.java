package name.pehl.tire.server.settings.entity;

import javax.persistence.PostLoad;
import javax.persistence.Transient;

import name.pehl.tire.server.entity.BaseEntity;

import org.joda.time.DateTimeZone;

public class Settings extends BaseEntity
{
    private static final long serialVersionUID = 5319841453603258758L;

    private String timeZoneId;

    @Transient private DateTimeZone timeZone;


    public Settings()
    {
        super();
        defaults();
    }


    private void defaults()
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
                defaults();
            }
        }
        else
        {
            defaults();
        }
    }


    public DateTimeZone getTimeZone()
    {
        return timeZone;
    }
}