package name.pehl.tire.client.activity.view;

import static name.pehl.tire.client.activity.event.ActivityAction.Action.DETAILS;

import java.util.ArrayList;
import java.util.List;

import name.pehl.tire.client.activity.event.ActivityAction.Action;
import name.pehl.tire.client.activity.event.ActivityActionEvent;
import name.pehl.tire.client.activity.event.ActivityActionEvent.ActivityActionHandler;
import name.pehl.tire.client.activity.event.ActivityActionEvent.HasActivityActionHandlers;
import name.pehl.tire.client.cell.ModelCell;
import name.pehl.tire.client.cell.ModelColumn;
import name.pehl.tire.client.cell.ModelRenderer;
import name.pehl.tire.client.cell.ModelTextRenderer;
import name.pehl.tire.client.cell.ModelsTable;
import name.pehl.tire.client.resources.TableResources;
import name.pehl.tire.client.ui.FormatUtils;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Activity;
import name.pehl.tire.shared.model.Tag;

import com.google.common.base.Strings;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.TextHeader;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ActivitiesTable extends ModelsTable<Activity> implements HasActivityActionHandlers
{
    // -------------------------------------------------------------- templates

    interface ActionsTemplates extends SafeHtmlTemplates
    {
        @Template("<span title=\"{1} pause\">{0}<sup>P</sup></span>")
        SafeHtml duration(String duration, String pause);


        @Template("<span title=\"{1}\">{0}</span>")
        SafeHtml nameDescription(String name, String description);


        @Template("<mark>{0}</mark>")
        SafeHtml tag(final String name);
    }

    static final ActionsTemplates TEMPLATES = GWT.create(ActionsTemplates.class);

    // ---------------------------------------------------------------- members

    Activities currentActivities;
    final name.pehl.tire.client.resources.Resources resources;


    // ----------------------------------------------------------- constructors

    public ActivitiesTable(final name.pehl.tire.client.resources.Resources resources,
            final TableResources tableResources)
    {
        super(tableResources);
        this.resources = resources;
        this.resources.activitiesTableStyle().ensureInjected();
    }


    // -------------------------------------------------------------- gui setup

    @Override
    protected void addColumns()
    {
        // Column #0: Start date
        ModelColumn<Activity> column = new ModelColumn<Activity>(new ModelCell<Activity>(this,
                new ModelTextRenderer<Activity>()
                {
                    @Override
                    protected String getValue(final Activity activity)
                    {
                        return FormatUtils.fulldate(activity.getStart());
                    }
                }));
        addColumn(column, null, new TextHeader(null)
        {
            @Override
            public String getValue()
            {
                if (currentActivities != null)
                {
                    return currentActivities.getNumberOfDays() + " days";
                }
                return null;
            }
        });
        addColumnStyleName(0, resources.projectsTableStyle().nameColumn());

        // Column #1: Duration from - to
        column = new ModelColumn<Activity>(new ModelCell<Activity>(this, new ModelTextRenderer<Activity>()
        {
            @Override
            public String getValue(final Activity activity)
            {
                return FormatUtils.timeDuration(activity.getStart(), activity.getEnd());
            }
        }));
        addColumn(column);
        addColumnStyleName(1, resources.activitiesTableStyle().durationFromToColumn());

        // Column #2: Duration in hours
        column = new ModelColumn<Activity>(new ModelCell<Activity>(this, new ModelRenderer<Activity>()
        {
            @Override
            public SafeHtml render(final Activity activity)
            {
                String duration = FormatUtils.duration(activity.getDuration());
                if (activity.getPause().isZero())
                {
                    return toSafeHtml(duration);
                }
                else
                {
                    String pause = FormatUtils.duration(activity.getPause());
                    return TEMPLATES.duration(duration, pause);
                }
            }
        }));
        addColumn(column, null, new TextHeader(null)
        {
            @Override
            public String getValue()
            {
                if (currentActivities != null)
                {
                    return FormatUtils.duration(currentActivities.getDuration());
                }
                return null;
            }
        });
        addColumnStyleName(2, resources.activitiesTableStyle().durationInHoursColumn());

        // Column #3: Name, Description & Tags
        column = new ModelColumn<Activity>(new ModelCell<Activity>(this, new ModelRenderer<Activity>()
        {
            @Override
            public SafeHtml render(final Activity activity)
            {
                SafeHtml nameDescription = TEMPLATES.nameDescription(activity.getName(),
                        Strings.nullToEmpty(activity.getDescription()));
                List<Tag> tags = activity.getTags();
                if (tags.isEmpty())
                {
                    return nameDescription;
                }
                else
                {
                    SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();
                    safeHtmlBuilder.append(nameDescription);
                    for (Tag tag : tags)
                    {
                        if (tag.getName() != null)
                        {
                            safeHtmlBuilder.append(' ').append(TEMPLATES.tag(tag.getName()));
                        }
                    }
                    return safeHtmlBuilder.toSafeHtml();
                }
            }
        }));
        addColumn(column);
        addColumnStyleName(3, resources.activitiesTableStyle().nameColumn());

        // Column #4: Project
        column = new ModelColumn<Activity>(new ModelCell<Activity>(this, new ModelTextRenderer<Activity>()
        {
            @Override
            public String getValue(final Activity activity)
            {
                if (activity.getProject() != null)
                {
                    return activity.getProject().getName();
                }
                return null;
            }
        }));
        addColumn(column);
        addColumnStyleName(4, resources.activitiesTableStyle().projectColumn());

        // Column #5: Actions
        column = new ModelColumn<Activity>(new ActivityActionCell()
        {
            @Override
            protected void onCopy(final Activity activity)
            {
                ActivityActionEvent.fire(ActivitiesTable.this, Action.COPY, activity);
            }


            @Override
            protected void onStartStop(final Activity activity)
            {
                ActivityActionEvent.fire(ActivitiesTable.this, Action.COPY, activity);
            }


            @Override
            protected void onDelete(final Activity activity)
            {
                ActivityActionEvent.fire(ActivitiesTable.this, Action.COPY, activity);
            }
        });
        addColumn(column);
        addColumnStyleName(5, resources.activitiesTableStyle().actionsColumn());
    }


    @Override
    protected String rowStyle(final Activity model, final int rowIndex)
    {
        if (model.isRunning())
        {
            return resources.activitiesTableStyle().activeActivity();
        }
        if (model.getStart().getDay() % 2 != 0)
        {
            return tableResources.cellTableStyle().alternativeColor();
        }
        return null;
    }


    // --------------------------------------------------------- public methods

    public void update(final Activities activities)
    {
        currentActivities = activities;
        update(new ArrayList<Activity>(activities.activities()));
    }


    // --------------------------------------------------------- event handling

    @Override
    public HandlerRegistration addActivityActionHandler(final ActivityActionHandler handler)
    {
        return addHandler(handler, ActivityActionEvent.getType());
    }


    @Override
    public void onEdit(final Activity activity)
    {
        ActivityActionEvent.fire(this, DETAILS, activity);
    }
}
