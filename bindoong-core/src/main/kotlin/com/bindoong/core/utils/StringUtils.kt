package com.bindoong.core.utils

import org.apache.commons.codec.binary.Base64
import java.nio.ByteBuffer
import java.util.UUID
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

    fun UUID.toBase64String(): String {
        val bb: ByteBuffer = ByteBuffer.wrap(ByteArray(16))
        bb.putLong(this.mostSignificantBits)
        bb.putLong(this.leastSignificantBits)
        return Base64.encodeBase64URLSafeString(bb.array())
    }

    fun String.toBase64Uuid(): UUID {
        val base64 = Base64()
        val bytes: ByteArray = base64.decode(this)
        val bb = ByteBuffer.wrap(bytes)
        return UUID(bb.long, bb.long)
    }
}
