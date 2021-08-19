package com.bindoong.web.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.auth0.jwt.interfaces.DecodedJWT
import com.bindoong.core.utils.JsonUtils.convertValueAsMap
import com.bindoong.core.utils.JsonUtils.objectMapper
import com.fasterxml.jackson.core.JsonProcessingException
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.time.Instant
import java.util.Base64
import java.util.Date

@Component
class JwtTokenProvider(
    @Value("\${jwt.public-key}") private val publicKey: String,
    @Value("\${jwt.private-key}") private val privateKey: String,
    @Value("\${jwt.refresh-key}") private val refreshKey: String,
) : TokenProvider {
    override fun createToken(userId: Long): Token {
        val accessToken = JWT.create()
            .withPayload(JwtPayload(userId).asMap())
            .withExpiresAt(Date.from(Instant.now().plusSeconds(3600)))
            .sign(Algorithm.RSA256(rsaPublicKey, rsaPrivateKey))

        val refreshToken = JWT.create()
            .withPayload(JwtPayload(userId).asMap())
            .withExpiresAt(Date.from(Instant.now().plusSeconds(3600 * 24 * 7)))
            .sign(Algorithm.HMAC512(hmacRefreshKey))

        return Token(
            accessToken = accessToken,
            type = TOKEN_PREFIX,
            refreshToken = refreshToken
        )
    }

    override fun refreshToken(refreshToken: String): Token = decodeRefreshToken(refreshToken).let {
        createToken(it.claims[CLAIMS_USER_ID]!!.asLong())
    }

    override fun getTokenPrefix(): String = TOKEN_PREFIX

    override fun getAuthentication(accessToken: String): Authentication = decodeAccessToken(accessToken).let {
        UserSession(
            userId = it.claims[CLAIMS_USER_ID]!!.asLong(),
            token = accessToken
        )
    }

    private fun decodeAccessToken(accessToken: String): DecodedJWT = tryExecute {
        JWT.require(Algorithm.RSA256(rsaPublicKey, null))
            .build()
            .verify(accessToken)
    }

    private fun decodeRefreshToken(refreshToken: String): DecodedJWT = tryExecute {
        JWT.require(Algorithm.HMAC512(hmacRefreshKey))
            .build()
            .verify(refreshToken)
    }

    private fun <T : Any> tryExecute(decode: () -> T): T {
        try {
            return decode.invoke()
        } catch (e: TokenExpiredException) {
            throw BadCredentialsException("token expired")
        } catch (e: JWTDecodeException) {
            throw BadCredentialsException("error: jwt decode error")
        } catch (e: JsonProcessingException) {
            throw BadCredentialsException("error occurred while processing payload")
        } catch (e: Exception) {
            throw BadCredentialsException("error: token is wrong")
        }
    }

    /**
     * JWT AccessToken 요청 DTO
     *
     * @property userId Jwt payload 에 들어갈 userId
     */
    private data class JwtPayload(
        val userId: Long
    ) {
        fun asMap(): Map<String, Any> = objectMapper.convertValueAsMap(this)
    }

    private val rsaPrivateKey = createRSAPrivateKey(privateKey)
    private val rsaPublicKey = createRSAPublicKey(publicKey)
    private val hmacRefreshKey = refreshKey.toByteArray(StandardCharsets.UTF_8)

    companion object : KLogging() {
        private const val TOKEN_PREFIX = "Bearer"
        private fun createRSAPrivateKey(privateKey: String): RSAPrivateKey {
            val keyFactory = KeyFactory.getInstance("RSA")
            val pkcs8EncodedKeySpec = PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey))
            return keyFactory.generatePrivate(pkcs8EncodedKeySpec) as RSAPrivateKey
        }

        private fun createRSAPublicKey(publicKey: String): RSAPublicKey {
            val keyFactory = KeyFactory.getInstance("RSA")
            val x509EncodedKeySpec = X509EncodedKeySpec(Base64.getDecoder().decode(publicKey))
            return keyFactory.generatePublic(x509EncodedKeySpec) as RSAPublicKey
        }

        private const val CLAIMS_USER_ID = "userId"
    }
}
