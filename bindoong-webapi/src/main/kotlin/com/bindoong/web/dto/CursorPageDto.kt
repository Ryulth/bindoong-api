package com.bindoong.web.dto

import com.bindoong.domain.CursorPage
import com.bindoong.domain.post.Post
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

data class CursorPageDto<T>(
    val content: List<T>,
    val cursor: CursorDto
) {
    data class CursorDto(
        val current: String? = null,
        val next: String? = null
    )
}

suspend fun CursorPage<Post>.toPostDto(): CursorPageDto<PostDto> = this.let {
    CursorPageDto(
        it.content.map { post -> PostDto(post) }.toList(),
        CursorPageDto.CursorDto(
            it.cursor.current,
            it.cursor.next
        )
    )
}
