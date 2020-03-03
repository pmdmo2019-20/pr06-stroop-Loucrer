package es.iessaladillo.pedrojoya.stroop.data.baseData.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "Game",
    foreignKeys = [
    ForeignKey(
        entity = User::class,
        parentColumns = ["userId"],
        childColumns = ["userId"],
        onUpdate = CASCADE,
        onDelete = CASCADE
    )
    ]
)
data class Game(
    @PrimaryKey(autoGenerate = true) val gameId: Long,
    @Embedded
    val user: User,
    val gameMode: String,
    val totalTime: Int,
    val totalWords: Int,
    val wrongs: Int,
    val corrects: Int,
    val points: Int
)