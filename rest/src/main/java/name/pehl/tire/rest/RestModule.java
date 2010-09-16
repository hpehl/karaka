package name.pehl.tire.server.rest;

import name.pehl.tire.server.project.ProjectsResource;

import com.google.inject.AbstractModule;

/**
 * @author $Author: harald.pehl $
 * @version $Revision: 41 $
 */
public class RestModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(ProjectsResource.class);
    }
}
