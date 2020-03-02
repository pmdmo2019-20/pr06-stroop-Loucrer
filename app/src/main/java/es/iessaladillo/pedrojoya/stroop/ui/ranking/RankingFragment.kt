package es.iessaladillo.pedrojoya.stroop.ui.ranking

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.data.baseData.AppDatabase
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.Game
import es.iessaladillo.pedrojoya.stroop.extensions.invisibleUnless
import kotlinx.android.synthetic.main.ranking_fragment.*
import kotlinx.android.synthetic.main.ranking_fragment.toolbar


class RankingFragment : Fragment(R.layout.ranking_fragment) {

    private lateinit var listAdapter: RankingAdapter

    private val viewmodel: RankingViewModel by viewModels {
        RankingViewModelFactory(
            AppDatabase.getInstance
                (this.requireContext()).gameDao)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        setupToolbar()
        setupAdapter()
        setupRecyclerView()
        observeLiveData()
        observeSpinner()
    }

    private fun observeSpinner(){
        spFilter.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                setupBtns()
            }

        }
    }


    private fun setupBtns() {
        when {
            spFilter.selectedItem.toString().toLowerCase() == "all" -> {
                var games = viewmodel.queryAllUserGames()
                showGames(games)
            }
            spFilter.selectedItem.toString().toLowerCase() == "time" -> {
                var games = viewmodel.queryAllUserGamesTime()
                showGames(games)
            }
            spFilter.selectedItem.toString().toLowerCase() == "attempts" -> {
                var games = viewmodel.queryAllUserGamesAttempts()
                showGames(games)
            }
        }
    }

    private fun observeLiveData() {
        viewmodel.games.observe(this) {
            showGames(it)
        }
    }

    private fun showGames(game: List<Game>) {
        lstGames.post {
            listAdapter.submitList(game)
            imgEmptyRV.invisibleUnless(game.isEmpty())
            lblEmptyViewSeparator.invisibleUnless(game.isEmpty())
        }
    }

    private fun setupRecyclerView() {
        lstGames.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapter = listAdapter
        }
    }

    private fun setupAdapter() {
        listAdapter = RankingAdapter(activity!!.application)
    }

    private fun setupToolbar() {
        toolbar.inflateMenu(R.menu.fragments_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.InfoDialogDestination -> findNavController().navigate(R.id.infoDialogDestination,
                    bundleOf(
                        getString(R.string.ARG_MESSAGE) to getString(R.string.ranking_help_description))
                )
            }
            true
        }
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)
    }

}
