package es.iessaladillo.pedrojoya.stroop.data.baseData.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import es.iessaladillo.pedrojoya.stroop.data.pojo.UserWithGame

@Dao
interface UserWithGameDao {

    @Transaction
    @Query("SELECT * FROM User u, Game g WHERE u.userId = g.userId ORDER BY points DESC")
    fun queryAllGames(): List<UserWithGame>

    @Transaction
    @Query("SELECT * FROM User u, Game g WHERE u.userId = g.userId AND gameMode = \"attempts\" ORDER BY points DESC")
    fun queryAllGameForAttempts(): List<UserWithGame>

    @Transaction
    @Query("SELECT * FROM User u, Game g WHERE u.userId = g.userId AND gameMode = \"time\" ORDER BY points DESC")
    fun queryAllGameForTime(): List<UserWithGame>

}