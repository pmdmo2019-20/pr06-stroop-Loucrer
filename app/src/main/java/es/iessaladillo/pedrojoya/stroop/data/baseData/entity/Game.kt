package es.iessaladillo.pedrojoya.stroop.data.baseData.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
)
data class Game(
    @PrimaryKey(autoGenerate = true) val gameId: Long,
    val gameMode: String,
    val totalTime: Int,
    val totalWords: Int,
    val wrongs: Int,
    val corrects: Int,
    val points: Int
)