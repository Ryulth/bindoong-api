package com.bindoong.core.utils

import com.google.common.cache.LoadingCache
import java.util.concurrent.ExecutionException

class SingleLoadingCache<T : Any>(
    private val loadingCache: LoadingCache<Boolean, T>
) {
    @Throws(ExecutionException::class)
    fun get(): T {
        return loadingCache[true]
    }

    fun invalidate() {
        loadingCache.invalidateAll()
    }

    val unchecked: T
        get() = loadingCache.getUnchecked(true)
}