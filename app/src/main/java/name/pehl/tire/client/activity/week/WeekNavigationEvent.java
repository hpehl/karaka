package name.pehl.tire.client.activity.week;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

/**
 * @author $Author$
 * @version $Date$ $Revision$
 */
public class WeekNavigationEvent extends GwtEvent<WeekNavigationHandler>
{
    public static enum Direction
    {
        PREV,
        CURRENT,
        NEXT;
    }

    private static Type<WeekNavigationHandler> TYPE;


    public static Type<WeekNavigationHandler> getType()
    {
        if (TYPE == null)
        {
            TYPE = new Type<WeekNavigationHandler>();
        }
        return TYPE;
    }


    @Override
    public Type<WeekNavigationHandler> getAssociatedType()
    {
        return TYPE;
    }


    public static <S extends HasHandlers> void fire(S source, Direction direction)
    {
        if (TYPE != null)
        {
            WeekNavigationEvent event = new WeekNavigationEvent(direction);
            source.fireEvent(event);
        }
    }

    private final Direction direction;


    protected WeekNavigationEvent(Direction direction)
    {
        this.direction = direction;
    }


    @Override
    protected void dispatch(WeekNavigationHandler handler)
    {
        handler.onNavigation(this);
    }


    public Direction getDirection()
    {
        return direction;
    }
}
