package name.mharbovskyi.redditsimpleclient.data.di

import dagger.Module
import dagger.Provides
import name.mharbovskyi.redditsimpleclient.data.retrofit.RedditService
import name.mharbovskyi.redditsimpleclient.data.retrofit.RemoteLoadPostsRepository
import name.mharbovskyi.redditsimpleclient.data.room.RoomPostDao
import name.mharbovskyi.redditsimpleclient.data.room.RoomPostsRepository
import name.mharbovskyi.redditsimpleclient.domain.repository.LoadPaginatedPostsRepository
import name.mharbovskyi.redditsimpleclient.domain.repository.LocalRepository
import javax.inject.Named

@Module(includes = [NetworkModule::class, RoomModule::class])
class DataSourceModule {

    @Provides
    @Named(REMOTE)
    fun provideRemoteLoadPostsRepository(service: RedditService)
            : LoadPaginatedPostsRepository =
        RemoteLoadPostsRepository(service)

    @Provides
    fun provideLocalPostsRepository(postDao: RoomPostDao)
            : LocalRepository =
        RoomPostsRepository(postDao)

    companion object {
        const val REMOTE = "remote"
        const val LOCAL = "local"
    }

}