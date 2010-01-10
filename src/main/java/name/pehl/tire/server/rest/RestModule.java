package name.pehl.tire.server.rest;

import name.pehl.tire.server.project.ProjectsResource;

import com.google.inject.AbstractModule;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public class RestModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(ProjectsResource.class);
    }
}
