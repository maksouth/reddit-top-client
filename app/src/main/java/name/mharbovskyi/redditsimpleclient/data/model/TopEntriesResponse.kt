package name.mharbovskyi.redditsimpleclient.data.model


data class TopEntriesResponse(
    val kind: String,
    val data: Body
)

data class Body(
    val modhash: String,
    val children: List<Children>,
    val after: String,
    val before: String?
)

data class Children(
    val kind: String,
    val data: Data
)

data class Data(
    val domain: String,
    val banned_by: Any,
    val media_embed: Any,
    val subreddit: String,
    val selftext_html: Any,
    val selftext: String,
    val likes: Any,
    val user_reports: List<Any>,
    val secure_media: Any,
    val link_flair_text: Any,
    val id: String,
    val gilded: Int,
    val secure_media_embed: Any,
    val clicked: Boolean,
    val report_reasons: Any,
    val author: String,
    val media: Any,
    val score: Int,
    val approved_by: Any,
    val over_18: Boolean,
    val hidden: Boolean,
    val thumbnail: String,
    val subreddit_id: String,
    val edited: Any,
    val link_flair_css_class: Any,
    val author_flair_css_class: Any,
    val downs: Int,
    val mod_reports: List<Any>,
    val saved: Boolean,
    val is_self: Boolean,
    val name: String,
    val permalink: String,
    val stickied: Boolean,
    val created: Int,
    val url: String,
    val author_flair_text: Any,
    val title: String,
    val created_utc: Int,
    val ups: Int,
    val num_comments: Int,
    val visited: Boolean,
    val num_reports: Any,
    val distinguished: Any
)