package name.pehl.karaka.shared.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class User extends BaseModel
{
    String userId;
    String username;
    String firstname;
    String surname;
    String email;
    String logoutUrl;


    public User()
    {
        this(newId(), null, null, null);
    }


    public User(String userId, String username, String email)
    {
        this(newId(), userId, username, email);
    }


    public User(String id, String userId, String username, String email)
    {
        super(id);
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(final String userId)
    {
        this.userId = userId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getLogoutUrl()
    {
        return logoutUrl;
    }

    public void setLogoutUrl(final String logoutUrl)
    {
        this.logoutUrl = logoutUrl;
    }
}
