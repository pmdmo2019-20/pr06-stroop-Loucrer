package es.iessaladillo.pedrojoya.stroop.data.baseData.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(
    primaryKeys = ["userId", "gameId"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onUpdate = CASCADE,
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Game::class,
            parentColumns = ["gameId"],
            childColumns = ["gameId"],
            onUpdate = CASCADE,
            onDelete = CASCADE
        )
    ]
    )
data class UserGame(
    @ColumnInfo(index = true)
    val userId: Long,
    @ColumnInfo(index = true)
    val gameId: Long
)