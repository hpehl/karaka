package name.pehl.tire.client.client;

import java.util.List;

import name.pehl.tire.shared.model.Client;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch
public class GetClients
{
    @Out(1) List<Client> clients;
}
