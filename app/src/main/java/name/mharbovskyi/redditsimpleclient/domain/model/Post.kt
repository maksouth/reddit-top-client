package name.mharbovskyi.redditsimpleclient.domain.model

import java.time.LocalDateTime

data class Post (
        val id: String,
        val title: String,
        val created: LocalDateTime,
        val author: String,
        val thumbnailUrl: String?,
        val contentUrl: String,
        val commentsCount: Int
)