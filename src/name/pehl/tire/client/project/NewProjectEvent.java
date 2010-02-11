package name.pehl.tire.client.project;

import com.google.gwt.event.shared.GwtEvent;

public class NewProjectEvent extends GwtEvent<NewProjectEventHandler>
{
    public static Type<NewProjectEventHandler> TYPE = new Type<NewProjectEventHandler>();

    private final String name;
    private final String message;


    public NewProjectEvent(final String name, final String message)
    {
        this.name = name;
        this.message = message;
    }


    public String getName()
    {
        return name;
    }


    public String getMessage()
    {
        return message;
    }


    @Override
    public Type<NewProjectEventHandler> getAssociatedType()
    {
        return TYPE;
    }


    @Override
    protected void dispatch(final NewProjectEventHandler handler)
    {
        handler.onNewProject(this);
    }
}
