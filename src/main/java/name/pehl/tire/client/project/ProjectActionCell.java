package name.pehl.tire.client.project;

import name.pehl.tire.shared.model.Project;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiRenderer;

public abstract class ProjectActionCell extends AbstractCell<Project>
{
    interface ActionRenderer extends UiRenderer
    {
        void render(SafeHtmlBuilder sb);


        void onBrowserEvent(ProjectActionCell cell, NativeEvent event, Element element, Project project);
    }

    static ActionRenderer renderer = GWT.create(ActionRenderer.class);


    public ProjectActionCell()
    {
        super("click");
    }


    @Override
    public void render(final com.google.gwt.cell.client.Cell.Context context, final Project value,
            final SafeHtmlBuilder sb)
    {
        renderer.render(sb);
    }


    @Override
    public void onBrowserEvent(final com.google.gwt.cell.client.Cell.Context context, final Element parent,
            final Project value, final NativeEvent event, final ValueUpdater<Project> valueUpdater)
    {
        renderer.onBrowserEvent(this, event, parent, value);
    }


    @UiHandler("deleteSpan")
    void onDeleteClicked(final ClickEvent event, final Element parent, final Project project)
    {
        onDelete(project);
    }


    protected abstract void onDelete(Project project);
}
