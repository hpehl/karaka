package name.pehl.tire.client.activity.presenter;

import name.pehl.tire.client.PresenterTest;
import name.pehl.tire.client.activity.dispatch.GetYearsAction;
import name.pehl.tire.client.activity.dispatch.GetYearsHandler;
import name.pehl.tire.client.activity.dispatch.GetYearsResult;
import name.pehl.tire.shared.model.Years;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.common.collect.ImmutableMap;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.client.actionhandler.ClientActionHandler;
import com.gwtplatform.dispatch.client.actionhandler.ExecuteCommand;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

import static name.pehl.tire.client.NameTokens.dashboard;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;

import static org.junit.Assert.assertSame;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SelectYearAndMonthOrWeekPresenterTest extends PresenterTest
{
    // ------------------------------------------------------------------ setup

    GetYearsHandler getYearsHandler;
    SelectYearAndMonthOrWeekPresenter.MyView view;
    SelectYearAndMonthOrWeekPresenter cut;


    @Before
    public void setUp()
    {
        // client action handlers
        getYearsHandler = mock(GetYearsHandler.class);
        ImmutableMap<Class<?>, ClientActionHandler<?, ?>> actionHandlerMappings = new ImmutableMap.Builder<Class<?>, ClientActionHandler<?, ?>>()
                .put(GetYearsAction.class, getYearsHandler).build();

        // actual setup
        view = mock(SelectYearAndMonthOrWeekPresenter.MyView.class);
        cut = new SelectYearAndMonthOrWeekPresenter(eventBus, view, newDispatcher(actionHandlerMappings), placeManager);
    }


    // ------------------------------------------------------------------ tests

    @Test
    @SuppressWarnings("unchecked")
    public void onReveal()
    {
        Years years = new Years(null);
        final GetYearsResult getYearsResult = new GetYearsResult(years);
        Answer<Object> getYearsAnswer = new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocation)
            {
                AsyncCallback<GetYearsResult> callback = (AsyncCallback<GetYearsResult>) invocation.getArguments()[1];
                callback.onSuccess(getYearsResult);
                return null;
            }
        };
        doAnswer(getYearsAnswer).when(getYearsHandler).execute(any(GetYearsAction.class), any(AsyncCallback.class),
                any(ExecuteCommand.class));

        cut.onReveal();

        assertSame(years, cut.years);
        verify(view).updateYears(years);
    }


    @Test
    public void setUnit()
    {
        cut.setUnit(WEEK);
        verify(view).setUnit(WEEK);
    }


    @Test
    public void onSelectYearAndMonthOrWeek()
    {
        cut.onSelectYearAndMonth(2000, 1);
        verify(placeManager).revealPlace(new PlaceRequest(dashboard).with("year", "2000").with("month", "1"));

        cut.onSelectYearAndWeek(2000, 1);
        verify(placeManager).revealPlace(new PlaceRequest(dashboard).with("year", "2000").with("week", "1"));
    }
}
