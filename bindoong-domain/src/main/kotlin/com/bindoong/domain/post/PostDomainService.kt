package com.bindoong.domain.post

import com.bindoong.domain.CursorPage
import com.bindoong.domain.CursorRequest
import com.github.f4b6a3.ulid.UlidCreator
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostDomainService(
    private val postRepository: PostRepository
) {
    @Transactional
    suspend fun create(parameter: CreateParameter): Post =
        this.insert(
            Post(
                postId = UlidCreator.getUlid().toString(),
                userId = parameter.userId,
                imageUrl = parameter.imageUrl,
                content = parameter.content,
                locationId = parameter.locationId
            )
        )

    @Transactional
    suspend fun update(parameter: UpdateParameter): Post =
        this.update(
            Post(
                postId = parameter.postId,
                userId = parameter.userId,
                imageUrl = parameter.imageUrl,
                content = parameter.content,
                locationId = parameter.locationId
            )
        )

    @Transactional(readOnly = true)
    suspend fun getByPostId(postId: String): Post? = this.findByPostId(postId)

    @Transactional(readOnly = true)
    suspend fun getAllByUserId(userId: String, cursorRequest: CursorRequest): CursorPage<Post> =
        this.findAllByUserId(userId, cursorRequest)

    @Transactional
    suspend fun delete(postId: String) = this.deleteByPostId(postId)

    @Transactional
    suspend fun getAllByRandomExcludeUserId(size: Int, userId: String): Flow<Post> =
        this.findAllByRandomAndUserIdNot(size, userId)

    private suspend fun insert(post: Post): Post = postRepository.insert(post)

    private suspend fun update(post: Post): Post = postRepository.update(post)

    private suspend fun findByPostId(postId: String): Post? = postRepository.findById(postId)

    private suspend fun findAllByUserId(userId: String, cursorRequest: CursorRequest): CursorPage<Post> =
        postRepository.findAllByUserId(userId, cursorRequest)

    private suspend fun deleteByPostId(postId: String) = postRepository.deleteById(postId)

    private suspend fun findAllByRandomAndUserIdNot(size: Int, userId: String): Flow<Post> =
        postRepository.findAllByRandomAndUserIdNot(size, userId)
}

data class CreateParameter(
    val userId: String,
    val imageUrl: String,
    val content: String?,
    val locationId: String?
)

data class UpdateParameter(
    val userId: String,
    val postId: String,
    val imageUrl: String,
    val content: String?,
    val locationId: String?
)
