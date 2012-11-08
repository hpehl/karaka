package name.pehl.karaka.client.bootstrap;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BootstrapCommand
{
    void execute(AsyncCallback<Boolean> callback);
}
