package es.iessaladillo.pedrojoya.stroop.data.pojo

import androidx.room.Embedded
import androidx.room.Relation
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.Game
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.User

data class UserWithGame(
    val imageId: Int,
    val userName: String,
    val gameMode: String,
    val totalTime: Int,
    val totalWords: Int,
    val corrects: Int,
    val points: Int

)