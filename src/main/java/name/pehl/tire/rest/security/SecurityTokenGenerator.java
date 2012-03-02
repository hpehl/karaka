package name.pehl.taoki.security;

/**
 * Interface for generating security tokens.
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-10-19 13:55:11 +0200 (Di, 19. Okt 2010) $ $Revision: 175
 *          $
 */
public interface SecurityTokenGenerator
{
    String generateToken();
}
