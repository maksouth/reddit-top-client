package name.mharbovskyi.redditsimpleclient.data.mapper

import name.mharbovskyi.redditsimpleclient.data.model.Children
import name.mharbovskyi.redditsimpleclient.domain.model.Post
import java.time.LocalDateTime

fun toPost(children: Children): Post {
    val data = children.data
    val post = Post(
            id = data.id,
            title = data.title,
            author = data.author,
            created = data.created_utc.toLocalDateTime(),
            thumbnailUrl = checkThumbnail(data.thumbnail),
            commentsCount = data.num_comments,
            contentUrl = data.url
    )
    return post
}

fun checkThumbnail(thumbnail: String): String? =
        if (thumbnail.isBlank()) null
        else thumbnail

fun Int.toLocalDateTime(): LocalDateTime {
    TODO()
}