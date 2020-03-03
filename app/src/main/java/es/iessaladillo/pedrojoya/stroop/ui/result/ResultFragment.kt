package es.iessaladillo.pedrojoya.stroop.ui.result

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.data.baseData.AppDatabase
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.Game
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.result_fragment.*
import kotlinx.android.synthetic.main.result_fragment.imgCurrentPlayer
import kotlinx.android.synthetic.main.result_fragment.toolbar

class ResultFragment: Fragment(R.layout.result_fragment) {

    private val viewModel : ResultViewModel by viewModels {
        ResulViewModelFactory(AppDatabase.getInstance(this.requireContext()).gameDao,
            AppDatabase.getInstance(this.requireContext()).userDao,
            AppDatabase.getInstance(this.requireContext()).userWithGameDao,
            requireActivity().application)
    }


    val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(activity)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolBar()
        setupViews()
        saveUserGame()
    }

    private fun saveUserGame() {
        viewModel.insertUserGame()
    }

    private fun setupToolBar() {
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)
    }

    private fun setupViews() {
        var gameResult = viewModel.getGame()
        setupViewsHeader(gameResult)
        setupViewsBody(gameResult)
    }

    private fun setupViewsHeader(gameResult: Game) {
        imgCurrentPlayer.setImageResource(viewModel.getCurrentUser(settings.getLong("currentPlayer", -1)).imageId)
        lblPlayerSelected.text = viewModel.getCurrentUser(settings.getLong("currentPlayer", -1)).userName
    }

    private fun setupViewsBody(gameResult: Game) {
        lblCountAnswers.text = gameResult.corrects.toString()
        lblCountIncorrects.text = gameResult.wrongs.toString()
        lblTotalPoints.text = gameResult.points.toString()
    }

}