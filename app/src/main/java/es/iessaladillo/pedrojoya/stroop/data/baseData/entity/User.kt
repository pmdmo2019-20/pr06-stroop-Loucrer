package es.iessaladillo.pedrojoya.stroop.data.baseData.entity

import androidx.room.*

@Entity(
    indices = [
        Index(
            name = "USERS_INDEX_NAME_UNIQUE",
            value = ["userName"],
            unique = true
        )
    ]
)
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Long,
    var userName: String,
    val imageId: Int
)