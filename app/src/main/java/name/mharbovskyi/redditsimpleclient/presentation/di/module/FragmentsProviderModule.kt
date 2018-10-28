package name.mharbovskyi.redditsimpleclient.presentation.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import name.mharbovskyi.redditsimpleclient.presentation.view.PostListFragment

@Module
interface FragmentsProviderModule {
    @ContributesAndroidInjector
    fun contributesPostListFragment(): PostListFragment
}