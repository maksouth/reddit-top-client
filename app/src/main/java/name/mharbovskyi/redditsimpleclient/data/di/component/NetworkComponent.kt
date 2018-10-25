package name.mharbovskyi.redditsimpleclient.data.di.component

import dagger.Component
import name.mharbovskyi.redditsimpleclient.data.datasource.network.RedditService
import name.mharbovskyi.redditsimpleclient.data.di.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent {
    fun redditService(): RedditService
}