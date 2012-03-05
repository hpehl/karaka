package name.pehl.tire.server.rest.security;

import java.math.BigInteger;

import com.google.inject.Inject;

/**
 * A security token generator which used {@link SecureRandomSingleton} to
 * generate the token.
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-10-19 13:55:11 +0200 (Di, 19. Okt 2010) $ $Revision: 175
 *          $
 */
public class RandomSecurityTokenGenerator implements SecurityTokenGenerator
{
    private final SecureRandomSingleton srs;


    @Inject
    public RandomSecurityTokenGenerator(final SecureRandomSingleton srs)
    {
        this.srs = srs;
    }


    @Override
    public String generateToken()
    {
        return new BigInteger(130, srs).toString(32);
    }
}
