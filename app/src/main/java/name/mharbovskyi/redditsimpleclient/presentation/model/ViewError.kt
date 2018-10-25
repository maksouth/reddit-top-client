package name.mharbovskyi.redditsimpleclient.presentation.model

import android.support.annotation.StringRes
import name.mharbovskyi.redditsimpleclient.R

data class ViewError (
        @StringRes val messageId: Int
)

val errorLoadPosts = ViewError(R.string.error_load_posts)