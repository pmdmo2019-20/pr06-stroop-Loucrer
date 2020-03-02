package es.iessaladillo.pedrojoya.stroop.data.baseData.entity

import androidx.room.*

@Entity(
    tableName = "User",
    indices = [
        Index(
            name = "USERS_INDEX_NAME_UNIQUE",
            value = ["userName"],
            unique = true
        )
    ])
data class User (
    @PrimaryKey(autoGenerate = true) val userId: Long,
    @ColumnInfo(name = "userName")
    val userName: String,
    @ColumnInfo(name = "userImgId")
    val userImgId: Int
)