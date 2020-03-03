package es.iessaladillo.pedrojoya.stroop.data.pojo

import androidx.room.Embedded
import androidx.room.Relation
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.Game
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.User

data class UserWithGame(
    @Embedded
    val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val game: Game
)