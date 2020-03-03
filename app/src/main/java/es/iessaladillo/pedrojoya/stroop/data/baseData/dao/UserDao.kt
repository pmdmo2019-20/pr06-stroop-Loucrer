package es.iessaladillo.pedrojoya.stroop.data.baseData.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.User
import es.iessaladillo.pedrojoya.stroop.data.pojo.UserWithGame

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deletedUser(user: User)

    @Query("SELECT * FROM user")
    fun queryAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM User WHERE userId = :userId")
    fun queryUser(userId: Long): User

}