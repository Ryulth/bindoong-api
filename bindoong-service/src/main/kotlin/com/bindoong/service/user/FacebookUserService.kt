package com.bindoong.service.user

import com.bindoong.domain.user.FacebookUserCreateParameter
import com.bindoong.domain.user.FacebookUserDomainService
import com.bindoong.domain.user.LoginType
import com.bindoong.domain.user.Role
import com.bindoong.domain.user.User
import com.bindoong.domain.user.UserCreateParameter
import com.bindoong.domain.user.UserDomainService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FacebookUserService(
    private val facebookUserDomainService: FacebookUserDomainService,
    private val userDomainService: UserDomainService
) : AbstractUserService<FacebookRegisterParameter, FacebookLoginParameter>() {
    @Transactional
    override suspend fun validateRegister(registerParameter: FacebookRegisterParameter) {
        // TODO 인증
    }

    @Transactional
    override suspend fun doRegister(registerParameter: FacebookRegisterParameter): User =
        userDomainService.create(
            UserCreateParameter(
                nickName = registerParameter.nickname,
                loginType = LoginType.FACEBOOK,
                roles = setOf(Role.ROLE_BASIC)
            )
        ).also {
            facebookUserDomainService.create(
                FacebookUserCreateParameter(
                    facebookId = registerParameter.facebookId,
                    lastAccessToken = registerParameter.accessToken,
                    userId = it.userId!!
                )
            )
        }

    @Transactional
    override suspend fun validateLogin(loginParameter: FacebookLoginParameter) {
        // TODO 인증
    }

    @Transactional
    override suspend fun doLogin(loginParameter: FacebookLoginParameter): User =
        facebookUserDomainService.get(loginParameter.facebookId)
            ?.let { userDomainService.get(it.userId) }
            ?: throw UserNotFoundException("Facebook user not found login request $loginParameter")

    @Transactional
    override suspend fun isExist(registerParameter: FacebookRegisterParameter): Boolean =
        facebookUserDomainService.isExist(registerParameter.facebookId)

    @Transactional
    override suspend fun doWithDraw(userId: String) {
        facebookUserDomainService.delete(userId)
    }
}

data class FacebookRegisterParameter(
    val facebookId: String,
    val accessToken: String,
    override val nickname: String
) : RegisterParameter(nickname)

data class FacebookLoginParameter(
    val facebookId: String,
    val accessToken: String,
) : LoginParameter()
