package name.pehl.tire.server.config;

import java.util.Random;

import javax.inject.Inject;

public class RandomString
{
    @Inject
    Random random;


    public String next(int length)
    {
        int start = 'a';
        int end = 'z';
        int gap = end - start;

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++)
        {
            char c = (char) (random.nextInt(gap) + start);
            builder.append(c);
        }
        return builder.toString();
    }
}
