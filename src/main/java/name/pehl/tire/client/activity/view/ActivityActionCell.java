package name.pehl.tire.client.activity.view;

import name.pehl.tire.shared.model.Activity;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiRenderer;

public abstract class ActivityActionCell extends AbstractCell<Activity>
{
    interface ActionRenderer extends UiRenderer
    {
        void render(SafeHtmlBuilder sb);


        void onBrowserEvent(ActivityActionCell cell, NativeEvent event, Element element, Activity activity);
    }

    static ActionRenderer renderer = GWT.create(ActionRenderer.class);


    public ActivityActionCell()
    {
        super("click");
    }


    @Override
    public void render(final com.google.gwt.cell.client.Cell.Context context, final Activity value,
            final SafeHtmlBuilder sb)
    {
        renderer.render(sb);
    }


    @Override
    public void onBrowserEvent(final com.google.gwt.cell.client.Cell.Context context, final Element parent,
            final Activity value, final NativeEvent event, final ValueUpdater<Activity> valueUpdater)
    {
        renderer.onBrowserEvent(this, event, parent, value);
    }


    @UiHandler("copySpan")
    void onCopyClicked(final ClickEvent event, final Element parent, final Activity activity)
    {
        onCopy(activity);
    }


    @UiHandler("startStopSpan")
    void onStartStopClicked(final ClickEvent event, final Element parent, final Activity activity)
    {
        onStartStop(activity);
    }


    @UiHandler("deleteSpan")
    void onDeleteClicked(final ClickEvent event, final Element parent, final Activity activity)
    {
        onDelete(activity);
    }


    protected abstract void onCopy(Activity activity);


    protected abstract void onStartStop(Activity activity);


    protected abstract void onDelete(Activity activity);
}
