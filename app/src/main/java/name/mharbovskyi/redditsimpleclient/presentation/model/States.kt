package name.mharbovskyi.redditsimpleclient.presentation.model

enum class ImageVisibilityState {
    SHOW, HIDE
}

class ImageState internal constructor (
    val visibilityState: ImageVisibilityState,
    val contentUrl: String? = null
)

fun showState(contentUrl: String) =
        ImageState(ImageVisibilityState.SHOW, contentUrl)

fun hideState() =
        ImageState(ImageVisibilityState.HIDE)

