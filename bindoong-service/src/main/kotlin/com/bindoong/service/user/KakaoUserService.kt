package com.bindoong.service.user

import com.bindoong.core.exceptions.UserNotFoundException
import com.bindoong.domain.user.KakaoUserCreateParameter
import com.bindoong.domain.user.KakaoUserDomainService
import com.bindoong.domain.user.LoginType
import com.bindoong.domain.user.Role
import com.bindoong.domain.user.User
import com.bindoong.domain.user.UserCreateParameter
import com.bindoong.domain.user.UserDomainService
import com.bindoong.infrastructure.client.kakao.KakaoClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class KakaoUserService(
    private val kakaoUserDomainService: KakaoUserDomainService,
    private val userDomainService: UserDomainService,
    private val kakaoClient: KakaoClient
) : AbstractUserService<KakaoRegisterParameter, KakaoLoginParameter>() {
    @Transactional
    override suspend fun validateRegister(registerParameter: KakaoRegisterParameter) {
        kakaoClient.getUserInfo(registerParameter.accessToken)
        // TODO 인증
    }

    @Transactional
    override suspend fun doRegister(registerParameter: KakaoRegisterParameter): User =
        userDomainService.create(
            UserCreateParameter(
                nickName = registerParameter.nickname,
                loginType = LoginType.KAKAO,
                roles = setOf(Role.ROLE_BASIC)
            )
        ).also {
            kakaoUserDomainService.create(
                KakaoUserCreateParameter(
                    kakaoId = registerParameter.kakaoId,
                    lastAccessToken = registerParameter.accessToken,
                    userId = it.userId
                )
            )
        }

    @Transactional
    override suspend fun validateLogin(loginParameter: KakaoLoginParameter) {
        kakaoClient.getUserInfo(loginParameter.accessToken)
        // TODO 인증
    }

    @Transactional
    override suspend fun doLogin(loginParameter: KakaoLoginParameter): User =
        kakaoUserDomainService.get(loginParameter.kakaoId)
            ?.let { userDomainService.get(it.userId) }
            ?: throw UserNotFoundException("Kakao user not found login request $loginParameter")

    @Transactional
    override suspend fun isExist(registerParameter: KakaoRegisterParameter): Boolean =
        kakaoUserDomainService.isExist(registerParameter.kakaoId)

    @Transactional
    override suspend fun doWithDraw(userId: String) {
        kakaoUserDomainService.delete(userId)
    }
}

data class KakaoRegisterParameter(
    val kakaoId: String,
    val accessToken: String,
    override val nickname: String
) : RegisterParameter(nickname)

data class KakaoLoginParameter(
    val kakaoId: String,
    val accessToken: String,
) : LoginParameter()
