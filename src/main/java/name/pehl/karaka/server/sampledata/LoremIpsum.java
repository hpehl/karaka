package name.pehl.karaka.server.sampledata;

import java.util.Random;

import javax.inject.Inject;

/**
 * Simple lorem ipsum text generator.
 * 
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class LoremIpsum
{
    public static final String LOREM_IPSUM = "lorem ipsum dolor sit amet consetetur sadipscing elitr sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat sed diam voluptua at vero eos et accusam et justo duo dolores et ea rebum stet clita kasd gubergren no sea takimata sanctus est lorem ipsum dolor sit amet";

    final String[] words;
    @Inject Random random;


    public LoremIpsum()
    {
        this.words = LOREM_IPSUM.split(" ");
    }


    /**
     * Returns words from the lorem ipsum text.
     * 
     * @param amount
     *            Amount of words
     * @return Lorem ipsum text
     */
    public String words(int amount)
    {
        return words(amount, 0);
    }


    public String randomWords(int amount)
    {
        return words(amount, random.nextInt(50));
    }


    /**
     * Returns words from the lorem ipsum text.
     * 
     * @param amount
     *            Amount of words
     * @param startIndex
     *            Start index of word to begin with (must be >= 0 and < 50)
     * @return Lorem ipsum text
     * @throws IndexOutOfBoundsException
     *             If startIndex is < 0 or > 49
     */
    public String words(int amount, int startIndex)
    {
        if (startIndex < 0 || startIndex > 49)
        {
            throw new IndexOutOfBoundsException("startIndex must be >= 0 and < 50");
        }

        int word = startIndex;
        StringBuilder lorem = new StringBuilder();

        for (int i = 0; i < amount; i++)
        {
            if (word == 50)
            {
                word = 0;
            }
            lorem.append(words[word]);
            if (i < amount - 1)
            {
                lorem.append(' ');
            }
            word++;
        }
        if (lorem.length() > 1)
        {
            return lorem.substring(0, 1).toUpperCase() + lorem.substring(1);
        }
        return lorem.toString();
    }
}
