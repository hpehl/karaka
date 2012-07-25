package name.pehl.tire.client.activity.presenter;

public class TimeParser
{
    static final String REGEX = "([0-9]+[,.]?[0-9]*)([h|m]?)";


    public Duration parse(String enteredTime)
    {
        return new Duration(0, 0);
    }

    public static class Duration
    {
        private final long hours;
        private final long minutes;


        public Duration(long hours, long minutes)
        {
            super();
            this.hours = hours;
            this.minutes = minutes;
        }


        public long getHours()
        {
            return hours;
        }


        public long getMinutes()
        {
            return minutes;
        }


        public long getTotalInMinutes()
        {
            return hours * 60 + minutes;
        }
    }
}
