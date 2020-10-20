package com.swustacm.poweroj.config.shiro;


import com.swustacm.poweroj.config.redis.RedisManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import java.util.*;

/**
 * @author zcy
 * @param <K>
 * @param <V>
 */
@Slf4j
public class RedisCache<K,V> implements Cache<K,V> {
    private RedisManager redisManager;
    private String keyPrefix = "";
    private int expire = 0;
    private String principalIdFieldName = RedisCacheManager.DEFAULT_PRINCIPAL_ID_FIELD_NAME;
    /**
     * Construction
     * @param redisManager
     */
    public RedisCache(RedisManager redisManager, String prefix, int expire, String principalIdFieldName) {
        if (redisManager == null) {
            throw new IllegalArgumentException("redisManager cannot be null.");
        }
        this.redisManager = redisManager;
        if (prefix != null && !"".equals(prefix)) {
            this.keyPrefix = prefix;
        }
        if (expire != -1) {
            this.expire = expire;
        }
        if (principalIdFieldName != null && !"".equals(principalIdFieldName)) {
            this.principalIdFieldName = principalIdFieldName;
        }
    }
    @Override
    public V get(K key) throws CacheException {
        log.info("get key [{}]",key);
        if (key == null) {
            return null;
        }
        try {
            String redisCacheKey = getRedisCacheKey(key);
            Object rawValue = redisManager.get(redisCacheKey);
            if (rawValue == null) {
                return null;
            }
            V value = (V) rawValue;
            return value;
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }
    @Override
    public V put(K key, V value) throws CacheException {
        log.debug("put key [{}]",key);
        if (key == null) {
           log.warn("Saving a null key is meaningless, return value directly without call Redis.");
            return value;
        }
        try {
            String redisCacheKey = getRedisCacheKey(key);
            redisManager.set(redisCacheKey, value, expire);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public V remove(K key) throws CacheException {
        log.debug("remove key [{}]",key);
        if (key == null) {
            return null;
        }
        try {
            String redisCacheKey = getRedisCacheKey(key);
            Object rawValue = redisManager.get(redisCacheKey);
            V previous = (V) rawValue;
            redisManager.del(redisCacheKey);
            return previous;
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }
    private String getRedisCacheKey(K key) {
        if (key == null) {
            return null;
        }
        return this.keyPrefix + getStringRedisKey(key);
    }
    private String getStringRedisKey(K key) {
        String redisKey;
        if (key instanceof PrincipalCollection) {
            redisKey = getRedisKeyFromPrincipalIdField((PrincipalCollection) key);
        } else {
            redisKey = key.toString();
        }
        return redisKey;
    }
    private String getRedisKeyFromPrincipalIdField(PrincipalCollection key) {
        if (key == null) {
            throw new PrincipalInstanceException(key.getClass(), this.principalIdFieldName);
        }
        return key.toString();
    }

    @Override
    public void clear() throws CacheException {
        log.debug("clear cache");
        Set<String> keys = null;
        try {
            keys = redisManager.scan(this.keyPrefix + "*");
        } catch (Exception e) {
            log.error("get keys error", e);
        }
        if (keys == null || keys.size() == 0) {
            return;
        }
        for (String key: keys) {
            redisManager.del(key);
        }
    }
    @Override
    public int size() {
        Long longSize = 0L;
        try {
            longSize = new Long(redisManager.scanSize(this.keyPrefix + "*"));
        } catch (Exception e) {
            log.error("get keys error", e);
        }
        return longSize.intValue();
    }
    @SuppressWarnings("unchecked")
    @Override
    public Set<K> keys() {
        Set<String> keys = null;
        try {
            keys = redisManager.scan(this.keyPrefix + "*");
        } catch (Exception e) {
            log.error("get keys error", e);
            return Collections.emptySet();
        }
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        }
        Set<K> convertedKeys = new HashSet<K>();
        for (String key:keys) {
            try {
                convertedKeys.add((K) key);
            } catch (Exception e) {
                log.error("deserialize keys error", e);
            }
        }
        return convertedKeys;
    }
    @Override
    public Collection<V> values() {
        Set<String> keys = null;
        try {
            keys = redisManager.scan(this.keyPrefix + "*");
        } catch (Exception e) {
            log.error("get values error", e);
            return Collections.emptySet();
        }
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        }
        List<V> values = new ArrayList<V>(keys.size());
        for (String key : keys) {
            V value = null;
            try {
                value = (V) redisManager.get(key);
            } catch (Exception e) {
                log.error("deserialize values= error", e);
            }
            if (value != null) {
                values.add(value);
            }
        }
        return Collections.unmodifiableList(values);
    }
    public String getKeyPrefix() {
        return keyPrefix;
    }
    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
    public String getPrincipalIdFieldName() {
        return principalIdFieldName;
    }
    public void setPrincipalIdFieldName(String principalIdFieldName) {
        this.principalIdFieldName = principalIdFieldName;
    }
}
