package com.bindoong.domain.post

import com.bindoong.domain.Cursor
import com.bindoong.domain.Cursorable
import com.fasterxml.uuid.EthernetAddress
import com.fasterxml.uuid.Generators
import com.github.f4b6a3.ulid.UlidCreator
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
                postId = UlidCreator.getUlid().toString(),
                userId = parameter.userId,
                imageUrl = parameter.imageUrl
            )
        )

    @Transactional
    suspend fun update(parameter: UpdateParameter): Post =
        this.save(
            Post(
                postId = parameter.postId,
                userId = parameter.userId,
                imageUrl = parameter.imageUrl
            )
        )

    @Transactional(readOnly = true)
    suspend fun getByPostId(postId: String): Post? = this.findByPostId(postId)

    @Transactional(readOnly = true)
    suspend fun getAllByUserId(userId: String, cursorable: Cursorable): Cursor<Post> = this.findAllByUserId(userId, cursorable)

    @Transactional
    suspend fun delete(postId: String) = this.deleteByPostId(postId)

    private suspend fun save(post: Post): Post = postRepository.save(post)

    private suspend fun findByPostId(postId: String): Post? = postRepository.findById(postId)

    private suspend fun findAllByUserId(userId: String, cursorable: Cursorable): Cursor<Post> =
        postRepository.findAllByUserId(userId, cursorable)

    private suspend fun deleteByPostId(postId: String) = postRepository.deleteById(postId)

    companion object {
        private val generator = Generators.timeBasedGenerator(EthernetAddress.fromInterface())
    }
}

data class CreateParameter(
    val userId: String,
    val imageUrl: String
)

data class UpdateParameter(
    val userId: String,
    val postId: String,
    val imageUrl: String
)
