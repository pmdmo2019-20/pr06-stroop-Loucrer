package es.iessaladillo.pedrojoya.stroop.ui.dashboard

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.UserDao

class DashBoardViewModel(private val userDao: UserDao, private val application: Application) : ViewModel() {

    private val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(application)
    }

    private val _currentUserId: MutableLiveData<Long> = MutableLiveData()
    val currentUserId: LiveData<Long>
        get() = _currentUserId


    // Para cargar el valor inicial de usuario de la aplicacion
    init {
        _currentUserId.value = settings.getLong("currentPlayer", -1)
    }


    fun queryUser(userId: Long) = userDao.queryUser(userId)


    fun setCurrentUserId(userId: Long){
        _currentUserId.value = userId
    }


}