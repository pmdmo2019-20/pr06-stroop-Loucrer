package es.iessaladillo.pedrojoya.stroop.ui.dashboard

import android.content.SharedPreferences
import android.os.Bundle
import androidx.core.os.bundleOf

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.data.baseData.AppDatabase
import es.iessaladillo.pedrojoya.stroop.data.models.Card
import kotlinx.android.synthetic.main.dashboard_fragment.*
import kotlinx.android.synthetic.main.dashboard_fragment.toolbar

class DashboardFragment : Fragment(R.layout.dashboard_fragment){

    private lateinit var dashboardAdapter: DashBoardFragmentAdapter

    // variable settings importante para obtener todos los valores que guardemos
    val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(activity)
    }

    private val viewModel: DashBoardViewModel by viewModels {
        DashboardViewModelFactory(
            AppDatabase.getInstance(this.requireContext()).userDao,
            requireActivity().application
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.setCurrentUserId(settings.getLong("currentPlayer",-1))
        setupViews()
    }

    private fun setupViews() {
        setupToolbar()
        setupAdapter()
        setupRecyclerView()
        observeUser()
        setupBtns()
    }

    private fun setupBtns() {
        imgCurrentPlayer.setOnClickListener {
            findNavController().navigate(R.id.navigateToPlayer)
        }

        lblPlayerSelected.setOnClickListener {
            findNavController().navigate(R.id.navigateToPlayer)
        }
    }

    private fun observeUser() {
        if (settings.getLong("currentPlayer", -1) != -1L) {
            viewModel.currentUserId.observe(this) {
                var user = viewModel.queryUser(it)
                imgCurrentPlayer.setImageResource(user.userImgId)
                lblPlayerSelected.text = user.userName
            }
        } else {
            imgCurrentPlayer.setImageResource(R.drawable.logo)
            lblPlayerSelected.text = getString(R.string.app_name)
        }
    }

    //Cargar la toolbar
    private fun setupToolbar() {
        //Atento a los imports de donde obtenemos el id de la toolbar
        toolbar.inflateMenu(R.menu.fragments_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.InfoDialogDestination -> findNavController().navigate(R.id.infoDialogDestination,
                    bundleOf(
                        getString(R.string.ARG_MESSAGE) to getString(R.string.dashboard_help_description))
                )
            }
            true
        }
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)
    }

    private fun setupRecyclerView() {
        lstCards.run {
            setHasFixedSize(true)
            // Configurar como queremos que se muestre el grid del recyclerView
            layoutManager = GridLayoutManager(activity, 2)
            // Le pasamos el adaptador de la dashboard
            adapter = dashboardAdapter

        }
    }

    private fun setupAdapter() {
        // Usar ArrayList<> da menos problemas que List<>
        val cardList: ArrayList<Card> = arrayListOf()

        // Añadimos al array todos los objetos que tiene que pintar el adaptador.
        cardList.add(
            Card(
                R.drawable.ic_play_black_24dp,
                getString(R.string.dashboard_lblPlay),
                R.color.playOption
            )
        )
        cardList.add(
            Card(
                R.drawable.ic_settings_black_24dp,
                getString(R.string.dashboard_lblSettings),
                R.color.settingsOption
            )
        )
        cardList.add(
            Card(
                R.drawable.ic_ranking_black_24dp,
                getString(R.string.dashboard_lblRanking),
                R.color.rankingOption
            )
        )
        cardList.add(
            Card(
                R.drawable.ic_assistant_black_24dp,
                getString(R.string.dashboard_lblAssistant),
                R.color.assistantOption
            )
        )
        cardList.add(
            Card(
                R.drawable.ic_player_black_24dp,
                getString(R.string.dashboard_lblPlayer),
                R.color.playerOption
            )
        )
        cardList.add(
            Card(
                R.drawable.ic_about_black_24dp,
                getString(R.string.dashboard_lblAbout),
                R.color.aboutOption
            )
        )

        // Le pasamos la lista de cartas para pintar
        dashboardAdapter = DashBoardFragmentAdapter(cardList).also {
            it.onItemClickListener = { position -> navigateToPosition(position) }
        }
    }

    private fun navigateToPosition(position: Int) {
        when(position){
            0 ->
                if(settings.getLong("currentPlayer", -1) == -1L){
                    findNavController().navigate(R.id.navigateToPlayer)
                }else{
                    findNavController().navigate(R.id.navigateToGame)
                }
            1 -> findNavController().navigate(R.id.navigateToSetting)
            2 -> findNavController().navigate(R.id.navigateToRanking)
            3 -> findNavController().navigate(R.id.navigateToAssitant)
            4 -> findNavController().navigate(R.id.navigateToPlayer)
            5 -> findNavController().navigate(R.id.navigateToAbout)
        }
    }

}