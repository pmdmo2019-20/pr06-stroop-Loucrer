package es.iessaladillo.pedrojoya.stroop.data.baseData.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Game"
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