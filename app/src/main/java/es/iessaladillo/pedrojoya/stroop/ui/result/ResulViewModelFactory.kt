package es.iessaladillo.pedrojoya.stroop.ui.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.GameDao

class ResulViewModelFactory(private val gameDao: GameDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ResultViewModel(gameDao) as T
}
