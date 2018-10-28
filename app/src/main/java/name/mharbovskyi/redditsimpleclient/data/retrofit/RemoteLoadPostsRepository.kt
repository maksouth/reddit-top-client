package name.mharbovskyi.redditsimpleclient.data.retrofit

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import name.mharbovskyi.redditsimpleclient.domain.model.Post
import name.mharbovskyi.redditsimpleclient.domain.repository.LoadPaginatedPostsRepository

class RemoteLoadPostsRepository(private val service: RedditService): LoadPaginatedPostsRepository {

    private var modhash: String? = null
    private var after: String? = null

    override fun loadNext(size: Int): Single<List<Post>> =
        service.top(size, after, modhash)
                .doOnSuccess { modhash = it.data.modhash }
                .doOnSuccess { after = it.data.after }
                .flatMapObservable { Observable.fromIterable(it.data.children) }
                .map (::toPost)
                .toList()
                .subscribeOn(Schedulers.io())
}