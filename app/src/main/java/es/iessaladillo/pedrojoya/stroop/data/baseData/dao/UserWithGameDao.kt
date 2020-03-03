package es.iessaladillo.pedrojoya.stroop.data.baseData.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.UserGame
import es.iessaladillo.pedrojoya.stroop.data.pojo.UserWithGame

@Dao
interface UserWithGameDao {

    @Transaction
    @Query("SELECT * FROM User us, Game g, UserGame ug WHERE us.userId = ug.userId AND g.gameId = ug.gameId ORDER BY points DESC LIMIT 10")
    fun queryAllGames(): List<UserWithGame>

    @Transaction
    @Query("SELECT * FROM User u, Game g, UserGame ug WHERE u.userId = ug.userId AND g.gameId = ug.gameId AND gameMode = \"attempts\" ORDER BY points DESC LIMIT 10")
    fun queryAllGameForAttempts(): List<UserWithGame>

    @Transaction
    @Query("SELECT * FROM User u, Game g, UserGame ug WHERE u.userId = ug.userId AND g.gameId = ug.gameId AND gameMode = \"time\" ORDER BY points DESC LIMIT 10")
    fun queryAllGameForTime(): List<UserWithGame>

    @Insert
    fun insertUserGame(UserGame: UserGame)

}