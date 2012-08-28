package name.pehl.tire.client.activity.dispatch;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import com.google.common.testing.EqualsTester;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class ActivitiesRequestTest
{
    @Test
    public void newWithUrl()
    {
        ActivitiesRequest cut = new ActivitiesRequest("foo");
        assertEquals("n/a", cut.text);
        assertEquals("foo", cut.url);
    }


    @Ignore
    // TODO: Mock GWT calls caused by
    // name.pehl.tire.client.rest.UrlBuilder.defaultUrl()
    public void newWithPlaceRequest()
    {
        // blank
        ActivitiesRequest cut = new ActivitiesRequest(new PlaceRequest());
        assertEquals("current week", cut.text);
        assertEquals("http://localhost/rest/activities/currentWeek/", cut.url);

        // current
        cut = new ActivitiesRequest(new PlaceRequest().with("current", "month"));
        assertEquals("current month", cut.text);
        assertEquals("http://localhost/rest/activities/currentMonth/", cut.url);
        cut = new ActivitiesRequest(new PlaceRequest().with("current", "week"));
        assertEquals("current week", cut.text);
        assertEquals("http://localhost/rest/activities/currentWeek/", cut.url);
        cut = new ActivitiesRequest(new PlaceRequest().with("current", "day"));
        assertEquals("today", cut.text);
        assertEquals("http://localhost/rest/activities/today/", cut.url);

        // absolute
        cut = new ActivitiesRequest(new PlaceRequest().with("year", "2000").with("month", "1"));
        assertEquals("1 / 2000", cut.text);
        assertEquals("http://localhost/rest/activities/2000/1/", cut.url);
        cut = new ActivitiesRequest(new PlaceRequest().with("year", "2000").with("week", "1"));
        assertEquals("CW 1 / 2000", cut.text);
        assertEquals("http://localhost/rest/activities/2000/cw1/", cut.url);

        // error / wrong parameters
        cut = new ActivitiesRequest(new PlaceRequest().with("current", "foo"));
        assertEquals("current week", cut.text);
        assertEquals("http://localhost/rest/activities/currentWeek/", cut.url);
        cut = new ActivitiesRequest(new PlaceRequest().with("year", "2000").with("month", "foo"));
        assertEquals("foo / 2000", cut.text);
        assertEquals("http://localhost/rest/activities/2000/foo/", cut.url);
    }


    @Ignore
    // TODO: Mock GWT calls caused by
    // name.pehl.tire.client.rest.UrlBuilder.defaultUrl()
    public void equalsAndHashcode()
    {
        ActivitiesRequest blank = new ActivitiesRequest((String) null);
        new EqualsTester()
                .addEqualityGroup(blank, blank)
                .addEqualityGroup(new ActivitiesRequest("foo"), new ActivitiesRequest("foo"))
                .addEqualityGroup(new ActivitiesRequest("bar"), new ActivitiesRequest("bar"))
                .addEqualityGroup(new ActivitiesRequest(new PlaceRequest("foo")),
                        new ActivitiesRequest(new PlaceRequest("foo"))).testEquals();
    }


    @Test
    public void toStringAndUrl()
    {
        ActivitiesRequest cut = new ActivitiesRequest("foo");
        assertEquals("n/a", cut.toString());
        assertEquals("foo", cut.toUrl());
    }
}
