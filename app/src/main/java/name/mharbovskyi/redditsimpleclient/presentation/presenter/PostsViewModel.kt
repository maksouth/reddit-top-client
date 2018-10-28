package name.mharbovskyi.redditsimpleclient.presentation.presenter

import android.arch.lifecycle.ViewModel
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import name.mharbovskyi.redditsimpleclient.domain.usecase.LoadPostsUsecase
import name.mharbovskyi.redditsimpleclient.presentation.model.ViewError
import name.mharbovskyi.redditsimpleclient.presentation.model.ViewPost
import name.mharbovskyi.redditsimpleclient.presentation.model.errorLoadPosts
import name.mharbovskyi.redditsimpleclient.presentation.model.mapper.toViewPostList

class PostsViewModel (
        private val loadPostsUsecase: LoadPostsUsecase
): ViewModel() {

    private val TAG = PostsViewModel::class.java.simpleName

    lateinit var posts: BehaviorSubject<List<ViewPost>>
    lateinit var errors: PublishSubject<ViewError>

    val displosables = CompositeDisposable()

    fun start() {
        if (!::posts.isInitialized) {
            posts = BehaviorSubject.create()
            errors = PublishSubject.create()

            displosables.add(
                loadPostsUsecase.loadMore()
                    .doOnSuccess{Log.d("AAAA", "receive ${it.size}")}
                    .observeOn(AndroidSchedulers.mainThread())
                    .map{ it.toViewPostList() }
                    .subscribeBy (
                        onSuccess = { posts.onNext(it) },
                        onError = {
                            Log.d(TAG, "Error loading posts", it)
                            errors.onNext(errorLoadPosts)
                        }
                    )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        displosables.dispose()
    }

}