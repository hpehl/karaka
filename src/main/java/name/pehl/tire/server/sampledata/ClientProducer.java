package name.pehl.tire.server.sampledata;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import name.pehl.tire.server.client.entity.Client;

class ClientProducer
{
    @Inject
    IdGenerator idGenerator;

    @Inject
    RandomString randomString;


    @Produces
    @Count
    public List<Client> produceClients(InjectionPoint ip)
    {
        List<Client> clients = new ArrayList<Client>();
        Count count = ip.getAnnotated().getAnnotation(Count.class);
        if (count != null && count.value() > 0)
        {
            for (int i = 0; i < count.value(); i++)
            {
                Client client = new Client("Client " + randomString.next(5), randomString.next(10));
                clients.add(client);
            }
        }
        return clients;
    }
}
