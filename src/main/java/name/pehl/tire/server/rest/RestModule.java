package name.pehl.tire.server.rest;

import name.pehl.taoki.security.CookieSecurityCheck;
import name.pehl.taoki.security.HeaderSecurityTokenReader;
import name.pehl.taoki.security.RandomSecurityTokenGenerator;
import name.pehl.taoki.security.SecurityCheck;
import name.pehl.taoki.security.SecurityToken;
import name.pehl.taoki.security.SecurityTokenGenerator;
import name.pehl.taoki.security.SecurityTokenReader;
import name.pehl.tire.server.rest.activity.ActivitiesResource;
import name.pehl.tire.server.rest.activity.ActivityAdapter;
import name.pehl.tire.server.rest.activity.ActivityResource;
import name.pehl.tire.server.rest.activity.TimeAdapter;
import name.pehl.tire.server.rest.client.ClientResource;
import name.pehl.tire.server.rest.client.ClientsResource;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * @author $Author: harald.pehl $
 * @version $Revision: 41 $
 */
public class RestModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        // Security stuff
        bindConstant().annotatedWith(SecurityToken.class).to("TST");
        bind(SecurityCheck.class).to(CookieSecurityCheck.class);
        bind(SecurityTokenGenerator.class).to(RandomSecurityTokenGenerator.class);
        bind(SecurityTokenReader.class).to(HeaderSecurityTokenReader.class);

        // bind resources
        bind(ActivityResource.class);
        bind(ActivitiesResource.class);
        bind(ClientResource.class);
        bind(ClientsResource.class);

        // Gson setup
        bindConstant().annotatedWith(DateTimeFormat.class).to("dd.MM.yyyy HH:mm:ss.SSS Z");
        bind(ActivityAdapter.class);
        bind(TimeAdapter.class);
        bind(Gson.class).toProvider(GsonProvider.class);

        // helper classes
        bind(EntityIdFinder.class).in(Singleton.class);
    }
}
