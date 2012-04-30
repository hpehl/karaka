package name.pehl.tire.client.application;

import java.util.logging.Level;

import static java.util.logging.Level.INFO;

/**
 * Value object for a message shown in the {@link MessagePresenter}.
 * 
 * @author $Author$
 * @version $Revision$
 */
public class Message
{
    private final String text;
    private final Level level;


    public Message(String text)
    {
        this(text, INFO);
    }


    public Message(String text, Level level)
    {
        super();
        this.text = text;
        this.level = level;
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((level == null) ? 0 : level.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof Message))
        {
            return false;
        }
        Message other = (Message) obj;
        if (level == null)
        {
            if (other.level != null)
            {
                return false;
            }
        }
        else if (!level.equals(other.level))
        {
            return false;
        }
        if (text == null)
        {
            if (other.text != null)
            {
                return false;
            }
        }
        else if (!text.equals(other.text))
        {
            return false;
        }
        return true;
    }


    @Override
    public String toString()
    {
        return new StringBuilder().append(level).append(": ").append(text).toString();
    }


    public String getText()
    {
        return text;
    }


    public Level getLevel()
    {
        return level;
    }
}
