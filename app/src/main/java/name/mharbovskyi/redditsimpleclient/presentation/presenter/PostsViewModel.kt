package name.mharbovskyi.redditsimpleclient.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import name.mharbovskyi.redditsimpleclient.domain.mapper.toViewPostList
import name.mharbovskyi.redditsimpleclient.domain.usecase.LoadPostsUsecase
import name.mharbovskyi.redditsimpleclient.domain.usecase.StorePostsUsecase
import name.mharbovskyi.redditsimpleclient.presentation.model.ViewError
import name.mharbovskyi.redditsimpleclient.presentation.model.ViewPost
import name.mharbovskyi.redditsimpleclient.presentation.model.errorLoadPosts

class PostsViewModel(
        private val loadPostsUsecase: LoadPostsUsecase,
        private val savePostsUsecase: StorePostsUsecase
): ViewModel() {

    private val TAG = PostsViewModel::class.java.simpleName

    lateinit var posts: MutableLiveData<List<ViewPost>>
    lateinit var errors: MutableLiveData<ViewError>

    fun start() {
        posts = MutableLiveData()
        errors = MutableLiveData()

        loadPostsUsecase.loadMore()
                .doOnSuccess(savePostsUsecase::store)
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(::toViewPostList)
                .subscribeBy (
                        onSuccess = {posts.value = it},
                        onError = {
                            Log.d(TAG, "Error loading posts", it)
                            errors.value = errorLoadPosts
                        }
                )
    }

}