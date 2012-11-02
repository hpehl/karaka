package name.pehl.karaka.server.cache;

import static com.google.appengine.api.memcache.jsr107cache.GCacheFactory.EXPIRATION_DELTA;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.jboss.resteasy.plugins.cache.server.ServerCache;
import org.jboss.resteasy.plugins.cache.server.ServerCacheHitInterceptor;
import org.jboss.resteasy.plugins.cache.server.ServerCacheInterceptor;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

public class AppEngineCache implements ServerCache
{
    public static class CacheEntry implements Entry, Serializable
    {
        private final byte[] cached;
        private final int expires;
        private final long timestamp = System.currentTimeMillis();
        private final transient MultivaluedMap<String, Object> headers;
        private final String etag;
        private final String mediaType;
        private final String mediaSubtype;


        private CacheEntry(final MultivaluedMap<String, Object> headers, final byte[] cached, final int expires,
                final String etag, final String mediaType, final String mediaSubtype)
        {
            this.cached = cached;
            this.expires = expires;
            this.headers = headers;
            this.etag = etag;
            this.mediaType = mediaType;
            this.mediaSubtype = mediaSubtype;
        }


        @Override
        public int getExpirationInSeconds()
        {
            return expires - (int) ((System.currentTimeMillis() - timestamp) / 1000);
        }


        @Override
        public boolean isExpired()
        {
            return System.currentTimeMillis() - timestamp >= expires * 1000;
        }


        @Override
        public String getEtag()
        {
            return etag;
        }


        @Override
        public MultivaluedMap<String, Object> getHeaders()
        {
            return headers;
        }


        @Override
        public byte[] getCached()
        {
            return cached;
        }


        public String getMediaType()
        {
            return mediaType;
        }


        public String getMediaSubtype()
        {
            return mediaSubtype;
        }
    }

    Cache cache;
    int maxSize = 1000;
    int expiration = 36000; // in seconds = 10h
    ResteasyProviderFactory providerFactory;


    @SuppressWarnings({"rawtypes", "unchecked"})
    public void start()
    {
        try
        {
            Map props = new HashMap();
            props.put(EXPIRATION_DELTA, expiration);
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            cache = cacheFactory.createCache(props);
        }
        catch (CacheException e)
        {
            throw new RuntimeException("Cache cannot be created: " + e.getMessage(), e);
        }

        ServerCacheHitInterceptor hit = new ServerCacheHitInterceptor(this);
        ServerCacheInterceptor interceptor = new ServerCacheInterceptor(this);
        providerFactory.getServerPreProcessInterceptorRegistry().register(hit);
        providerFactory.getServerMessageBodyWriterInterceptorRegistry().register(interceptor);
    }


    @Override
    @SuppressWarnings("unchecked")
    public Entry add(final String uri, final MediaType mediaType, final CacheControl cc,
            final MultivaluedMap<String, Object> headers, final byte[] entity, final String etag)
    {
        CacheEntry cacheEntry = new CacheEntry(headers, entity, cc.getMaxAge(), etag, mediaType.getType(),
                mediaType.getSubtype());
        if (cache.size() < maxSize)
        {
            cache.put(uri, cacheEntry);
        }
        return cacheEntry;
    }


    @Override
    public Entry get(final String uri, final MediaType accept)
    {
        CacheEntry entry = (CacheEntry) cache.get(uri);
        if (entry != null)
        {
            MediaType cacheMediaType = new MediaType(entry.getMediaType(), entry.getMediaSubtype());
            if (accept.isCompatible(cacheMediaType))
            {
                return entry;
            }
        }
        return null;
    }


    @Override
    public void remove(final String uri)
    {
        cache.remove(uri);
    }


    @Override
    public void clear()
    {
        cache.clear();
    }


    public void setMaxSize(final int maxSize)
    {
        this.maxSize = maxSize;
    }


    public void setExpiration(final int expiration)
    {
        this.expiration = expiration;
    }


    public void setProviderFactory(final ResteasyProviderFactory providerFactory)
    {
        this.providerFactory = providerFactory;
    }
}
