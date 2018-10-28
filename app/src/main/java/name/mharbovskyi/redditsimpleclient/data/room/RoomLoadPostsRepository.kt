package name.mharbovskyi.redditsimpleclient.data.room

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import name.mharbovskyi.redditsimpleclient.domain.model.Post
import name.mharbovskyi.redditsimpleclient.domain.repository.LoadPaginatedPostsRepository
import name.mharbovskyi.redditsimpleclient.domain.repository.SavePostsRepository

class RoomLoadPostsRepository(
    private val postsDao: RoomPostDao
) : LoadPaginatedPostsRepository, SavePostsRepository
{
    private var afterAddedSec: Long? = null

    override fun save(posts: List<Post>) {
        Completable.fromCallable {
            val roomPosts = posts.toRoomPostList()
            postsDao.insertAll(roomPosts)
        }.subscribeOn(Schedulers.io())
            .subscribe()
    }

    override fun loadNext(size: Int): Single<List<Post>> =
        Single.fromCallable {
            val after = afterAddedSec
            if (after == null) {
                postsDao.get(size)
            } else postsDao.getAfter(after, size)
        }
            .map { it.toPostList() }
            .subscribeOn(Schedulers.io())
}