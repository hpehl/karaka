package name.pehl.tire.server.sampledata;

import java.util.Random;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class RandomProducer
{
    private Random random = new Random(System.currentTimeMillis());


    @Produces
    Random getRandom()
    {
        return random;
    }
}
