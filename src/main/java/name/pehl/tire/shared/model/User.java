package name.pehl.tire.shared.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class User extends BaseModel
{
    String username;
    String firstname;
    String surname;
    String email;


    public User()
    {
        this(null, null, null);
    }


    public User(String username, String email)
    {
        this(null, username, email);
    }


    public User(String id, String username, String email)
    {
        super(id);
        this.username = username;
        this.email = email;
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
}
