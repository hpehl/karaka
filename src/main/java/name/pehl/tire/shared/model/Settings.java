package name.pehl.tire.shared.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-03 16:50:33 +0100 (Fr, 03. Dez 2010) $ $Revision: 83
 *          $
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Settings extends BaseModel
{
    int hoursPerMonth;
    String timeZoneId;
    User user;


    public Settings()
    {
        super();
    }


    public Settings(String id)
    {
        super(id);
    }


    public int getHoursPerMonth()
    {
        return hoursPerMonth;
    }


    public void setHoursPerMonth(int hoursPerMonth)
    {
        this.hoursPerMonth = hoursPerMonth;
    }


    public String getTimeZoneId()
    {
        return timeZoneId;
    }


    public void setTimeZoneId(String timeZoneId)
    {
        this.timeZoneId = timeZoneId;
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
