package com.bindoong.domain.user

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
internal interface UserRepository: CoroutineCrudRepository<User, Long>