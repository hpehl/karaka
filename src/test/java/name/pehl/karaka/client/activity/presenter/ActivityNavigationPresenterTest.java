package name.pehl.karaka.client.activity.presenter;

import static name.pehl.karaka.client.NameTokens.dashboard;
import static name.pehl.karaka.shared.model.TimeUnit.MONTH;
import static name.pehl.karaka.shared.model.TimeUnit.WEEK;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import name.pehl.karaka.client.PresenterTest;
import name.pehl.karaka.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.karaka.shared.model.Activities;

import org.junit.Before;
import org.junit.Test;

import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class ActivityNavigationPresenterTest extends PresenterTest
{
    // ------------------------------------------------------------------ setup

    ActivityNavigationPresenter.MyView view;
    SelectMonthPresenter selectMonthPresenter;
    SelectWeekPresenter selectWeekPresenter;
    ActivityNavigationPresenter cut;


    @Before
    public void setUp()
    {
        view = mock(ActivityNavigationPresenter.MyView.class);
        selectMonthPresenter = mock(SelectMonthPresenter.class);
        selectWeekPresenter = mock(SelectWeekPresenter.class);
        cut = new ActivityNavigationPresenter(eventBus, view, placeManager, selectMonthPresenter, selectWeekPresenter);
    }


    // ------------------------------------------------------------------ tests

    @Test
    public void onActivitiesLoaded()
    {
        Activities activities = td.newActivities(WEEK);
        cut.onActivitiesLoaded(new ActivitiesLoadedEvent(activities));
        verify(view).updateHeader(activities);
    }


    @Test
    public void onCurrentWeek()
    {
        cut.onCurrentWeek();
        verify(placeManager).revealPlace(new PlaceRequest(dashboard).with("current", "week"));
    }


    @Test
    public void onCurrentMonth()
    {
        cut.onCurrentMonth();
        verify(placeManager).revealPlace(new PlaceRequest(dashboard).with("current", "month"));
    }


    @Test
    public void onSelectMonth()
    {
        SelectTimeUnitPresenter.MyView selectView = mock(SelectTimeUnitPresenter.MyView.class);
        when(selectMonthPresenter.getView()).thenReturn(selectView);
        cut.onSelectMonth(100, 200);
        verify(selectView).setPosition(100, 200);
    }


    @Test
    public void onSelectWeek()
    {
        SelectTimeUnitPresenter.MyView selectView = mock(SelectTimeUnitPresenter.MyView.class);
        when(selectWeekPresenter.getView()).thenReturn(selectView);
        cut.onSelectWeek(100, 200);
        verify(selectView).setPosition(100, 200);
    }


    @Test
    public void onPrevious()
    {
        // decrease month, keep year
        Activities activities = td.newActivities(MONTH);
        activities.setMonth(9);
        activities.setYear(2000);
        cut.activities = activities;
        cut.onPrevious();
        verify(placeManager).revealPlace(new PlaceRequest(dashboard).with("year", "2000").with("month", "8"));

        // decrease month and year
        reset(placeManager);
        activities = td.newActivities(MONTH);
        activities.setMonth(1);
        activities.setYear(2000);
        cut.activities = activities;
        cut.onPrevious();
        verify(placeManager).revealPlace(new PlaceRequest(dashboard).with("year", "1999").with("month", "12"));

        // decrease week, keep year
        activities = td.newActivities(WEEK);
        activities.setWeek(11);
        activities.setYear(2000);
        cut.activities = activities;
        cut.onPrevious();
        verify(placeManager).revealPlace(new PlaceRequest(dashboard).with("year", "2000").with("week", "10"));

        // decrease week and year
        reset(placeManager);
        activities = td.newActivities(WEEK);
        activities.setWeek(1);
        activities.setYear(2000);
        cut.activities = activities;
        cut.onPrevious();
        verify(placeManager).revealPlace(new PlaceRequest(dashboard).with("year", "1999").with("week", "52"));
    }


    @Test
    public void onNext()
    {
        // increase month, keep year
        Activities activities = td.newActivities(MONTH);
        activities.setMonth(9);
        activities.setYear(2000);
        cut.activities = activities;
        cut.onNext();
        verify(placeManager).revealPlace(new PlaceRequest(dashboard).with("year", "2000").with("month", "10"));

        // increase month and year
        reset(placeManager);
        activities = td.newActivities(MONTH);
        activities.setMonth(12);
        activities.setYear(2000);
        cut.activities = activities;
        cut.onNext();
        verify(placeManager).revealPlace(new PlaceRequest(dashboard).with("year", "2001").with("month", "1"));

        // increase week, keep year
        activities = td.newActivities(WEEK);
        activities.setWeek(11);
        activities.setYear(2000);
        cut.activities = activities;
        cut.onNext();
        verify(placeManager).revealPlace(new PlaceRequest(dashboard).with("year", "2000").with("week", "12"));

        // increase week and year
        reset(placeManager);
        activities = td.newActivities(WEEK);
        activities.setWeek(52);
        activities.setYear(2000);
        cut.activities = activities;
        cut.onNext();
        verify(placeManager).revealPlace(new PlaceRequest(dashboard).with("year", "2001").with("week", "1"));
    }
}
