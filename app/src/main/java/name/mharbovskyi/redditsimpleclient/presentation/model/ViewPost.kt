package name.mharbovskyi.redditsimpleclient.presentation.model

data class ViewPost(
        val id: String,
        val title: String,
        val createdBefore: String,
        val authorName: String,
        val hasThumbnail: Boolean,
        val thumbnailUrl: String?,
        val hasContentUrl: Boolean,
        val contentUrl: String?,
        val commentsCount: String
)