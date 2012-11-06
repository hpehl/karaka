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
package name.pehl.karaka.server.settings.control;

import name.pehl.karaka.server.settings.entity.Settings;
import name.pehl.karaka.server.settings.entity.User;

import javax.enterprise.inject.Produces;

/**
 * @author Harald Pehl
 * @date 11/06/2012
 */
public class DefaultSettingsProducer
{
    @Produces
    @DefaultSettings
    Settings getDefaultSettings()
    {
        Settings settings = new Settings();
        settings.setFormatHoursAsFloatingPointNumber(false);
        User user = new User("_unknown_user_id_", "_unknown_username_", "_unknown_email_");
        settings.setUser(user);
        return settings;
    }
}
