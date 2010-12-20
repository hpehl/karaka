package name.pehl.tire.rest.activity;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for all template parameters used in {@link ActivitiesResource} and
 * {@link ActivityResource}.
 * 
 * @author $Author$
 * @version $Date$ $Revision: 123
 *          $
 */
public class ActivityParameters
{
    // -------------------------------------------------------------- constants

    public static final String YEAR = "year";
    public static final String YEAR_OR_RELATIVE_OR_ID = "yearOrRelativeOrId";
    public static final String MONTH_OR_WEEK_OR_ACTION = "monthOrWeekOrAction";
    public static final String RELATIVE = "relative";
    public static final String CURRENT_MONTH = "currentMonth";
    public static final String MONTH = "month";
    public static final String CURRENT_WEEK = "currentWeek";
    public static final String TODAY = "today";
    public static final String DAY = "day";
    public static final String ID_OR_CURRENT_MONTH_OR_CURRENT_WEEK_OR_TODAY = "idOrCurrentMonthOrCurrentWeekOrToday";

    public static final String START = "start";
    public static final String STOP = "stop";
    public static final String PAUSE = "pause";
    public static final Pattern WEEK_PATTERN = Pattern.compile("cw((\\+|-)?[\\d]{1,2})");

    // -------------------------------------------------------- private members

    private static final Logger logger = Logger.getLogger(ActivityParameters.class.getName());

    private Map<String, Object> parameters;
    private boolean hasYear;
    private int year;
    private boolean currentMonth;
    private boolean hasMonth;
    private int month;
    private boolean currentWeek;
    private boolean hasWeek;
    private int week;
    private boolean today;
    private boolean hasDay;
    private int day;
    private boolean hasId;
    private long id;
    private boolean relative;
    private boolean hasAction;
    private Action action;


    // --------------------------------------------------------- public methods

    public ActivityParameters parse(Map<String, Object> parameters) throws IllegalArgumentException
    {
        if (parameters == null || parameters.isEmpty())
        {
            return this;
        }

        // Unambiguous template parameters
        String value = null;
        value = (String) parameters.get(YEAR);
        if (value != null)
        {
            year = (int) convertToNumber(YEAR, value);
            hasYear = true;
        }
        value = (String) parameters.get(MONTH);
        if (value != null)
        {
            month = (int) convertToNumber(MONTH, value);
            hasMonth = true;
        }
        value = (String) parameters.get(DAY);
        if (value != null)
        {
            day = (int) convertToNumber(DAY, value);
            hasDay = true;
        }

        // Ambiguous template parameters
        value = (String) parameters.get(ID_OR_CURRENT_MONTH_OR_CURRENT_WEEK_OR_TODAY);
        if (value != null)
        {
            if (CURRENT_MONTH.equals(value) || CURRENT_WEEK.equals(value) || TODAY.equals(value))
            {
                if (CURRENT_MONTH.equals(value))
                {
                    currentMonth = true;
                }
                else if (CURRENT_WEEK.equals(value))
                {
                    currentWeek = true;
                }
                else
                {
                    today = true;
                }
                hasId = false;
            }
            else
            {
                id = convertToNumber(ID_OR_CURRENT_MONTH_OR_CURRENT_WEEK_OR_TODAY, value);
                hasId = true;
            }
        }
        value = (String) parameters.get(MONTH_OR_WEEK_OR_ACTION);
        if (value != null)
        {
            if (START.equals(value) || STOP.equals(value) || PAUSE.equals(value))
            {
                // In this case id is required
                String idValue = (String) parameters.get(YEAR_OR_RELATIVE_OR_ID);
                id = convertToNumber(YEAR_OR_RELATIVE_OR_ID, idValue);
                hasId = true;

                action = Action.valueOf(value.toUpperCase());
                hasAction = true;
            }
            else
            {
                // In this case relative keyword or year is required
                String yearOrRelativeValue = (String) parameters.get(YEAR_OR_RELATIVE_OR_ID);
                if (RELATIVE.equals(yearOrRelativeValue))
                {
                    relative = true;
                    Matcher m = WEEK_PATTERN.matcher(value);
                    if (m.matches())
                    {
                        week = (int) convertToNumber(MONTH_OR_WEEK_OR_ACTION, m.group(1));
                        hasWeek = true;
                    }
                    else
                    {
                        month = (int) convertToNumber(MONTH_OR_WEEK_OR_ACTION, value);
                        hasMonth = true;
                    }
                }
                else
                {
                    year = (int) convertToNumber(YEAR_OR_RELATIVE_OR_ID, yearOrRelativeValue);
                    hasYear = true;
                    Matcher m = WEEK_PATTERN.matcher(value);
                    if (m.matches())
                    {
                        week = (int) convertToNumber(MONTH_OR_WEEK_OR_ACTION, m.group(1));
                        hasWeek = true;
                    }
                    else
                    {
                        month = (int) convertToNumber(MONTH_OR_WEEK_OR_ACTION, value);
                        hasMonth = true;
                    }
                }
            }
        }
        return this;
    }


    private long convertToNumber(String name, String value)
    {
        if (value != null && value.trim().length() != 0)
        {
            try
            {
                return Long.parseLong(value);
            }
            catch (NumberFormatException e)
            {
                String message = String.format(
                        "The template parameter named %s with value %s cannot be converted to a number", name, value);
                logger.log(Level.WARNING, message, e);
                throw new IllegalArgumentException(message, e);
            }
        }
        else
        {
            throw new IllegalArgumentException(String.format("Required template parameter %s was not specified", name));
        }
    }


    // ------------------------------------------------------------- properties

    public boolean hasParameters()
    {
        return parameters != null && !parameters.isEmpty();
    }


    public boolean hasYear()
    {
        return hasYear;
    }


    public int getYear()
    {
        return year;
    }


    public boolean isCurrentMonth()
    {
        return currentMonth;
    }


    public boolean hasMonth()
    {
        return hasMonth;
    }


    public int getMonth()
    {
        return month;
    }


    public boolean isCurrentWeek()
    {
        return currentWeek;
    }


    public boolean hasWeek()
    {
        return hasWeek;
    }


    public int getWeek()
    {
        return week;
    }


    public boolean isToday()
    {
        return today;
    }


    public boolean hasDay()
    {
        return hasDay;
    }


    public int getDay()
    {
        return day;
    }


    public boolean hasId()
    {
        return hasId;
    }


    public long getId()
    {
        return id;
    }


    public boolean isRelative()
    {
        return relative;
    }


    public boolean hasAction()
    {
        return hasAction;
    }


    public Action getAction()
    {
        return action;
    }
}
