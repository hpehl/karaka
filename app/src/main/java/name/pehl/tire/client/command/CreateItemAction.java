package name.pehl.tire.client.command;

import com.gwtplatform.dispatch.shared.ActionImpl;

public class CreateItemAction extends ActionImpl<CreateItemActionResult>
{
    private final String itemName;


    public CreateItemAction(final String itemName)
    {
        super();
        this.itemName = itemName;
    }


    public String getItemName()
    {
        return itemName;
    }
}
