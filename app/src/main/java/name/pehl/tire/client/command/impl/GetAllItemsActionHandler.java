package name.pehl.tire.client.command.impl;

import name.pehl.tire.client.command.GetAllItemsAction;
import name.pehl.tire.client.command.GetAllItemsActionResult;
import name.pehl.tire.client.dispatch.RequestBuilderActionHandler;
import name.pehl.tire.client.model.ItemsModel;

import com.google.gwt.http.client.RequestBuilder;

public class GetAllItemsActionHandler extends BaseActionHandler implements
        RequestBuilderActionHandler<GetAllItemsAction, GetAllItemsActionResult>
{
    @Override
    public GetAllItemsActionResult extractResult(final com.google.gwt.http.client.Response response)
    {

        ItemsModel items = ItemsModel.JSON.read(response.getText());

        return new GetAllItemsActionResult(items);
    }


    @Override
    public Class<GetAllItemsAction> getActionType()
    {
        return GetAllItemsAction.class;
    }


    @Override
    public RequestBuilder getRequestBuilder(final GetAllItemsAction action)
    {
        final String url = getItemsManagerBaseUrl() + "?action=findAll";
        return new RequestBuilder(RequestBuilder.GET, url);
    };
}
