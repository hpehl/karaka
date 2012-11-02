package name.pehl.karaka.shared.model;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class YearsTest
{
    @Test
    public void newInstance()
    {
        Years cut = new Years();
        assertTrue(cut.getYears().isEmpty());
    }
}
