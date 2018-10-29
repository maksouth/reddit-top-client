package name.mharbovskyi.redditsimpleclient.presentation.presenter

import android.arch.lifecycle.ViewModel
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import name.mharbovskyi.redditsimpleclient.domain.usecase.ClearLocalPostsUsecase
import name.mharbovskyi.redditsimpleclient.domain.usecase.LoadPostsUsecase
import name.mharbovskyi.redditsimpleclient.presentation.model.ImageState
import name.mharbovskyi.redditsimpleclient.presentation.model.ViewError
import name.mharbovskyi.redditsimpleclient.presentation.model.ViewPost
import name.mharbovskyi.redditsimpleclient.presentation.model.errorLoadPosts
import name.mharbovskyi.redditsimpleclient.presentation.model.mapper.toViewPostList
import java.util.concurrent.atomic.AtomicBoolean

class PostsViewModel (
        private val loadPostsUsecase: LoadPostsUsecase,
        private val clearLocalPostsUsecase: ClearLocalPostsUsecase
): ViewModel() {

    private val TAG = PostsViewModel::class.java.simpleName

    lateinit var posts: ReplaySubject<List<ViewPost>>
    lateinit var errors: PublishSubject<ViewError>

    private val disposables = CompositeDisposable()
    private var loadingInProgress = AtomicBoolean(false)

    fun start() {
        if (!::posts.isInitialized) {
            posts = ReplaySubject.create()
            errors = PublishSubject.create()

            val d = clearLocalPostsUsecase.execute()
            disposables.add(d)

            loadMore()
        }
    }

    fun scrolled(visibleItemCount: Int, firstVisibleItemPosition: Int, itemCount: Int) {
        Log.d(TAG, "Scrolled first visible: $firstVisibleItemPosition, visible count: $visibleItemCount, total: $itemCount")
        if (firstVisibleItemPosition + visibleItemCount > itemCount - postsThreshold) {
            Log.d(TAG, "load more")
            loadMore()
        }
    }

    fun thumbnailClick(imageUrl: String) {

    }

    private fun loadMore() {

        if(!loadingInProgress.get()) {
            Log.d(TAG, "actually loading")
            loadingInProgress.set(true)
            val d = loadPostsUsecase.loadMore()
                .doFinally { loadingInProgress.set(false) }
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.toViewPostList() }
                .subscribeBy(
                    onSuccess = { posts.onNext(it) },
                    onError = {
                        Log.d(TAG, "Error loading posts", it)
                        errors.onNext(errorLoadPosts)
                    }
                )
            disposables.add(d)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    companion object {
        const val postsThreshold = 5
    }
}