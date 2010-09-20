package name.pehl.tire.client.command.impl;

import name.pehl.tire.client.command.CreateItemAction;
import name.pehl.tire.client.command.CreateItemActionResult;
import name.pehl.tire.client.dispatch.RequestBuilderActionHandler;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;

public class CreateItemActionHandler extends BaseActionHandler implements
        RequestBuilderActionHandler<CreateItemAction, CreateItemActionResult>
{
    @Override
    public CreateItemActionResult extractResult(final Response response)
    {
        final String text = response.getText();
        boolean success;
        if ("OK".equals(text))
        {
            success = true;
        }
        else
        {
            success = false;
        }
        return new CreateItemActionResult(success);
    }


    @Override
    public Class<CreateItemAction> getActionType()
    {
        return CreateItemAction.class;
    }


    @Override
    public RequestBuilder getRequestBuilder(final CreateItemAction action)
    {
        final String url = getItemsManagerBaseUrl() + "?action=create&name=" + action.getItemName();
        return new RequestBuilder(RequestBuilder.GET, url);
    }
}
