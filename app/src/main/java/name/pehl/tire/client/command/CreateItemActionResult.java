package name.pehl.tire.client.command;

import com.gwtplatform.dispatch.shared.Result;

public class CreateItemActionResult implements Result
{
    private final boolean success;


    public CreateItemActionResult(final boolean success)
    {
        super();
        this.success = success;
    }


    public boolean isSuccess()
    {
        return success;
    }
}
