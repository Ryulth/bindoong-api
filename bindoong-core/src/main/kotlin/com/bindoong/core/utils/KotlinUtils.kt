package com.bindoong.core.utils

import java.util.Optional

object KotlinUtils {
    fun <T : Any> Optional<T>.toNullable(): T? = this.orElse(null)
}