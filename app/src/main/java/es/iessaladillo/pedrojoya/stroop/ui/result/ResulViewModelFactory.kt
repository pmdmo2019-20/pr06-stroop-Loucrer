package es.iessaladillo.pedrojoya.stroop.ui.result

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.GameDao
import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.UserDao
import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.UserWithGameDao

class ResulViewModelFactory(private val gameDao: GameDao, private val userDao: UserDao, private val userWithGameDao: UserWithGameDao,
                            private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ResultViewModel(gameDao, userDao, userWithGameDao, application) as T
}
