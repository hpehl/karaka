package name.pehl.tire.server.sampledata;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import name.pehl.tire.server.client.entity.Client;

class ClientProducer
{
    static String[] names = new String[] {"ACME", "FooBar", "United", "Blabla"};
    @Inject LoremIpsum loremIpsum;


    @Produces
    public List<Client> produceClients()
    {
        List<Client> clients = new ArrayList<Client>();
        for (String name : names)
        {
            Client client = new Client(name, loremIpsum.randomWords(3));
            clients.add(client);
        }
        return clients;
    }
}
