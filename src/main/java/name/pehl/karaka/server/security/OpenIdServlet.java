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
package name.pehl.karaka.server.security;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Harald Pehl
 * @date 11/06/2012
 */
public class OpenIdServlet extends HttpServlet
{
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException
    {
        String cont = req.getParameter("continue");
        if (cont == null)
        {
            cont = req.getRequestURI();
        }
        String identifier = req.getParameter("openid_identifier");
        if (identifier != null)
        {
            UserService userService = UserServiceFactory.getUserService();
            String redirect = userService.createLoginURL(cont, null, identifier, null);
            log("OpenIdServlet: Redirect to " + redirect);
            resp.sendRedirect(redirect);
        }
        else
        {
            String forward = new StringBuilder().append("/openid.html?=continue").append(cont).toString();
            RequestDispatcher dispatcher = req.getRequestDispatcher(forward);
            log("OpenIdServlet: Forward to " + forward);
            dispatcher.forward(req, resp);
        }
    }
}
