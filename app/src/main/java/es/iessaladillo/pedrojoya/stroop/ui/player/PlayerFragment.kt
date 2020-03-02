package es.iessaladillo.pedrojoya.stroop.ui.player

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.core.content.edit
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
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.User
import es.iessaladillo.pedrojoya.stroop.extensions.invisibleUnless
import kotlinx.android.synthetic.main.item_avatar_player.*
import kotlinx.android.synthetic.main.player_fragment.*


class PlayerFragment : Fragment(R.layout.player_fragment) {

    private val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(activity)
    }

    private val viewModel: PlayerSelectionViewModel by viewModels {
        PlayerSelecctionViewModelFactory(
            AppDatabase.getInstance(this.requireContext()).userDao,
            requireActivity().application
        )
    }

    private lateinit var playerAdapter: PlayerAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupView()
    }

    private fun setupView() {
        setupToolBar()
        setupAdapter()
        setupRecyclerView()
        setupBtns()
        observeLV()
        setupCurrentPlayer()
    }

    private fun observeLV() {
        viewModel.users.observe(this) {
            showPlayers(it)
        }
    }

    private fun showPlayers(users: List<User>) {
        lstPlayers.post {
            playerAdapter.submitList(users)
            imgAddPlayer.invisibleUnless(users.isEmpty())
            lblEmptyView.invisibleUnless(users.isEmpty())
        }
    }

    private fun setupCurrentPlayer() {
        viewModel.currentUserId.observe(this) {
            if (viewModel.currentUserId.value != -1L && settings.getLong("currentPlayer",-1) != -1L) {
                var user = viewModel.queryUser(it)
                lblCurrentPlayer.text = user.userName
                imgCurrentPlayer.setImageResource(user.userImgId)
                btnEdit.visibility = View.VISIBLE
            } else {
                lblCurrentPlayer.text = getString(R.string.player_selection_no_player_selected)
                imgCurrentPlayer.setImageResource(R.drawable.logo)
            }
        }
    }

    private fun setupBtns() {
        btnEdit.setOnClickListener { navigateToEditPlayer() }
        fabAdd.setOnClickListener { navigateToAddPlayer() }
        imgAddPlayer.setOnClickListener { navigateToAddPlayer() }
        lblEmptyView.setOnClickListener { navigateToAddPlayer() }
    }

    private fun navigateToEditPlayer() {
        findNavController().navigate(R.id.navigateToEdit)
    }

    private fun navigateToAddPlayer() {
        findNavController().navigate(R.id.navigateToAddPlayer)
    }

    private fun setupAdapter() {
        playerAdapter = PlayerAdapter().also { it ->
            it.onItemClickListener = {
                selectCurrentPlayer(it)
            }
        }
    }

    private fun selectCurrentPlayer(position: Int) {
        // Editamos en los settings el jugador que estará como activo para la app
        settings.edit {
            putLong("currentPlayer", playerAdapter.userList[position].userId)
        }
        // Establecemos el usuario seleccionado de la lista con dicho id
        viewModel.setCurrentUserId(playerAdapter.userList[position].userId)

    }

    private fun setupRecyclerView() {
        lstPlayers.run {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 2)
            adapter = playerAdapter
        }
    }

    private fun setupToolBar() {
        toolbar.inflateMenu(R.menu.fragments_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.InfoDialogDestination -> findNavController().navigate(
                    R.id.infoDialogDestination,
                    bundleOf(
                        getString(R.string.ARG_MESSAGE) to getString(R.string.player_selection_help_description)
                    )
                )
            }
            true
        }
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)
    }

}
