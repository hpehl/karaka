package name.pehl.tire.server.security;

import org.restlet.resource.ServerResource;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public class SecurityModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        SecurityInterceptor interceptor = new SecurityInterceptor();
        bindInterceptor(Matchers.subclassesOf(ServerResource.class), Matchers.annotatedWith(Secured.class), interceptor);
        // bindInterceptor(Matchers.subclassesOf(ServerResource.class),
        // Matchers.annotatedWith(Get.class), interceptor);
        // bindInterceptor(Matchers.subclassesOf(ServerResource.class),
        // Matchers.annotatedWith(Post.class), interceptor);
        // bindInterceptor(Matchers.subclassesOf(ServerResource.class),
        // Matchers.annotatedWith(Put.class), interceptor);
        // bindInterceptor(Matchers.subclassesOf(ServerResource.class),
        // Matchers.annotatedWith(Delete.class), interceptor);
    }
}
