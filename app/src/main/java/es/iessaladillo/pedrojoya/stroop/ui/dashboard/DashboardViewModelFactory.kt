package es.iessaladillo.pedrojoya.stroop.ui.dashboard

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.UserDao

class DashboardViewModelFactory(private val userDao: UserDao, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        DashBoardViewModel(userDao, application) as T
}