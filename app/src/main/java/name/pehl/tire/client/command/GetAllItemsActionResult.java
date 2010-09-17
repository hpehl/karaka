package name.pehl.tire.client.command;

import name.pehl.tire.client.model.ItemsModel;

import com.gwtplatform.dispatch.shared.Result;

public class GetAllItemsActionResult implements Result
{
    private final ItemsModel items;


    public GetAllItemsActionResult(final ItemsModel items)
    {
        super();
        this.items = items;
    }


    public ItemsModel getItems()
    {
        return items;
    }
}
