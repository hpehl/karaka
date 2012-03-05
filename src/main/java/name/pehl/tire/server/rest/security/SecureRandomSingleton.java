package name.pehl.tire.server.rest.security;

import java.security.SecureRandom;

import com.google.inject.Singleton;

/**
 * Singelton {@link SecureRandom}.
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-10-19 13:55:11 +0200 (Di, 19. Okt 2010) $ $Revision: 180 $
 */
@Singleton
public class SecureRandomSingleton extends SecureRandom
{
    private static final long serialVersionUID = 462441711297897572L;
}
