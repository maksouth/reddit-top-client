package name.mharbovskyi.redditsimpleclient.data.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class RoomPost(
    @PrimaryKey
    val id: String,
    val title: String,
    val createdSec: Long, // published on Reddit
    val author: String,
    val thumbnailUrl: String?,
    val contentUrl: String,
    val commentsCount: Int,
    val addedSec: Long // saved to Room
)