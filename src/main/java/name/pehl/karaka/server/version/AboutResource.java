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
package name.pehl.karaka.server.version;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

/**
 * @author Harald Pehl
 * @date 11/22/2012
 */
@Path("/about")
@Produces(TEXT_PLAIN)
public class AboutResource
{
    @Context ServletContext context;

    @GET
    public Response about()
    {
        InputStream inputStream = null;
        try
        {
            StringWriter writer = new StringWriter();
            inputStream = context.getResourceAsStream("/WEB-INF/about.properties");
            Properties about = new Properties();
            about.load(inputStream);
            about.list(new PrintWriter(writer));
            return Response.ok(writer).build();
        }
        catch (IOException e)
        {
            return Response.status(NOT_FOUND).build();
        }
        finally
        {
            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                }
                catch (IOException ignored)
                {
                }
            }
        }
    }
}
