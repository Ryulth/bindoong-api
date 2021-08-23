package com.bindoong.infrastructure.post

import com.bindoong.domain.Cursor
import com.bindoong.domain.Cursorable
import com.bindoong.domain.post.Post
import com.bindoong.domain.post.PostRepository
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.domain.Sort
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PostRepositoryImpl(
    private val template: R2dbcEntityTemplate
) : PostRepository {
    @Transactional
    override suspend fun save(post: Post): Post =
        if (existsById(post.postId)) {
            template.update(post).awaitSingle()
        } else {
            template.insert(post).awaitSingle()
        }

    @Transactional
    override suspend fun findById(postId: String): Post? =
        template.selectOne(Query.query(where(COLUMN_POST_ID).`is`(postId)), Post::class.java).awaitSingleOrNull()

    @Transactional
    override suspend fun findAllByUserId(userId: String, cursorable: Cursorable): Cursor<Post> =
        template.select(
            Query.query(
                where(COLUMN_USER_ID).`is`(userId).let {
                    cursorable.cursor?.let { cursor ->
                        it.and(where(COLUMN_POST_ID).lessThan(cursor))
                    } ?: it
                }
            )
                .sort(Sort.by(Sort.Direction.DESC, COLUMN_POST_ID))
                .limit(cursorable.size),
            Post::class.java
        ).asFlow().let {
            Cursor(
                content = it,
                current = cursorable.cursor,
                next = it.last().postId
            )
        }

    @Transactional
    override suspend fun deleteById(postId: String) {
        template.delete(Query.query(where(COLUMN_POST_ID).`is`(postId)), Post::class.java).awaitSingleOrNull()
    }

    @Transactional
    override suspend fun existsById(postId: String): Boolean =
        template.exists(Query.query(where(COLUMN_POST_ID).`is`(postId)), Post::class.java).awaitSingleOrNull()
            ?: false

    companion object {
        private const val COLUMN_POST_ID = "post_id"
        private const val COLUMN_USER_ID = "user_id"
    }
}
