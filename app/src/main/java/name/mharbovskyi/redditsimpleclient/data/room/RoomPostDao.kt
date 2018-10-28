package name.mharbovskyi.redditsimpleclient.data.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface RoomPostDao {
    @Query("SELECT * FROM RoomPost ORDER BY addedSec ASC LIMIT :count")
    fun get(count: Int): List<RoomPost>

    @Query("SELECT * FROM RoomPost WHERE addedSec > :after ORDER BY addedSec LIMIT :count")
    fun getAfter(after: Long, count: Int): List<RoomPost>

    @Insert
    fun insertAll(posts: List<RoomPost>)
}