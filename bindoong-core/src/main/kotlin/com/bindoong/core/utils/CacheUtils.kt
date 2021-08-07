package com.bindoong.core.utils

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import java.time.Duration
import java.util.concurrent.TimeUnit

object CacheUtils {
    fun <T : Any> newSingleLoadingCache(function: () -> T, duration: Duration): SingleLoadingCache<T> {
        return SingleLoadingCache(
            CacheBuilder.newBuilder()
                .expireAfterWrite(duration.seconds, TimeUnit.SECONDS)
                .build(CacheLoader.from(function))
        )
    }
}
