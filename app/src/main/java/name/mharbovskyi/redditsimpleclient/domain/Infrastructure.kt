package name.mharbovskyi.redditsimpleclient.domain

import io.reactivex.Single

interface ConnectionChecker {
    fun isConnected(): Single<Boolean>
}