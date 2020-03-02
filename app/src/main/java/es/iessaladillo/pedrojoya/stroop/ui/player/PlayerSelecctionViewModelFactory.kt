package es.iessaladillo.pedrojoya.stroop.ui.player

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.UserDao

class PlayerSelecctionViewModelFactory(private val userDao: UserDao, private val application: Application): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = PlayerSelectionViewModel(userDao, application) as T
}