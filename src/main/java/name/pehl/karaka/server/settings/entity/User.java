package name.pehl.karaka.server.settings.entity;

import name.pehl.karaka.server.entity.BaseEntity;

public class User extends BaseEntity
{
    private String userId;
    private String username;
    private String firstname;
    private String surname;
    private String email;


    User()
    {
        super();
    }


    public User(String userId, String username, String email)
    {
        super();
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
}
