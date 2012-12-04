package name.pehl.karaka.client.activity.presenter;

import com.google.common.collect.ImmutableMap;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.client.actionhandler.ClientActionHandler;
import com.gwtplatform.dispatch.client.actionhandler.ExecuteCommand;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import name.pehl.karaka.client.PresenterTest;
import name.pehl.karaka.client.activity.dispatch.GetYearsAction;
import name.pehl.karaka.client.activity.dispatch.GetYearsHandler;
import name.pehl.karaka.client.activity.dispatch.GetYearsResult;
import name.pehl.karaka.shared.model.Years;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static name.pehl.karaka.client.NameTokens.dashboard;
import static name.pehl.karaka.client.activity.dispatch.ActivitiesRequest.ACTIVITIES_PARAM;
import static name.pehl.karaka.client.activity.dispatch.ActivitiesRequest.SEPERATOR;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class SelectTimeUnitPresenterTest extends PresenterTest
{
    // ------------------------------------------------------------------ setup

    GetYearsHandler getYearsHandler;
    SelectTimeUnitPresenter.MyView view;
    SelectMonthPresenter cutMonth;
    SelectWeekPresenter cutWeek;

    @Before
    public void setUpTimeUnitPresenter()
    {
        // client action handlers
        getYearsHandler = mock(GetYearsHandler.class);
        ImmutableMap<Class<?>, ClientActionHandler<?, ?>> actionHandlerMappings = new ImmutableMap.Builder<Class<?>, ClientActionHandler<?, ?>>()
                .put(GetYearsAction.class, getYearsHandler).build();

        // actual setup
        view = mock(SelectTimeUnitPresenter.MyView.class);
        cutMonth = new SelectMonthPresenter(eventBus, view, newDispatcher(actionHandlerMappings), placeManager);
        cutWeek = new SelectWeekPresenter(eventBus, view, newDispatcher(actionHandlerMappings), placeManager);
    }


    // ------------------------------------------------------------------ tests

    @Test
    @SuppressWarnings("unchecked")
    public void onRevealMonth()
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

        cutMonth.onReveal();

        assertSame(years, cutMonth.years);
        verify(view).updateYears(years);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void onRevealWeek()
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

        cutWeek.onReveal();

        assertSame(years, cutWeek.years);
        verify(view).updateYears(years);
    }

    @Test
    public void onSelectMonth()
    {
        cutMonth.onSelectYearAndMonth(2000, 1);
        verify(placeManager).revealPlace(new PlaceRequest(dashboard).with(ACTIVITIES_PARAM, "2000" + SEPERATOR + "1"));
    }

    @Test
    public void onSelectWeek()
    {
        cutWeek.onSelectYearAndWeek(2000, 1);
        verify(placeManager)
                .revealPlace(new PlaceRequest(dashboard).with(ACTIVITIES_PARAM, "2000" + SEPERATOR + "cw1"));
    }
}
