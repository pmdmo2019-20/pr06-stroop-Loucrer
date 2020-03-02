package es.iessaladillo.pedrojoya.stroop.ui.player

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.UserDao
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.User

class PlayerSelectionViewModel(private val userDao: UserDao, private val application: Application): ViewModel() {

    // La lista de todos los usuarios de nuestra entidad
    val users: LiveData<List<User>> = queryAllUsers()


    // Setting de nuestra aplicacion para obtener todas las propiedades que almacene nuestra aplicacion en el sistema
    val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(application)
    }

    // id usuario actual
    private val _currentUserId : MutableLiveData<Long> = MutableLiveData()
    val currentUserId : LiveData<Long>
        get()=_currentUserId

    // Usuario establecido
    private val _currentUser : MutableLiveData<User> = MutableLiveData()
    val currentUser : LiveData<User>
        get()=_currentUser

    // Por defecto lo iniciamos con -1 para los valores por defecto
    init {
        _currentUserId.value = settings.getLong("currentPlayer",-1)
    }


    // Funcion donde obtendremos todos los usuarios de la entidad
    private fun queryAllUsers(): LiveData<List<User>> {
        return userDao.queryAllUsers()
    }

    // Obtenemos el usuario seleccionado
    fun queryUser(userId: Long) = userDao.queryUser(userId)


    // Establecer el id del usuario seleccionado
    fun setCurrentUserId(userId: Long){
        _currentUserId.value = userId
    }

}