package name.mharbovskyi.redditsimpleclient.presentation.viewmodel

import android.arch.lifecycle.ViewModel
import io.reactivex.subjects.BehaviorSubject
import name.mharbovskyi.redditsimpleclient.presentation.model.Hide
import name.mharbovskyi.redditsimpleclient.presentation.model.ImageState
import name.mharbovskyi.redditsimpleclient.presentation.model.Show

class MainViewModel: ViewModel() {

    val fullImageSubject: BehaviorSubject<ImageState> = BehaviorSubject.create()

    fun showImage(contentUrl: String) {
        fullImageSubject.onNext(Show(contentUrl))
    }

    fun shouldExit() =
        when(fullImageSubject.value) {
            is Show -> {
                fullImageSubject.onNext(Hide)
                false
            }
            Hide, null -> true
        }
}