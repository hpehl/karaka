package name.pehl.tire.client.activity.view;

public class TimeParser
{
    public int[] parse(String time) throws IllegalArgumentException
    {
        if (time == null || time.trim().length() == 0)
        {
            throw new IllegalArgumentException("Null or empty string");
        }

        int[] result = new int[2];
        String[] parts = time.split(":");
        if (parts.length == 1)
        {
            result[0] = convert(parts[0]);
        }
        else if (parts.length == 2)
        {
            result[0] = convert(parts[0]);
            result[1] = convert(parts[1]);
        }
        else
        {
            throw new IllegalArgumentException("Invalid format");
        }
        return result;
    }


    private int convert(String value)
    {
        int result = 0;
        try
        {
            result = Integer.valueOf(value);
        }
        catch (NumberFormatException e)
        {
            throw new IllegalArgumentException(value + " is not a valid number");
        }
        return result;
    }
}