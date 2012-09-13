package name.pehl.tire.client.activity.view;

import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;
import name.pehl.tire.client.activity.presenter.ActivityNavigationPresenter;
import name.pehl.tire.client.activity.presenter.ActivityNavigationUiHandlers;
import name.pehl.tire.client.resources.I18n;
import name.pehl.tire.client.ui.FormatUtils;
import name.pehl.tire.client.ui.InlineHTMLWithContextMenu;
import name.pehl.tire.shared.model.Activities;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

/**
 * Ideas:
 * <ul>
 * <li>Place buttons / clickable labels with the current tags next to the
 * header. Clicking the labels will filter the activities. See
 * http://meteor.com/examples/todos
 * </ul>
 */
public class ActivityNavigationView extends ViewWithUiHandlers<ActivityNavigationUiHandlers> implements
        ActivityNavigationPresenter.MyView
{
    // ---------------------------------------------------------- inner classes

    public interface Binder extends UiBinder<Widget, ActivityNavigationView>
    {
    }

    // ---------------------------------------------------------- private stuff

    final I18n i18n;
    final Widget widget;

    @UiField InlineLabel header;
    @UiField InlineHTMLWithContextMenu previous;
    @UiField InlineHTMLWithContextMenu next;
    @UiField InlineHTMLWithContextMenu month;
    @UiField InlineHTMLWithContextMenu week;


    // ------------------------------------------------------------------ setup

    @Inject
    public ActivityNavigationView(final Binder binder, final I18n i18n)
    {
        this.i18n = i18n;
        this.widget = binder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    @Override
    public void setUiHandlers(ActivityNavigationUiHandlers uiHandlers)
    {
        super.setUiHandlers(uiHandlers);
    }


    // ----------------------------------------------------------- view methods

    @Override
    public void updateHeader(Activities activities)
    {
        // Update header
        StringBuilder text = new StringBuilder();
        StringBuilder title = new StringBuilder();
        if (activities.getUnit() == WEEK)
        {
            String cw = "CW " + activities.getWeek() + " / " + activities.getYear();
            text.append(cw);
            title.append(cw);
        }
        else if (activities.getUnit() == MONTH)
        {
            String monthKey = "month_" + activities.getMonth();
            String month = i18n.enums().getString(monthKey) + " " + activities.getYear();
            text.append(month);
            title.append(month);
        }
        title.append(" - ").append(FormatUtils.duration(activities.getDuration())).append(" - ")
                .append(FormatUtils.dateDuration(activities.getStart(), activities.getEnd()));
        header.setText(text.toString());
        header.setTitle(title.toString());

        // Update navigation
        previous.setVisible(activities.hasPrev());
        previous.setTitle("Previous " + activities.getUnit().name().toLowerCase());
        next.setVisible(activities.hasNext());
        next.setTitle("Next " + activities.getUnit().name().toLowerCase());
    }


    // ------------------------------------------------------------ ui handlers

    @UiHandler("month")
    public void onMonthClicked(ClickEvent event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().onCurrentMonth();
        }
    }


    @UiHandler("month")
    public void onMonthContextMenu(ContextMenuEvent event)
    {
        if (getUiHandlers() != null)
        {
            event.preventDefault();
            getUiHandlers().onSelectMonth(event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY());
        }
    }


    @UiHandler("week")
    public void onWeekClicked(ClickEvent event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().onCurrentWeek();
        }
    }


    @UiHandler("week")
    public void onWeekContextMenu(ContextMenuEvent event)
    {
        if (getUiHandlers() != null)
        {
            event.preventDefault();
            getUiHandlers().onSelectWeek(event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY());
        }
    }


    @UiHandler("previous")
    public void onPreviousClicked(ClickEvent event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().onPrevious();
        }
    }


    @UiHandler("next")
    public void onNextClicked(ClickEvent event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().onNext();
        }
    }
}
