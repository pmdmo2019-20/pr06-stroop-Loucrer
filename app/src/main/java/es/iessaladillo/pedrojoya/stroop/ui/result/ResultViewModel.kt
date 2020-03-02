package es.iessaladillo.pedrojoya.stroop.ui.result

import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.GameDao

class ResultViewModel(private val gameDao: GameDao): ViewModel() {

    fun getGame() = gameDao.queryLatesGamePlay()

}