package es.iessaladillo.pedrojoya.stroop.ui.result

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.GameDao
import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.UserDao
import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.UserWithGameDao
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.User
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.UserGame
import kotlin.concurrent.thread

class ResultViewModel(private val gameDao: GameDao,private val userDao: UserDao,private val userWithGameDao: UserWithGameDao, private val application: Application): ViewModel() {

    val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(application)
    }

    fun getGame() = gameDao.queryLatesGamePlay()

    fun getCurrentUser(id: Long) = userDao.queryUser(id)

    fun insertUserGame(){
        thread {
            userWithGameDao.insertUserGame(UserGame( settings.getLong("currentPlayer", -1), gameDao.queryLatesGamePlay().gameId))
        }
    }


}