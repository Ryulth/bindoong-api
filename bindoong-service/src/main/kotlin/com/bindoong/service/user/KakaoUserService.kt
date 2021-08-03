package com.bindoong.service.user

import com.bindoong.domain.user.KakaoUserCreateParameter
import com.bindoong.domain.user.KakaoUserDomainService
import com.bindoong.domain.user.LoginType
import com.bindoong.domain.user.Role
import com.bindoong.domain.user.User
import com.bindoong.domain.user.UserCreateParameter
import com.bindoong.domain.user.UserDomainService
import org.springframework.stereotype.Service

@Service
class KakaoUserService(
    private val kakaoUserDomainService: KakaoUserDomainService,
    private val userDomainService: UserDomainService
) : AbstractUserService<KakaoRegisterParameter, KakaoLoginParameter>() {

    override suspend fun validateRegister(registerParameter: KakaoRegisterParameter) {
        // TODO 인증
    }

    override suspend fun isExist(registerParameter: KakaoRegisterParameter): Boolean =
        kakaoUserDomainService.isExist(registerParameter.kakaoId)

    override suspend fun create(registerParameter: KakaoRegisterParameter): User =
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
                    userId = it.id!!
                )
            )
        }

    override suspend fun get(loginParameter: KakaoLoginParameter): User =
        kakaoUserDomainService.get(loginParameter.kakaoId)
            ?.let { userDomainService.get(it.userId) }
            ?: throw UserNotFoundException("Kakao user not found login request $loginParameter")

    override suspend fun delete(userId: Long) {
        kakaoUserDomainService.delete(userId)
    }
}