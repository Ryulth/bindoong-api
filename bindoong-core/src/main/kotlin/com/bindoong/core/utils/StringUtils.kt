package com.bindoong.core.utils

import java.util.regex.Pattern

object StringUtils {
    private val EMAIL_PATTERN = Pattern.compile(
        "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$", Pattern.CASE_INSENSITIVE
    )

    fun isEmailPattern(s: String): Boolean {
        return if (s.isNotEmpty()) {
            EMAIL_PATTERN.matcher(s).matches()
        } else true
    }
}