package com.bindoong.infrastructure.post

import com.bindoong.domain.CursorPage
import com.bindoong.domain.CursorRequest
import com.bindoong.domain.post.Comment
import com.bindoong.domain.post.CommentRepository
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

@Component
class CommentRepositoryImpl(
    private val template: R2dbcEntityTemplate
) : CommentRepository {
    @Transactional
    override suspend fun insert(comment: Comment): Comment = template.insert(comment).awaitSingle()

    @Transactional
    override suspend fun update(comment: Comment): Comment = template.update(comment).awaitSingle()

    @Transactional
    override suspend fun findById(commentId: String): Comment? =
        template.selectOne(Query.query(where(COLUMN_COMMENT_ID).`is`(commentId)), Comment::class.java).awaitSingleOrNull()

    @Transactional
    override suspend fun findAllByPostId(postId: String, cursorRequest: CursorRequest): CursorPage<Comment> {
        return template.select(
            Query.query(
                where(COLUMN_POST_ID).`is`(postId).let {
                    cursorRequest.cursor?.let { cursor ->
                        it.and(where(COLUMN_COMMENT_ID).lessThan(cursor))
                    } ?: it
                }
            )
                .sort(Sort.by(Sort.Direction.DESC, COLUMN_COMMENT_ID))
                .limit(cursorRequest.size),
            Comment::class.java
        ).asFlow().let {
            CursorPage(
                content = it,
                CursorPage.Cursor(
                    current = cursorRequest.cursor,
                    next = it.lastOrNull()?.postId
                )
            )
        }
    }

    @Transactional
    override suspend fun deleteById(commentId: String) {
        template.delete(Query.query(where(COLUMN_COMMENT_ID).`is`(commentId)), Comment::class.java).awaitSingleOrNull()
    }

    companion object {
        private const val COLUMN_COMMENT_ID = "comment_id"
        private const val COLUMN_POST_ID = "post_id"
    }
}
