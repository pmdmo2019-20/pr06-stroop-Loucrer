package es.iessaladillo.pedrojoya.stroop.ui.ranking

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.GameDao
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.Game

class RankingViewModel(private val gameDao: GameDao): ViewModel() {

    // Lista de partidas jugadas
    private val _currentGameList : MutableLiveData<List<Game>> = MutableLiveData()
    val games: LiveData<List<Game>>
        get() = _currentGameList

    init {
        queryAllUserGames()
    }


    fun queryAllUserGames(): List<Game>{
      _currentGameList.value = gameDao.queryAllGames()
      return gameDao.queryAllGames()
    }


    fun queryAllUserGamesTime(): List<Game>{
        _currentGameList.value = gameDao.queryAllGameForTime()
        return gameDao.queryAllGameForTime()
    }


    fun queryAllUserGamesAttempts(): List<Game>{
        _currentGameList.value = gameDao.queryAllGameForAttempts()
        return gameDao.queryAllGameForAttempts()
    }


}