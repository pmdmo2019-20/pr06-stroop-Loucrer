package es.iessaladillo.pedrojoya.stroop.ui.playerEdit

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.stroop.base.Event
import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.UserDao
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.User
import java.lang.Exception
import kotlin.concurrent.thread

class PlayerEditViewModel(private val userDao: UserDao): ViewModel() {

    private val _onBack : MutableLiveData<Event<Boolean>> = MutableLiveData()
    val onBack : LiveData<Event<Boolean>> get()=_onBack

    private val _message : MutableLiveData<Event<String>> = MutableLiveData()
    val message : LiveData<Event<String>> get() = _message

    private val _currentPlayerAvatar : MutableLiveData<Int> = MutableLiveData()
    val currentPlayerAvatar : LiveData<Int>
        get()=_currentPlayerAvatar


    fun queryUser(userId: Long): User {
        return userDao.queryUser(userId)
    }

    fun setCurrentPlayerAvatar(avatar : Int){
        _currentPlayerAvatar.value = avatar
    }

    // Consulta de actualizacion/delete siempre hacerlo en un hilo secundario
    fun updateUser(userId: Long,userName: String, imageUser: Int){
        thread {
            try {
                userDao.updateUser(User(userId,userName,imageUser))
                _onBack.postValue(Event(true)) // Para volver atras despues de actualizarce

            }catch (e: Exception){
                _message.postValue(Event("ERROR"))
            }

        }
    }

}