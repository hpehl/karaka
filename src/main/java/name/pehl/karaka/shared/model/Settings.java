package name.pehl.karaka.shared.model;

import com.google.common.collect.ComparisonChain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-03 16:50:33 +0100 (Fr, 03. Dez 2010) $ $Revision: 83 $
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Settings extends BaseModel
{
    boolean formatHoursAsFloatingPointNumber;
    String timeZoneId;
    User user;


    public Settings()
    {
        super();
    }


    public Settings(final String id)
    {
        super(id);
    }

    public boolean hasChanged(final Settings other)
    {
        boolean changed = ComparisonChain.start()
                .compareFalseFirst(formatHoursAsFloatingPointNumber, other.formatHoursAsFloatingPointNumber)
                .compare(timeZoneId, other.timeZoneId).compare(user.username, other.user.username).result() != 0;
        return changed;
    }

    public boolean isFormatHoursAsFloatingPointNumber()
    {
        return formatHoursAsFloatingPointNumber;
    }

    public void setFormatHoursAsFloatingPointNumber(final boolean formatHoursAsFloatingPointNumber)
    {
        this.formatHoursAsFloatingPointNumber = formatHoursAsFloatingPointNumber;
    }

    public String getTimeZoneId()
    {
        return timeZoneId;
    }

    public void setTimeZoneId(final String timeZoneId)
    {
        this.timeZoneId = timeZoneId;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(final User user)
    {
        this.user = user;
    }
}
