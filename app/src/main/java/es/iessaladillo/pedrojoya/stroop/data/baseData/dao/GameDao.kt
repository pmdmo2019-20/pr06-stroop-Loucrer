package es.iessaladillo.pedrojoya.stroop.data.baseData.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.Game


@Dao
interface GameDao {

    @Query("SELECT * FROM Game ORDER BY points DESC")
    fun queryAllGames(): List<Game>

    @Query("SELECT * FROM Game WHERE gameMode = \"attempts\" ORDER BY points DESC")
    fun queryAllGameForAttempts(): List<Game>

    @Query("SELECT * FROM Game WHERE gameMode = \"time\" ORDER BY points DESC")
    fun queryAllGameForTime(): List<Game>

    @Query("SELECT * FROM Game ORDER BY gameId DESC LIMIT 1")
    fun queryLatesGamePlay(): Game

    @Insert
    fun insertGame(game: Game)

}