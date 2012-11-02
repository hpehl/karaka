package name.pehl.karaka.client.client;

import java.util.List;

import name.pehl.karaka.shared.model.Client;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch
public class GetClients
{
    @Out(1) List<Client> clients;
}
