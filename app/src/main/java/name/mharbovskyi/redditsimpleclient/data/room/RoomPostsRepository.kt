package name.mharbovskyi.redditsimpleclient.data.room

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import name.mharbovskyi.redditsimpleclient.domain.model.Post
import name.mharbovskyi.redditsimpleclient.domain.repository.LocalRepository

class RoomPostsRepository(
    private val postsDao: RoomPostDao
) : LocalRepository {

    private var afterAddedSec: Long? = null

    override fun save(posts: List<Post>) {
        wrapCompletable {
            Log.d("RoomPostRepo", "saved ${posts.size} posts")
            val roomPosts = posts.toRoomPostList()
            postsDao.insertAll(roomPosts)
        }
    }

    override fun clear() {
        wrapCompletable {
            val cleared = postsDao.clear()
            Log.d("RoomPostRepo", "cleared $cleared posts")
        }
    }

    override fun loadNext(size: Int): Single<List<Post>> =
        Single.fromCallable {
            val after = afterAddedSec
            if (after == null) {
                postsDao.get(size)
            } else postsDao.getAfter(after, size)
        }
            .doOnSuccess { if(it.isNotEmpty()) afterAddedSec = it.last().addedSec }
            .map { it.toPostList() }
            .subscribeOn(Schedulers.io())

    private fun wrapCompletable(block: () -> Any) {
        Completable.fromCallable(block)
            .subscribeOn(Schedulers.io())
            .subscribe()

    }
}