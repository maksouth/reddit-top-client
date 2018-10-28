package name.mharbovskyi.redditsimpleclient.data.di

import dagger.Module
import dagger.Provides
import name.mharbovskyi.redditsimpleclient.StubLocalDataSource
import name.mharbovskyi.redditsimpleclient.data.retrofit.RedditService
import name.mharbovskyi.redditsimpleclient.data.retrofit.RemoteLoadPostsRepository
import name.mharbovskyi.redditsimpleclient.data.room.RoomLoadPostsRepository
import name.mharbovskyi.redditsimpleclient.data.room.RoomPostDao
import name.mharbovskyi.redditsimpleclient.domain.repository.LoadPaginatedPostsRepository
import name.mharbovskyi.redditsimpleclient.domain.repository.SavePostsRepository
import name.mharbovskyi.redditsimpleclient.presentation.di.scope.ActivityScope
import javax.inject.Named

@Module(includes = [NetworkModule::class, RoomModule::class])
class DataSourceModule {

    @Provides
    @Named(REMOTE)
    fun provideRemoteLoadPostsRepository(service: RedditService)
            : LoadPaginatedPostsRepository =
        RemoteLoadPostsRepository(service)

    @Provides
    @ActivityScope
    fun provideRoomRepository(postDao: RoomPostDao) =
        RoomLoadPostsRepository(postDao)

    @Provides
    @Named(LOCAL)
    fun provideLocalLoadPostsRepository(roomLoadPostsRepository: RoomLoadPostsRepository)
            : LoadPaginatedPostsRepository =
        roomLoadPostsRepository

    @Provides
    fun provideLocalSavePostsRepository(roomRepository: RoomLoadPostsRepository)
            : SavePostsRepository =
        roomRepository

    companion object {
        const val REMOTE = "remote"
        const val LOCAL = "local"
    }

}