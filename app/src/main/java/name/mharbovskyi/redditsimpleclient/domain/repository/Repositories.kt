package name.mharbovskyi.redditsimpleclient.domain.repository

import io.reactivex.Single
import name.mharbovskyi.redditsimpleclient.domain.model.Post

interface SavePostsRepository {
    fun save(posts: List<Post>)
}

interface LoadPaginatedPostsRepository {
    fun loadNext(size: Int = 10): Single<List<Post>>
}