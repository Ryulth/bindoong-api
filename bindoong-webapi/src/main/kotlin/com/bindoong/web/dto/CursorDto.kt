package com.bindoong.web.dto

import com.bindoong.domain.Cursor
import com.bindoong.domain.post.Post
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

data class CursorDto<T>(
    val content: List<T>,
    val currentCursor: String?,
    val nextCursor: String?
)

suspend fun Cursor<Post>.toPostDto(): CursorDto<PostDto> = this.let {
    CursorDto(
        it.content.map { post -> PostDto(post) }.toList(),
        it.currentCursor,
        it.nextCursor
    )
}
