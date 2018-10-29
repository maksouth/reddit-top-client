package name.mharbovskyi.redditsimpleclient.presentation.model

import android.support.annotation.StringRes
import name.mharbovskyi.redditsimpleclient.R

data class ViewPost(
    val id: String,
    val title: String,
    val createdBefore: String,
    val authorName: String,
    val showThumbnail: Boolean,
    val thumbnailUrl: String?,
    val showContent: Boolean,
    val contentUrl: String?,
    val commentsCount: String
)

data class ViewInfo(@StringRes val messageId: Int)
data class ViewError(@StringRes val messageId: Int)

val errorLoadPosts = ViewError(R.string.error_load_posts)
val infoLastPage = ViewInfo(R.string.info_last_page)
val infoNoConnection = ViewInfo(R.string.info_no_connection)
