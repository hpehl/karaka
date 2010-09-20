package name.pehl.tire.client.command.impl;

import com.google.gwt.core.client.GWT;

public abstract class BaseActionHandler
{
    protected String getItemsManagerBaseUrl()
    {
        return GWT.getModuleBaseURL() + "itemsManager";
    }
}
