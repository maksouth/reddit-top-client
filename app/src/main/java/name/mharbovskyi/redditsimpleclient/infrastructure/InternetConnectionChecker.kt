package name.mharbovskyi.redditsimpleclient.infrastructure

import android.net.ConnectivityManager
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