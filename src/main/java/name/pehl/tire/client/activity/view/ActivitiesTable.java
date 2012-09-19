package name.pehl.tire.client.activity.view;

import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_RIGHT;
import static name.pehl.tire.client.activity.event.ActivityAction.Action.DETAILS;

import java.util.ArrayList;
import java.util.List;

import name.pehl.tire.client.activity.event.ActivityAction.Action;
import name.pehl.tire.client.activity.event.ActivityActionEvent;
import name.pehl.tire.client.activity.event.ActivityActionEvent.ActivityActionHandler;
import name.pehl.tire.client.activity.event.ActivityActionEvent.HasActivityActionHandlers;
import name.pehl.tire.client.cell.ModelActionCell;
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
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

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
        SafeHtml tag(String name);


        @Template("<div class=\"{0}\" style=\"width: 56px;\"><span id=\"copy\" style=\"margin-right:4px;\" title=\"Copy and add one day\">{1}</span><span id=\"start_stop\" style=\"margin-right:4px;\" title=\"Continue\">{2}</span><span id=\"delete\" title=\"Delete\">{3}</span></div>")
        SafeHtml actions(String hideActionsClassname, SafeHtml copy, SafeHtml goon, SafeHtml delete);
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
        addColumns();
    }


    // -------------------------------------------------------------- gui setup

    @Override
    protected void addColumns()
    {
        // Action is the last column in the UI, but the first one to create!
        this.actionCell = new ModelActionCell<Activity>(this, new ModelRenderer<Activity>()
        {
            @Override
            public SafeHtml render(final Activity activity)
            {
                SafeHtml copyHtml = SafeHtmlUtils.fromTrustedString(AbstractImagePrototype.create(resources.copy())
                        .getHTML());
                SafeHtml startStopHtml = SafeHtmlUtils.fromTrustedString(AbstractImagePrototype.create(
                        resources.startStop()).getHTML());
                SafeHtml deleteHtml = SafeHtmlUtils.fromTrustedString(AbstractImagePrototype.create(resources.delete())
                        .getHTML());
                return TEMPLATES.actions(tableResources.cellTableStyle().hideActions(), copyHtml, startStopHtml,
                        deleteHtml);
            }
        });

        // Column #0: Start date
        addDataColumn(resources.activitiesTableStyle().startColumn(), 0, new ModelTextRenderer<Activity>()
        {
            @Override
            protected String getValue(final Activity activity)
            {
                return FormatUtils.date(activity.getStart());
            }
        }, null, new TextHeader(null)
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

        // Column #1: Duration from - to
        addDataColumn(resources.activitiesTableStyle().durationFromToColumn(), 1, new ModelTextRenderer<Activity>()
        {
            @Override
            public String getValue(final Activity activity)
            {
                return FormatUtils.timeDuration(activity.getStart(), activity.getEnd());
            }
        }, null, null);

        // Column #2: Duration in hours
        ModelColumn<Activity> durationColumn = addDataColumn(resources.activitiesTableStyle().durationInHoursColumn(),
                2, new ModelRenderer<Activity>()
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
                }, null, new TextHeader(null)
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
        durationColumn.setHorizontalAlignment(ALIGN_RIGHT);

        // Column #3: Name, Description & Tags
        addDataColumn(resources.activitiesTableStyle().nameColumn(), 3, new ModelRenderer<Activity>()
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
        }, null, null);

        // Column #4: Project
        addDataColumn(resources.activitiesTableStyle().projectColumn(), 4, new ModelTextRenderer<Activity>()
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
        }, null, null);

        // Column #5: Actions
        ModelColumn<Activity> actionColumn = new ModelColumn<Activity>(actionCell);
        addColumnStyleName(5, resources.activitiesTableStyle().actionsColumn());
        addColumn(actionColumn);
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
    protected void onClick(final Activity activity)
    {
        ActivityActionEvent.fire(this, DETAILS, activity);
    }


    @Override
    protected void onAction(final Activity activity, final String actionId)
    {
        ActivityActionEvent.fire(this, Action.valueOf(actionId.toUpperCase()), activity);
    }
}
