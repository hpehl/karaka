package name.pehl.karaka.server.settings.entity;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Embed;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.OnLoad;
import name.pehl.karaka.server.entity.BaseEntity;
import org.joda.time.DateTimeZone;

@Cache
@Entity
public class Settings extends BaseEntity
{
    private boolean formatHoursAsFloatingPointNumber;
    private String timeZoneId;
    @Ignore private DateTimeZone timeZone;
    @Embed private User user;


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

    @OnLoad
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
