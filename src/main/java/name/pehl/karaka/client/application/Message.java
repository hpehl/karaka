package name.pehl.karaka.client.application;

import java.util.logging.Level;

/**
 * Value object for a message shown in the {@link MessagePresenter}.
 * 
 * @author $Author$
 * @version $Revision$
 */
public class Message
{
    private final Level level;
    private final String text;
    private final boolean autoHide;


    public Message(Level level, String text, boolean autoHide)
    {
        this.level = level;
        this.text = text;
        this.autoHide = autoHide;
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (autoHide ? 1231 : 1237);
        result = prime * result + (level == null ? 0 : level.hashCode());
        result = prime * result + (text == null ? 0 : text.hashCode());
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
        if (autoHide != other.autoHide)
        {
            return false;
        }
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
        return new StringBuilder().append(level).append(": ").append(text).append(", autoHide=").append(autoHide)
                .toString();
    }


    public Level getLevel()
    {
        return level;
    }


    public String getText()
    {
        return text;
    }


    public boolean isAutoHide()
    {
        return autoHide;
    }
}
