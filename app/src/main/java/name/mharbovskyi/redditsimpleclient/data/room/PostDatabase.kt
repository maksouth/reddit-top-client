package name.mharbovskyi.redditsimpleclient.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [RoomPost::class], version = 1)
abstract class PostDatabase: RoomDatabase() {
    abstract fun postDao(): RoomPostDao

    companion object {
        const val POSTS_DB_NAME = "posts_database.db"
    }
}