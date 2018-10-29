package name.mharbovskyi.redditsimpleclient.presentation.model

sealed class ImageState

class Show(val contentUrl: String): ImageState()
object Hide: ImageState()
