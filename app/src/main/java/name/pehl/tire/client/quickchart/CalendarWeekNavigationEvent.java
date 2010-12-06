package name.pehl.tire.client.quickchart;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class CalendarWeekNavigationEvent extends GwtEvent<CalendarWeekNavigationHandler>
{
    public static enum Direction
    {
        PREV,
        CURRENT,
        NEXT;
    }

    private static Type<CalendarWeekNavigationHandler> TYPE;


    public static Type<CalendarWeekNavigationHandler> getType()
    {
        if (TYPE == null)
        {
            TYPE = new Type<CalendarWeekNavigationHandler>();
        }
        return TYPE;
    }


    @Override
    public Type<CalendarWeekNavigationHandler> getAssociatedType()
    {
        return TYPE;
    }


    public static <S extends HasHandlers> void fire(S source, Direction direction)
    {
        if (TYPE != null)
        {
            CalendarWeekNavigationEvent event = new CalendarWeekNavigationEvent(direction);
            source.fireEvent(event);
        }
    }

    private final Direction direction;


    protected CalendarWeekNavigationEvent(Direction direction)
    {
        this.direction = direction;
    }


    @Override
    protected void dispatch(CalendarWeekNavigationHandler handler)
    {
        handler.onNavigation(this);
    }


    public Direction getDirection()
    {
        return direction;
    }
}
