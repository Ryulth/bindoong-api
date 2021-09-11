package com.bindoong.infrastructure.post

import com.bindoong.domain.CursorPage
import com.bindoong.domain.CursorRequest
import com.bindoong.domain.post.Post
import com.bindoong.domain.post.PostRepository
import io.r2dbc.spi.Row
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.domain.Sort
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component
class PostRepositoryImpl(
    private val template: R2dbcEntityTemplate
) : PostRepository {
    @Transactional
    override suspend fun insert(post: Post): Post = template.insert(post).awaitSingle()

    @Transactional
    override suspend fun update(post: Post): Post = template.update(post).awaitSingle()

    @Transactional
    override suspend fun findById(postId: String): Post? =
        template.selectOne(Query.query(where(COLUMN_POST_ID).`is`(postId)), Post::class.java).awaitSingleOrNull()

    @Transactional
    override suspend fun findAllByUserId(userId: String, cursorRequest: CursorRequest): CursorPage<Post> =
        template.select(
            Query.query(
                where(COLUMN_USER_ID).`is`(userId).let {
                    cursorRequest.cursor?.let { cursor ->
                        it.and(where(COLUMN_POST_ID).lessThan(cursor))
                    } ?: it
                }
            )
                .sort(Sort.by(Sort.Direction.DESC, COLUMN_POST_ID))
                .limit(cursorRequest.size),
            Post::class.java
        ).asFlow().let {
            CursorPage(
                content = it,
                CursorPage.Cursor(
                    current = cursorRequest.cursor,
                    next = it.lastOrNull()?.postId
                )
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

    @Transactional
    override suspend fun findAllByRandomAndUserIdNot(size: Int, userId: String): Flow<Post> =
        template.databaseClient.sql(
            """
            SELECT * FROM post AS p1 JOIN 
                (SELECT post_id FROM post WHERE post.user_id != '$userId' ORDER BY RAND() LIMIT $size)
            as p2 ON p1.post_id=p2.post_id;
            """.trimIndent()
        ).map { t, u ->
            t.toModel()
        }.all().asFlow()

    private fun Row.toModel() = Post(
        postId = this.get(COLUMN_POST_ID, String::class.java)!!,
        userId = this.get(COLUMN_USER_ID, String::class.java)!!,
        imageUrl = this.get(COLUMN_IMAGE_URL, String::class.java)!!,
        content = this.get(COLUMN_CONTENT, String::class.java),
        createdDateTime = this.get(COLUMN_CREATED_DATE_TIME, LocalDateTime::class.java)!!,
        updatedDateTime = this.get(COLUMN_UPDATED_DATE_TIME, LocalDateTime::class.java)!!,
    )

    companion object {
        private const val COLUMN_POST_ID = "post_id"
        private const val COLUMN_USER_ID = "user_id"
        private const val COLUMN_IMAGE_URL = "image_url"
        private const val COLUMN_CONTENT = "content"
        private const val COLUMN_CREATED_DATE_TIME = "created_date_time"
        private const val COLUMN_UPDATED_DATE_TIME = "updated_date_time"
    }
}
