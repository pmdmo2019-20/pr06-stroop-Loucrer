package es.iessaladillo.pedrojoya.stroop.ui.playerEdit

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.UserDao

class PlayerEditViewModelFactory(private val userDao: UserDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        PlayerEditViewModel(userDao) as T
}