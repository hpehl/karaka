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
package name.pehl.karaka.shared.model;

import com.google.common.collect.HashMultimap;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Harald Pehl
 * @date 11/30/2012
 */
public class LinksParser
{
    public static List<Link> valueOf(String value)
    {
        Parser parser = new Parser(value);
        parser.parse();
        return parser.getLinks();
    }


    // Borrowed from LinkHeaderDelegate.Parser, but parses only link and rel
    private static class Parser
    {
        private int curr;
        private String value;
        private List<Link> links = new LinkedList<Link>();


        public Parser(String value)
        {
            this.value = value;
        }

        public List<Link> getLinks()
        {
            return links;
        }

        public void parse()
        {
            String href = null;
            //MultivaluedMap<String, String> attributes = new MultivaluedMapImpl<String, String>();
            HashMultimap<String, String> attributes = HashMultimap.create();
            while (curr < value.length())
            {
                char c = value.charAt(curr);
                if (c == '<')
                {
                    if (href != null)
                    {
                        throw new IllegalArgumentException(
                                "Unable to parse Link header. Too many links in declaration: " + value);
                    }
                    href = parseLink();
                }
                else if (c == ';' || c == ' ')
                {
                    curr++;
                    continue;
                }
                else if (c == ',')
                {
                    populateLink(href, attributes);
                    href = null;
                    attributes = HashMultimap.create();
                    curr++;
                }
                else
                {
                    parseAttribute(attributes);
                }
            }
            populateLink(href, attributes);
        }

        protected void populateLink(String href, HashMultimap<String, String> attributes)
        {
            Set<String> rels = attributes.get("rel");
            for (String rel : rels)
            {
                Link link = new Link(rel, href);
                links.add(link);
            }
        }

        public String parseLink()
        {
            int end = value.indexOf('>', curr);
            if (end == -1)
            {
                throw new IllegalArgumentException("Unable to parse Link header.  No end to link: " + value);
            }
            String href = value.substring(curr + 1, end);
            curr = end + 1;
            return href;
        }

        public void parseAttribute(HashMultimap<String, String> attributes)
        {
            int end = value.indexOf('=', curr);
            if (end == -1 || end + 1 >= value.length())
            { throw new IllegalArgumentException("Unable to parse Link header.  No end to parameter: " + value); }
            String name = value.substring(curr, end);
            name = name.trim();
            curr = end + 1;
            String val;
            if (curr >= value.length())
            {
                val = "";
            }
            else
            {
                if (value.charAt(curr) == '"')
                {
                    if (curr + 1 >= value.length())
                    {
                        throw new IllegalArgumentException(
                                "Unable to parse Link header.  No end to parameter: " + value);
                    }
                    curr++;
                    end = value.indexOf('"', curr);
                    if (end == -1)
                    {
                        throw new IllegalArgumentException(
                                "Unable to parse Link header.  No end to parameter: " + value);
                    }
                    val = value.substring(curr, end);
                    curr = end + 1;
                }
                else
                {
                    StringBuffer buf = new StringBuffer();
                    while (curr < value.length())
                    {
                        char c = value.charAt(curr);
                        if (c == ',' || c == ';') { break; }
                        buf.append(value.charAt(curr));
                        curr++;
                    }
                    val = buf.toString();
                }
            }
            attributes.put(name, val);
        }
    }
}