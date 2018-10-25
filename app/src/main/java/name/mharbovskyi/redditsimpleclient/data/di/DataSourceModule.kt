package name.mharbovskyi.redditsimpleclient.data.di

import dagger.Module
import dagger.Provides
import name.mharbovskyi.redditsimpleclient.StubLocalDataSource
import name.mharbovskyi.redditsimpleclient.data.datasource.network.RedditService
import name.mharbovskyi.redditsimpleclient.data.datasource.repository.RemoteLoadPostsRepository
import name.mharbovskyi.redditsimpleclient.domain.repository.LoadPaginatedPostsRepository
import name.mharbovskyi.redditsimpleclient.presentation.di.scope.ActivityScope
import javax.inject.Named

@Module(includes = [NetworkModule::class])
class DataSourceModule {

    @Provides
    @Named(REMOTE)
    fun providesRemoteLoadPostsRepository(service: RedditService)
            : LoadPaginatedPostsRepository =
            RemoteLoadPostsRepository(service)

    @Provides
    @Named(LOCAL)
    fun providesLocalLoadPostsRepository()
            : LoadPaginatedPostsRepository =
        StubLocalDataSource()

    companion object {
        const val REMOTE = "remote"
        const val LOCAL = "local"
    }

}