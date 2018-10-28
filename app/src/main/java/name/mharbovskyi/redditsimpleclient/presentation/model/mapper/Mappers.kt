package name.mharbovskyi.redditsimpleclient.presentation.model.mapper

import name.mharbovskyi.redditsimpleclient.domain.model.Post
import name.mharbovskyi.redditsimpleclient.presentation.model.ViewPost

internal fun List<Post>.toViewPostList(): List<ViewPost> =
    asSequence()
        .map { it.toViewPost() }
        .toList()


internal fun Post.toViewPost(): ViewPost =
        ViewPost(
            id = id,
            title = title,
            showThumbnail = thumbnailUrl?.isImageFormat() ?: false,
            thumbnailUrl = thumbnailUrl,
            showContent = contentUrl.isImageFormat(),
            contentUrl = contentUrl,
            authorName = "by $author",
            commentsCount = "${shortStringNumber(commentsCount)} comments",
            createdBefore = created.beforeNowString()
        )