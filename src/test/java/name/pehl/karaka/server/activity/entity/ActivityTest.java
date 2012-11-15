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
import org.joda.time.DateTimeZone;
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
        assertNotNull(cut.getStart());
        assertNotNull(cut.getEnd());
        assertEquals(0, cut.getMinutes());
        assertEquals(STOPPED, cut.getStatus());
        assertTrue(cut.getTags().isEmpty());
        assertNull(cut.getProject());
        assertFalse(cut.isBillable());
    }

    @Test
    public void start()
    {
        cut.start();
        assertNotNull(cut.getStart());
        assertNotNull(cut.getEnd());
        assertEquals(0, cut.getMinutes());
        assertEquals(RUNNING, cut.getStatus());
    }

    @Test
    public void resume()
    {
        cut.resume();
        assertNotNull(cut.getStart());
        assertNotNull(cut.getEnd());
        assertEquals(0, cut.getMinutes());
        assertEquals(RUNNING, cut.getStatus());
    }

    @Test
    public void stop()
    {
        cut.stop();
        assertNotNull(cut.getStart());
        assertNotNull(cut.getEnd());
        assertEquals(0, cut.getMinutes());
        assertEquals(STOPPED, cut.getStatus());
    }

    @Test
    public void setTags()
    {
        List<Key<Tag>> tags = new ArrayList<Key<Tag>>();
        tags.add(Key.<Tag>create("agtzfmthcmFrYS1kOHIKCxIDVGFnGMM-DKIBFTEwOTk3NTg1NDcyNjc4OTE3Mzc4NQ"));

        // assure tags are passed by value rather than by reference
        cut.setTags(tags);
        tags.clear();
        assertEquals(1, cut.getTags().size());
    }
}
