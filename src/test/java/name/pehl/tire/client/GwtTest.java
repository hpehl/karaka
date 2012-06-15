package name.pehl.tire.client;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;

import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GWT.class, URL.class})
public abstract class GwtTest
{
    @Before
    public void gwtTestSetUp()
    {
        mockStatic(GWT.class);
        mockStatic(URL.class);
        when(GWT.getHostPageBaseURL()).thenReturn("http://localhost");
        when(URL.encode(Mockito.anyString())).thenAnswer(new Answer<String>()
        {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable
            {
                return String.valueOf(invocation.getArguments()[0]);
            }
        });
    }
}
