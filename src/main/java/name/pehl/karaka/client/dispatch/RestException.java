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
package name.pehl.karaka.client.dispatch;

import org.fusesource.restygwt.client.Method;

/**
 * @author Harald Pehl
 * @date 11/30/2012
 */
public class RestException extends RuntimeException
{
    private final Method method;
    private final int statusCode;

    public RestException(final Method method)
    {
        this(method, null, -1);
    }

    public RestException(final Method method, final String text)
    {
        this(method, text, -1);
    }

    public RestException(final Method method, final String text, final int statusCode)
    {
        super(text);
        this.method = method;
        this.statusCode = statusCode;
    }

    public Method getMethod()
    {
        return method;
    }

    public int getStatusCode()
    {
        return statusCode;
    }
}
