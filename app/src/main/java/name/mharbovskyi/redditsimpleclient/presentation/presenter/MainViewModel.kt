package name.mharbovskyi.redditsimpleclient.presentation.presenter

import android.arch.lifecycle.ViewModel
import io.reactivex.subjects.BehaviorSubject
import name.mharbovskyi.redditsimpleclient.presentation.model.ImageState
import name.mharbovskyi.redditsimpleclient.presentation.model.ImageVisibilityState
import name.mharbovskyi.redditsimpleclient.presentation.model.hideState
import name.mharbovskyi.redditsimpleclient.presentation.model.showState

class MainViewModel: ViewModel() {

    val fullImageSubject: BehaviorSubject<ImageState> = BehaviorSubject.create()

    fun showImage(contentUrl: String) {
        fullImageSubject.onNext(showState(contentUrl))
    }

    fun shouldExit(): Boolean {
        val lastState = fullImageSubject.value
        if (lastState != null && lastState.visibilityState == ImageVisibilityState.SHOW) {
            fullImageSubject.onNext(hideState())
            return false
        } else return true
    }
}