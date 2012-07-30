package name.pehl.tire.server.activity.entity;

public class Duration
{
    final long minutes;


    Duration()
    {
        this(0);
    }


    public Duration(long minutes)
    {
        this.minutes = minutes;
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (minutes ^ minutes >>> 32);
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
        if (!(obj instanceof Duration))
        {
            return false;
        }
        Duration other = (Duration) obj;
        if (minutes != other.minutes)
        {
            return false;
        }
        return true;
    }


    @Override
    public String toString()
    {
        return minutes + "m";
    }


    public long getMinutes()
    {
        return minutes;
    }
}
