/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package name.pehl.karaka.server.activity.entity;

import com.googlecode.objectify.Key;
import name.pehl.karaka.server.tag.entity.Tag;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;
import static name.pehl.karaka.shared.model.Status.RUNNING;
import static name.pehl.karaka.shared.model.Status.STOPPED;

/**
 * @author Harald Pehl
 * @date 11/15/2012
 */
public class ActivityTest
{
    Activity cut;

    @Before
    public void setUp()
    {
        cut = new Activity("test", DateTimeZone.getDefault());
    }

    @Test
    public void newInstance()
    {
        assertContract(cut);
        assertDefaultValues(cut);
    }

    @Test
    public void copy()
    {
        cut.start();
        Activity copy = cut.copy();

        assertContract(copy);
        assertEquals(STOPPED, copy.getStatus());
    }

    @Test(expected = IllegalArgumentException.class)
    public void copyWithNullPeriod()
    {
        cut.copy(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void copyEmptyNullPeriod()
    {
        cut.copy("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void copyWithIllegalPeriod()
    {
        cut.copy("bang");
    }

    @Test
    public void copyWithPeriod()
    {
        Activity copy = cut.copy("P1D");

        assertContract(copy);
        assertEquals(1, Days.daysBetween(cut.getStart(), copy.getStart()).getDays());
        assertEquals(1, Days.daysBetween(cut.getEnd(), copy.getEnd()).getDays());
    }

    @Test
    public void start()
    {
        cut.start();
        assertContract(cut);
        assertEquals(RUNNING, cut.getStatus());
    }

    @Test
    public void resume()
    {
        cut.resume();
        assertContract(cut);
        assertEquals(RUNNING, cut.getStatus());
    }

    @Test
    public void resumeFuture()
    {
        Time now = new Time();
        Time start = plusMinutes(10);
        Time end = plusMinutes(20);
        cut.setEnd(end);
        cut.setStart(start);
        cut.resume();

        assertContract(cut);
        assertTrue(cut.getStart().isBefore(start));
        assertTrue(cut.getEnd().isBefore(end));
        assertEquals(RUNNING, cut.getStatus());
    }

    @Test
    public void tickOnStopped()
    {
        Time end = cut.getEnd();
        cut.tick();

        assertContract(cut);
        assertDefaultValues(cut);
        assertSame(end, cut.getEnd());
    }

    @Test
    public void tickOnRunning()
    {
        Time end = cut.getEnd();
        cut.start();
        cut.tick();

        assertContract(cut);
        assertNotSame(end, cut.getEnd());
        assertEquals(RUNNING, cut.getStatus());
    }

    @Test
    public void stop()
    {
        cut.stop();
        assertContract(cut);
        assertDefaultValues(cut);
    }

    @Test
    public void isToday()
    {
        assertTrue(cut.isToday());
        cut.setStart(plusMinutes(24 * 60 * -1));
        assertFalse(cut.isToday());
    }

    @Test
    public void setNullStart()
    {
        cut.setStart(null);
        assertContract(cut);
        assertDefaultValues(cut);
    }

    @Test
    public void setFutureStart()
    {
        cut.setStart(plusMinutes(10));
        assertContract(cut);
        assertDefaultValues(cut);
    }

    @Test
    public void setStart()
    {
        Time start = plusMinutes(-10);
        cut.setStart(start);
        assertContract(cut);
        assertEquals(start, cut.getStart());
    }

    @Test
    public void setNullEnd()
    {
        cut.setEnd(null);
        assertContract(cut);
        assertDefaultValues(cut);
    }

    @Test
    public void setPastEnd()
    {
        cut.setEnd(plusMinutes(-10));
        assertContract(cut);
        assertDefaultValues(cut);
    }

    @Test
    public void setEnd()
    {
        Time end = plusMinutes(10);
        cut.setEnd(end);
        assertContract(cut);
        assertEquals(end, cut.getEnd());
    }

    @Test
    public void setNegativePause()
    {
        cut.setPause(-1);
        assertContract(cut);
        assertDefaultValues(cut);
    }

    @Test
    public void setPause()
    {
        cut.setEnd(plusMinutes(10));
        cut.setPause(5);
        assertContract(cut);
        assertEquals(5, cut.getPause());
        cut.setPause(11);
        assertEquals(5, cut.getPause());
    }

    @Test
    public void duration()
    {
        cut.setEnd(plusMinutes(10));
        assertContract(cut);
        assertEquals(10, cut.getDuration());
        cut.setPause(5);
        assertEquals(5, cut.getDuration());
    }

    @Test
    public void setTags()
    {
        List<Key<Tag>> tags = new ArrayList<Key<Tag>>();
        tags.add(Key.<Tag>create("agtzfmthcmFrYS1kOHIKCxIDVGFnGMM-DKIBFTEwOTk3NTg1NDcyNjc4OTE3Mzc4NQ"));
        cut.setTags(tags);
        tags.clear();

        // assure tags are passed by value rather than by reference
        assertContract(cut);
        assertEquals(1, cut.getTags().size());
    }

    void assertContract(Activity activity)
    {
        assertNotNull(activity.getStart());
        assertNotNull(activity.getEnd());
        assertFalse(activity.getStart().isAfter(activity.getEnd()));
        assertTrue(activity.getDuration() >= 0);
        assertTrue(activity.getPause() >= 0);
        assertNotNull(activity.getStatus());
        assertNotNull(activity.getTags());
    }

    void assertDefaultValues(Activity activity)
    {
        assertEquals(STOPPED, activity.getStatus());
        assertEquals(0, activity.getDuration());
        assertEquals(0, activity.getPause());
        assertNull(activity.getProject());
        assertTrue(activity.getTags().isEmpty());
    }

    Time plusMinutes(final int minutes)
    {
        return new Time(new DateTime().plusMinutes(minutes));
    }
}
