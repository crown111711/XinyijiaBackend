package com.xinyijia.backend.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheStats;
import com.xinyijia.backend.param.TokenCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/13 19:49
 */
@Slf4j
@Component
public class TokenCacheService {

    Cache<String/*accessToken */, Optional<TokenCache>> cache;
    ScheduledExecutorService singleScheduled = Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    public void init() {
        cache = CacheBuilder
                .newBuilder()
                .expireAfterAccess(1, TimeUnit.HOURS)
                .maximumSize(3000)
                .initialCapacity(1000)
                .recordStats()
                .build();
        singleScheduled = Executors.newSingleThreadScheduledExecutor();
        singleScheduled.scheduleAtFixedRate(() -> {
            CacheStats stats = cache.stats();
            log.info("hitRate:{} Count:{} Hit:{} Miss:{}", stats.hitRate(), stats.requestCount(), stats.hitCount(), stats.missCount());

        }, 30, 30, TimeUnit.SECONDS);
    }

    public TokenCache getCache(String tokenId) {
        try {
            Optional<TokenCache> tokenCache = cache.getIfPresent(tokenId);
            if (tokenCache == null) {
                return null;
            } else {
                return tokenCache.get();
            }
        } catch (Exception e) {
            log.error("从缓存中获取token失败", e);
            return null;
        }
    }

    public void putCache(String tokenId, TokenCache tokenCache) {
        cache.put(tokenId, Optional.of(tokenCache));
    }

}
