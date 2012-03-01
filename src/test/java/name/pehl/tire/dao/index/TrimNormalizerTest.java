package name.pehl.tire.dao.index;

import static org.junit.Assert.*;

import name.pehl.tire.dao.normalize.Normalizer;
import name.pehl.tire.dao.normalize.TrimNormalizer;

import org.junit.Before;
import org.junit.Test;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-05 00:40:48 +0100 (Fr, 05. Nov 2010) $ $Revision: 139 $
 */
public class TrimNormalizerTest
{
    private Normalizer normalizer;


    @Before
    public void setUp()
    {
        normalizer = new TrimNormalizer();
    }


    @Test
    public void normalizeNull()
    {
        assertNull(normalizer.normalize(null));
    }


    @Test
    public void normalizeEmpty()
    {
        assertEquals("", normalizer.normalize(""));
    }


    @Test
    public void normalizeBlank()
    {
        assertEquals("", normalizer.normalize("    "));
    }


    @Test
    public void normalizeTrim()
    {
        assertEquals("foo", normalizer.normalize("  foo "));
    }


    @Test
    public void normalize()
    {
        assertEquals("1 2 3 4 5 6", normalizer.normalize("  1 \f  2 3   \t\t4\n5  \r\r\r  6   "));
    }
}
