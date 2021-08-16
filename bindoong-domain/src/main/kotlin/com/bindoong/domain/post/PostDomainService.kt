package com.bindoong.domain.post

import com.bindoong.core.utils.StringUtils.toBase64String
import com.fasterxml.uuid.EthernetAddress
import com.fasterxml.uuid.Generators
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostDomainService(
    private val postRepository: PostRepository
) {
    @Transactional
    suspend fun create(parameter: CreateParameter): Post =
        this.save(
            Post(
                postId = generator.generate().toBase64String(),
                userId = parameter.userId,
                imageUrl = parameter.imageUrl
            )
        )

    @Transactional(readOnly = true)
    suspend fun get(postId: String): Post? = this.findByPostId(postId)

    @Transactional(readOnly = true)
    suspend fun get(userId: Long): Post? = this.findByUserId(userId)

    @Transactional
    suspend fun delete(postId: String) = this.deleteByPostId(postId)

    private suspend fun save(post: Post): Post = postRepository.save(post)

    private suspend fun findByPostId(postId: String): Post? = postRepository.findById(postId)

    private suspend fun findByUserId(userId: Long): Post? = postRepository.findByUserId(userId)

    private suspend fun deleteByPostId(postId: String) = postRepository.deleteById(postId)

    companion object {
        private val generator = Generators.timeBasedGenerator(EthernetAddress.fromInterface())
    }
}

data class CreateParameter(
    val userId: Long,
    val imageUrl: String
)
