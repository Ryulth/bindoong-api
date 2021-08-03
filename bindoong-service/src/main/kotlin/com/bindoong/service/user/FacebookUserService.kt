package com.bindoong.service.user

import com.bindoong.domain.user.FacebookUserCreateParameter
import com.bindoong.domain.user.FacebookUserDomainService
import com.bindoong.domain.user.LoginType
import com.bindoong.domain.user.Role
import com.bindoong.domain.user.User
import com.bindoong.domain.user.UserCreateParameter
import com.bindoong.domain.user.UserDomainService
import org.springframework.stereotype.Service

@Service
class FacebookUserService(
    private val facebookUserDomainService: FacebookUserDomainService,
    private val userDomainService: UserDomainService
) : AbstractUserService<FacebookRegisterParameter, FacebookLoginParameter>() {

    override suspend fun validateRegister(registerParameter: FacebookRegisterParameter) {
        // TODO 인증
    }

    override suspend fun isExist(registerParameter: FacebookRegisterParameter): Boolean =
        facebookUserDomainService.isExist(registerParameter.facebookId)

    override suspend fun create(registerParameter: FacebookRegisterParameter): User =
        userDomainService.create(
            UserCreateParameter(
                nickName = registerParameter.nickname,
                loginType = LoginType.KAKAO,
                roles = setOf(Role.ROLE_BASIC)
            )
        ).also {
            facebookUserDomainService.create(
                FacebookUserCreateParameter(
                    facebookId = registerParameter.facebookId,
                    lastAccessToken = registerParameter.accessToken,
                    userId = it.id!!
                )
            )
        }

    override suspend fun get(loginParameter: FacebookLoginParameter): User =
        facebookUserDomainService.get(loginParameter.facebookId)
            ?.let { userDomainService.get(it.userId) }
            ?: throw UserNotFoundException("Facebook user not found login request $loginParameter")

    override suspend fun delete(userId: Long) {
        facebookUserDomainService.delete(userId)
    }
}