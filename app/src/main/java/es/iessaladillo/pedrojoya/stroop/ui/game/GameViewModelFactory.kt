package es.iessaladillo.pedrojoya.stroop.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.GameDao
import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.UserDao

class GameViewModelFactory(private val gameDao: GameDao, private val userDao: UserDao): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            GameViewModel(gameDao,userDao) as T
}