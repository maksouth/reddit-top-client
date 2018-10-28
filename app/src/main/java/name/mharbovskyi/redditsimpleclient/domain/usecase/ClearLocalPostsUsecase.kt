package name.mharbovskyi.redditsimpleclient.domain.usecase

import android.util.Log
import name.mharbovskyi.redditsimpleclient.domain.ConnectionChecker
import name.mharbovskyi.redditsimpleclient.domain.repository.LocalRepository

class ClearLocalPostsUsecase (
    private val localRepository: LocalRepository,
    private val connectionChecker: ConnectionChecker
) {
    fun execute() =
        connectionChecker.isConnected()
            .filter { it }
            .subscribe { localRepository.clear() }
}