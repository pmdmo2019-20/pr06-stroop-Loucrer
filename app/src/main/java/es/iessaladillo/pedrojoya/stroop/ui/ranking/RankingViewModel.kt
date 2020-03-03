package es.iessaladillo.pedrojoya.stroop.ui.ranking

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.GameDao
import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.UserWithGameDao
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.Game
import es.iessaladillo.pedrojoya.stroop.data.pojo.UserWithGame

class RankingViewModel(private val userWithGameDao: UserWithGameDao): ViewModel() {

    // Lista de partidas jugadas
    private val _currentGameList : MutableLiveData<List<UserWithGame>> = MutableLiveData()
    val games: LiveData<List<UserWithGame>>
        get() = _currentGameList

    init {
        queryAllUserGames()
    }


    fun queryAllUserGames(): List<UserWithGame>{
      _currentGameList.value = userWithGameDao.queryAllGames()
      return userWithGameDao.queryAllGames()
    }


    fun queryAllUserGamesTime(): List<UserWithGame>{
        _currentGameList.value = userWithGameDao.queryAllGameForTime()
        return userWithGameDao.queryAllGameForTime()
    }


    fun queryAllUserGamesAttempts(): List<UserWithGame>{
        _currentGameList.value = userWithGameDao.queryAllGameForAttempts()
        return userWithGameDao.queryAllGameForAttempts()
    }


}