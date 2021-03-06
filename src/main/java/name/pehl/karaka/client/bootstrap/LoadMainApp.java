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
package name.pehl.karaka.client.bootstrap;

import com.google.gwt.user.client.Command;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import name.pehl.karaka.client.activity.presenter.ActivityController;

import java.util.Iterator;

/**
 * @author Harald Pehl
 * @date 11/22/2012
 */
public class LoadMainApp extends BootstrapStep
{
    private final PlaceManager placeManager;
    private final ActivityController activityController;


    @Inject
    public LoadMainApp(final PlaceManager placeManager, final ActivityController activityController)
    {
        this.placeManager = placeManager;
        this.activityController = activityController;
    }

    @Override
    public void execute(final Iterator<BootstrapStep> iterator, final Command command)
    {
        placeManager.revealCurrentPlace();
        activityController.init();
        next(iterator, command);
    }
}
