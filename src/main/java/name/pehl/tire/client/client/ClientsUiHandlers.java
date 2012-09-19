package name.pehl.tire.client.client;

import name.pehl.tire.client.client.ClientAction.Action;
import name.pehl.tire.shared.model.Client;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-23 14:58:34 +0100 (Do, 23. Dez 2010) $ $Revision: 169
 *          $
 */
public interface ClientsUiHandlers extends UiHandlers
{
    void onClientAction(Action action, Client client);
}
