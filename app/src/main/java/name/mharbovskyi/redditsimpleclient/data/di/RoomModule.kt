package name.mharbovskyi.redditsimpleclient.data.di

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import name.mharbovskyi.redditsimpleclient.data.room.PostDatabase

@Module
class RoomModule {
    @Provides
    fun provideDatabase(applicationContext: Context) =
            Room.databaseBuilder(
                applicationContext,
                PostDatabase::class.java,
                PostDatabase.POSTS_DB_NAME
            ).build()

    @Provides
    fun providePostDao(database: PostDatabase) =
            database.postDao()
}