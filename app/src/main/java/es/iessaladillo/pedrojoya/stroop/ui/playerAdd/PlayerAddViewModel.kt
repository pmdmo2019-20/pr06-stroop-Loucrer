package es.iessaladillo.pedrojoya.stroop.ui.playerAdd

import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.UserDao
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.User
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.stroop.base.Event

import java.lang.Exception
import kotlin.concurrent.thread

// Necesitamos un viewModel personalizado para poder trabajar con el contexto de la aplicacion y un usuario
class PlayerAddViewModel( private val userDao: UserDao, application: Application) : ViewModel() {

    private val _onBack : MutableLiveData<Event<Boolean>> = MutableLiveData()
    val onBack : LiveData<Event<Boolean>> get()=_onBack

    private val _message : MutableLiveData<Event<String>> = MutableLiveData()
    val message : LiveData<Event<String>> get()=_message

    private val _currentPlayerId : MutableLiveData<Long> = MutableLiveData(0)
    val currentPlayerId : LiveData<Long>
        get()=_currentPlayerId

    fun addUser(userName: String, imageUser: Int){
        thread {
            try {
                userDao.insertUser(User(0,userName,imageUser))
                _onBack.postValue(Event(true))

            }catch (e: Exception){
                _message.postValue(Event("ERROR"))
            }
        }
    }

    fun setCurrentPlayerId(userId: Long){
        _currentPlayerId.value = userId
    }

}