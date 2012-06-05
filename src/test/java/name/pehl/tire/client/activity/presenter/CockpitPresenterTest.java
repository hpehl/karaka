package name.pehl.tire.client.activity.presenter;

import name.pehl.tire.client.activity.presenter.CockpitPresenter.MyView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.web.bindery.event.shared.testing.CountingEventBus;
import com.gwtplatform.tester.TestDispatchAsync;

@RunWith(MockitoJUnitRunner.class)
public class CockpitPresenterTest
{
    // ------------------------------------------------------------------ setup

    CockpitPresenter cut;
    @Mock MyView view;


    @Before
    public void setUp()
    {
        // use
        new CountingEventBus();
        // and
        new TestDispatchAsync(null, null);
    }


    // ------------------------------------------------------------------ tests

    @Test
    public void onStartStop()
    {
    }


    @Test
    public void onActivityChanged()
    {
    }


    @Test
    public void onTick()
    {
    }
}
