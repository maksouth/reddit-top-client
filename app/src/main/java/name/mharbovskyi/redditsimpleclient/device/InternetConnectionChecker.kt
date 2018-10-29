package name.mharbovskyi.redditsimpleclient.device

import android.net.ConnectivityManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.Single
import name.mharbovskyi.redditsimpleclient.domain.ConnectionChecker
import javax.inject.Inject

class InternetConnectionChecker @Inject
constructor(private val connectivityManager: ConnectivityManager)
    : ConnectionChecker {

    override fun isConnected(): Single<Boolean> {
        return Single.fromCallable{
            val isConnected: Boolean
            val networkInfo = connectivityManager.activeNetworkInfo
            isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting

            isConnected
        }
    }
}

@Module
interface DeviceModule {
    @Binds fun bindsInternetConnectionChecker(
        internetConnectionChecker: InternetConnectionChecker
    ): ConnectionChecker
}