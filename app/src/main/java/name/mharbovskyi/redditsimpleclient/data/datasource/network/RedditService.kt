package name.mharbovskyi.redditsimpleclient.data.datasource.network

import io.reactivex.Single
import name.mharbovskyi.redditsimpleclient.data.model.TopEntriesResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RedditService {
    @GET(ENDPOINT_TOP_JSON)
    fun top(
            @Query(PARAM_LIMIT) limit: Int,
            @Query(PARAM_AFTER) after: String?,
            @Header(HEADER_MODHASH) modhash: String?
    ) : Single<TopEntriesResponse>

    companion object {
        const val BASE_URL = "https://www.reddit.com"
        const val ENDPOINT_TOP_JSON = "/top/.json"
        const val PARAM_LIMIT = "limit"
        const val PARAM_AFTER = "after"
        const val HEADER_MODHASH = "X-Modhash"
    }
}