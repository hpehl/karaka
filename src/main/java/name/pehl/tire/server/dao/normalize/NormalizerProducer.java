package name.pehl.tire.server.dao.normalize;

import javax.enterprise.inject.Produces;

public class NormalizerProducer
{
    @Produces
    @TireNormalizer
    Normalizer getNormalizer()
    {
        return new CompundNormalizer(new ToLowerCaseNormalizer(), new TrimNormalizer(), new RemoveNormalizer(
                "^!\"§$%&/()=?*+~'#_-:.;,<>|@€"));
    }
}
