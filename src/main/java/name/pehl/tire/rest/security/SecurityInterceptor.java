package name.pehl.taoki.security;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;

/**
 * Method interceptor for {@link ServerResource}s which invokes
 * {@link SecurityCheck#check(org.restlet.Request, org.restlet.Response)} before
 * the actual method.
 * 
 * @author $Author: harald.pehl $
 * @version $Revision: 180 $
 */
public class SecurityInterceptor implements MethodInterceptor
{
    private SecurityCheck securityCheck;


    @Inject
    public void setSecurityCheck(SecurityCheck securityCheck)
    {
        this.securityCheck = securityCheck;
    }


    protected SecurityCheck getSecurityCheck()
    {
        return securityCheck;
    }


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable
    {
        ServerResource resource = (ServerResource) invocation.getThis();
        securityCheck.check(resource.getRequest(), resource.getResponse());
        return invocation.proceed();
    }
}
