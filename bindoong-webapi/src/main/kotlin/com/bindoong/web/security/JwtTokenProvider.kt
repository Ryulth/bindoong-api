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
) : TokenProvider {
    override fun createToken(userId: Long): Token {
        val accessToken = JWT.create()
            .withPayload(JwtPayload(userId).asMap())
            .withExpiresAt(Date.from(Instant.now().plusSeconds(3600)))
            .sign(Algorithm.RSA256(rsaPublicKey, rsaPrivateKey))
        // TODO refresh 발급 로직 추가dd
        return Token(
            accessToken = accessToken,
            type = TOKEN_PREFIX,
            expiresAt = 0,
            refreshToken = accessToken
        )
    }

    override fun getTokenPrefix(): String = TOKEN_PREFIX

    override fun getAuthentication(token: String): Authentication = decodeJWT(token).let {
        UserSession(
            userId = it.claims["userId"]!!.asLong(),
            token = token
        )
    }

    private fun decodeJWT(token: String): DecodedJWT =
        try {
            doDecodeJWT(token, rsaPublicKey, rsaPrivateKey)
        } catch (e: TokenExpiredException) {
            throw BadCredentialsException("token expired")
        } catch (e: JWTDecodeException) {
            throw BadCredentialsException("error: jwt decode error")
        } catch (e: JsonProcessingException) {
            throw BadCredentialsException("error occurred while processing payload")
        } catch (e: Exception) {
            throw BadCredentialsException("error: token is wrong")
        }

    private fun doDecodeJWT(
        token: String,
        jwtPublicKey: RSAPublicKey,
        jwtPrivateKey: RSAPrivateKey
    ): DecodedJWT =
        JWT.require(Algorithm.RSA256(jwtPublicKey, jwtPrivateKey))
            .build()
            .verify(token)

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
    }
}