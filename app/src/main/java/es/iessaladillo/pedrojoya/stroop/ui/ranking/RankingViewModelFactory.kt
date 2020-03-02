package es.iessaladillo.pedrojoya.stroop.ui.ranking

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.GameDao

class RankingViewModelFactory(private val userGameDao: GameDao): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        RankingViewModel(userGameDao) as T
}