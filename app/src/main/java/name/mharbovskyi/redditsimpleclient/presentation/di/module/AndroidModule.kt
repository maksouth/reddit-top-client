package name.mharbovskyi.redditsimpleclient.presentation.di.module

import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides

@Module
class AndroidModule {
    @Provides
    internal fun provideConnectionManager(context: Context): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}