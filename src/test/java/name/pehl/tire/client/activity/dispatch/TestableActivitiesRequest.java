package name.pehl.tire.client.activity.dispatch;

import name.pehl.tire.client.rest.TestableUrlBuilder;
import name.pehl.tire.client.rest.UrlBuilder;

import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class TestableActivitiesRequest extends ActivitiesRequest
{
    public TestableActivitiesRequest(String url)
    {
        super(url);
    }


    public TestableActivitiesRequest(PlaceRequest placeRequest)
    {
        this(placeRequest, new TestableUrlBuilder().module("rest").path("activities"));
    }


    public TestableActivitiesRequest(PlaceRequest placeRequest, UrlBuilder urlBuilder)
    {
        super(placeRequest, urlBuilder);
    }
}
