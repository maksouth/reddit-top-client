package name.mharbovskyi.redditsimpleclient.data.di

import dagger.Module
import dagger.Provides
import name.mharbovskyi.redditsimpleclient.data.retrofit.RedditService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    internal fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    internal fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .baseUrl(RedditService.BASE_URL)
            .build()

    @Provides
    internal fun provideRedditService(retrofit: Retrofit) =
        retrofit.create(RedditService::class.java)
}
