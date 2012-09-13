package name.pehl.tire.server.cache;

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

import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;

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


        private CacheEntry(MultivaluedMap<String, Object> headers, byte[] cached, int expires, String etag,
                String mediaType, String mediaSubtype)
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


    public void start()
    {
        try
        {
            Map<String, Object> props = new HashMap<String, Object>();
            props.put(GCacheFactory.EXPIRATION_DELTA, expiration);
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
    public Entry add(String uri, MediaType mediaType, CacheControl cc, MultivaluedMap<String, Object> headers,
            byte[] entity, String etag)
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
    public Entry get(String uri, MediaType accept)
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
    public void remove(String uri)
    {
        cache.remove(uri);
    }


    @Override
    public void clear()
    {
        cache.clear();
    }


    public void setMaxSize(int maxSize)
    {
        this.maxSize = maxSize;
    }


    public void setExpiration(int expiration)
    {
        this.expiration = expiration;
    }


    public void setProviderFactory(ResteasyProviderFactory providerFactory)
    {
        this.providerFactory = providerFactory;
    }
}
