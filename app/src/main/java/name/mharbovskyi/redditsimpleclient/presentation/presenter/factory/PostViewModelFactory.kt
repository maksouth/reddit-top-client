package name.mharbovskyi.redditsimpleclient.presentation.presenter.factory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import name.mharbovskyi.redditsimpleclient.domain.usecase.LoadPostsUsecase
import name.mharbovskyi.redditsimpleclient.presentation.presenter.PostsViewModel
import javax.inject.Inject

class PostViewModelFactory @Inject constructor (
        private val loadPostsUsecase: LoadPostsUsecase
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            PostsViewModel(loadPostsUsecase) as T

}