package name.pehl.tire.client.activity.presenter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import name.pehl.tire.client.PresenterTest;
import name.pehl.tire.client.activity.dispatch.SaveActivityAction;
import name.pehl.tire.client.activity.dispatch.SaveActivityHandler;
import name.pehl.tire.client.activity.dispatch.SaveActivityResult;
import name.pehl.tire.client.activity.event.TickEvent;
import name.pehl.tire.client.activity.event.TickEvent.TickHandler;
import name.pehl.tire.shared.model.Activity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.common.collect.ImmutableMap;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.client.actionhandler.ClientActionHandler;
import com.gwtplatform.dispatch.client.actionhandler.ExecuteCommand;

public class TickCommandTest extends PresenterTest implements TickHandler
{
    // ------------------------------------------------------------------ setup

    SaveActivityHandler saveActivityHandler;
    TickCommand cut;


    @Before
    public void setUp()
    {
        // client action handlers
        saveActivityHandler = mock(SaveActivityHandler.class);
        ImmutableMap<Class<?>, ClientActionHandler<?, ?>> actionHandlerMappings = new ImmutableMap.Builder<Class<?>, ClientActionHandler<?, ?>>()
                .put(SaveActivityAction.class, saveActivityHandler).build();

        // class under test
        addEvents(this, TickEvent.getType());
        cut = new TickCommand(eventBus, scheduler, newDispatcher(actionHandlerMappings));
    }


    // ------------------------------------------------------------------ tests

    @Test
    @SuppressWarnings("unchecked")
    public void start()
    {
        Activity activity = mock(Activity.class);
        final SaveActivityResult saveActivityResult = new SaveActivityResult(activity);
        Answer<Object> saveActivityAnswer = new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocation)
            {
                AsyncCallback<SaveActivityResult> callback = (AsyncCallback<SaveActivityResult>) invocation
                        .getArguments()[1];
                callback.onSuccess(saveActivityResult);
                return null;
            }
        };
        doAnswer(saveActivityAnswer).when(saveActivityHandler).execute(any(SaveActivityAction.class),
                any(AsyncCallback.class), any(ExecuteCommand.class));

        cut.start(activity);
        assertTrue(cut.running);
        assertSame(activity, cut.activity);
        verify(activity).tick();
        TickEvent event = (TickEvent) popEvent();
        assertEquals(activity, event.getActivity());
        assertEquals(1, scheduler.getRepeatingCommands().size());
    }


    @Test
    public void stop()
    {
        cut.stop();
        assertFalse(cut.running);
        assertNull(cut.activity);
    }


    @Test
    public void update()
    {
        Activity activity = td.newActivity();
        cut.update(activity);
        assertSame(activity, cut.activity);
    }


    // ----------------------------------------------------------------- events

    @Override
    public void onTick(TickEvent event)
    {
        pushEvent(event);
    }
}
