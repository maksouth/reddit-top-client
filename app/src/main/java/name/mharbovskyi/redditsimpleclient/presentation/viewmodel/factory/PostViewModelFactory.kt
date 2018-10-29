package name.mharbovskyi.redditsimpleclient.presentation.viewmodel.factory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import name.mharbovskyi.redditsimpleclient.domain.usecase.ClearLocalPostsUsecase
import name.mharbovskyi.redditsimpleclient.domain.usecase.PaginationUsecase
import name.mharbovskyi.redditsimpleclient.presentation.viewmodel.PostsViewModel
import javax.inject.Inject

class PostViewModelFactory @Inject constructor (
        private val paginationUsecase: PaginationUsecase,
        private val clearLocalPostsUsecase: ClearLocalPostsUsecase
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            PostsViewModel(paginationUsecase, clearLocalPostsUsecase) as T

}