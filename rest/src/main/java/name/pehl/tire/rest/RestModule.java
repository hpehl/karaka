package name.pehl.tire.rest;

import name.pehl.taoki.security.CookieSecurityCheck;
import name.pehl.taoki.security.HeaderSecurityTokenReader;
import name.pehl.taoki.security.RandomSecurityTokenGenerator;
import name.pehl.taoki.security.SecurityCheck;
import name.pehl.taoki.security.SecurityToken;
import name.pehl.taoki.security.SecurityTokenGenerator;
import name.pehl.taoki.security.SecurityTokenReader;
import name.pehl.tire.rest.activity.ActivitiesResource;
import name.pehl.tire.rest.activity.ActivityResource;

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
        // Security stuff
        bindConstant().annotatedWith(SecurityToken.class).to("TST");
        bind(SecurityCheck.class).to(CookieSecurityCheck.class);
        bind(SecurityTokenGenerator.class).to(RandomSecurityTokenGenerator.class);
        bind(SecurityTokenReader.class).to(HeaderSecurityTokenReader.class);

        // bind resources
        bind(ActivityResource.class);
        bind(ActivitiesResource.class);
    }
}
