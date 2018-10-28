package name.mharbovskyi.redditsimpleclient.data.room

import name.mharbovskyi.redditsimpleclient.data.toLocalDateTime
import name.mharbovskyi.redditsimpleclient.data.toSeconds
import name.mharbovskyi.redditsimpleclient.domain.model.Post
import java.time.Instant

internal fun List<Post>.toRoomPostList(): List<RoomPost> =
    asSequence()
        .map { it.toRoomPost() }
        .toList()

internal fun List<RoomPost>.toPostList(): List<Post> =
    asSequence()
        .map { it.toPost() }
        .toList()

internal fun Post.toRoomPost(): RoomPost =
    RoomPost(
        id = id,
        title = title,
        author = author,
        createdSec = created.toSeconds(),
        addedSec = Instant.now().epochSecond,
        thumbnailUrl = thumbnailUrl,
        commentsCount = commentsCount,
        contentUrl = contentUrl
    )

internal fun RoomPost.toPost(): Post =
        Post(
            id = id,
            title = title,
            author = author,
            created = createdSec.toLocalDateTime(),
            contentUrl = contentUrl,
            thumbnailUrl = thumbnailUrl,
            commentsCount = commentsCount
        )