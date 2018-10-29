package name.mharbovskyi.redditsimpleclient.presentation.viewmodel

import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import name.mharbovskyi.redditsimpleclient.domain.ConnectionChecker
import name.mharbovskyi.redditsimpleclient.domain.usecase.ClearLocalPostsUsecase
import name.mharbovskyi.redditsimpleclient.domain.usecase.LoadedAll
import name.mharbovskyi.redditsimpleclient.domain.usecase.NextPage
import name.mharbovskyi.redditsimpleclient.domain.usecase.PaginationUsecase
import name.mharbovskyi.redditsimpleclient.presentation.model.*
import name.mharbovskyi.redditsimpleclient.presentation.model.mapper.toViewPostList
import java.util.concurrent.atomic.AtomicBoolean

class PostsViewModel (
    private val paginationUsecase: PaginationUsecase,
    private val clearLocalPostsUsecase: ClearLocalPostsUsecase,
    private val connectionChecker: ConnectionChecker
): ViewModel() {

    private val TAG = PostsViewModel::class.java.simpleName

    lateinit var posts: ReplaySubject<List<ViewPost>>
    lateinit var errors: PublishSubject<ViewError>
    lateinit var infos: PublishSubject<ViewInfo>
    lateinit var showImageData: PublishSubject<String>

    private val disposables = CompositeDisposable()
    private var loadingInProgress = AtomicBoolean(false)

    fun start() {
        if (!::posts.isInitialized) {
            posts = ReplaySubject.create()
            errors = PublishSubject.create()
            infos = PublishSubject.create()
            showImageData = PublishSubject.create()

            val d = clearLocalPostsUsecase.execute()
            disposables.add(d)

            loadMore()

            connectionChecker.isConnected()
                .filter{ !it }
                .subscribeBy { infos.onNext(infoNoConnection) }
        }
    }

    fun scrolled(visibleItemCount: Int, firstVisibleItemPosition: Int, itemCount: Int) {
        if (firstVisibleItemPosition + visibleItemCount == itemCount - postsThreshold) {
            loadMore()
        }
    }

    fun thumbnailClick(contentUrl: String) {
        connectionChecker.isConnected()
            .subscribeBy {
                if (it)
                    showImageData.onNext(contentUrl)
                else infos.onNext(infoNoConnection)
            }
    }

    private fun loadMore() {

        if(!loadingInProgress.get()) {
            loadingInProgress.set(true)

            val d = paginationUsecase.loadMore()
                .doFinally { loadingInProgress.set(false) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        when(it) {
                            LoadedAll -> infos.onNext(infoLastPage)
                            is NextPage -> it.posts.toViewPostList().let { posts.onNext(it) }
                        }
                    },
                    onError = { errors.onNext(errorLoadPosts) }
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