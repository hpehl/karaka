package name.pehl.karaka.server.sampledata;

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
