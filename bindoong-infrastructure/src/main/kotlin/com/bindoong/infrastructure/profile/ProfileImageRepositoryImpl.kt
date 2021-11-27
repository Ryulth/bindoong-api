package com.bindoong.infrastructure.profile

import com.bindoong.domain.profile.ProfileImage
import com.bindoong.domain.profile.ProfileImageRepository
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ProfileImageRepositoryImpl(
    private val template: R2dbcEntityTemplate
) : ProfileImageRepository {
    @Transactional
    override suspend fun insert(profileImage: ProfileImage): ProfileImage = template.insert(profileImage).awaitSingle()

    @Transactional
    override suspend fun update(profileImage: ProfileImage): ProfileImage = template.update(profileImage).awaitSingle()

    @Transactional
    override suspend fun findById(profileImageId: String): ProfileImage? =
        template.selectOne(Query.query(where(COLUMN_PROFILE_IMAGE_ID).`is`(profileImageId)), ProfileImage::class.java).awaitSingleOrNull()

    @Transactional
    override suspend fun findByUserId(userId: String): ProfileImage? =
        template.selectOne(Query.query(where(COLUMN_USER_ID).`is`(userId)), ProfileImage::class.java).awaitSingleOrNull()

    @Transactional
    override suspend fun deleteById(profileImageId: String) {
        template.delete(Query.query(where(COLUMN_PROFILE_IMAGE_ID).`is`(profileImageId)), ProfileImage::class.java).awaitSingleOrNull()
    }

    @Transactional
    override suspend fun deleteByUserId(userId: String) {
        template.delete(Query.query(where(COLUMN_USER_ID).`is`(userId)), ProfileImage::class.java).awaitSingleOrNull()
    }

    companion object {
        private const val COLUMN_PROFILE_IMAGE_ID = "profile_image_id"
        private const val COLUMN_USER_ID = "user_id"
    }
}
