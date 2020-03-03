package es.iessaladillo.pedrojoya.stroop.ui.playerAdd

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.avatars
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.base.observeEvent
import es.iessaladillo.pedrojoya.stroop.data.baseData.AppDatabase
import es.iessaladillo.pedrojoya.stroop.extensions.hideSoftKeyboard
import kotlinx.android.synthetic.main.player_fragment_add.*

class PlayerFragmentAdd: Fragment(R.layout.player_fragment_add) {

    private lateinit var playerAddAdapter: PlayerAddAdapter

    private val viewModel: PlayerAddViewModel by viewModels {
        PlayerAddViewModelFactory(
            AppDatabase.getInstance(this.requireContext()).userDao,
            requireActivity().application
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        setupToolbar()
        setupAdapter()
        setupRecyclerView()
        showPlayers(avatars)
        fabSave.setOnClickListener { save() }
        observeEvents()
    }

    private fun observeEvents() {
        viewModel.message.observeEvent(this) {
            Snackbar.make(lstAvatars, it, Snackbar.LENGTH_SHORT).show()
        }
        viewModel.onBack.observeEvent(this){
            if(it){
                activity!!.onBackPressed()
            }
        }
        viewModel.currentPlayerId.observe(this){
            // Si el id es 0 cargamos el por defecto
            // Si no cargamos la imagen de dicho jugador
            if(it == 0L){
                imgCurrentPlayer.setImageResource(R.drawable.logo)
            }else{
                imgCurrentPlayer.setImageResource(it.toInt())
            }
        }
    }


    private fun save() {
        if (lblActualPlayerEdit.text.isNotEmpty() && viewModel.currentPlayerId.value != 0L) {
            viewModel.addUser(lblActualPlayerEdit.text.toString(), viewModel.currentPlayerId.value!!.toInt())
            lblActualPlayerEdit.hideSoftKeyboard()
        }
    }

    private fun showPlayers(avatars: List<Int>) {
        playerAddAdapter.submitList(avatars)
    }


    private fun setupToolbar() {
        toolbar.inflateMenu(R.menu.fragments_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.InfoDialogDestination -> findNavController().navigate(R.id.infoDialogDestination,
                    bundleOf(
                        getString(R.string.ARG_MESSAGE) to getString(R.string.player_creation_help_description))
                )
            }
            true
        }
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)

    }

    private fun setupRecyclerView() {
        lstAvatars.run {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 3)
            adapter = playerAddAdapter
        }
    }

    private fun setupAdapter() {
        playerAddAdapter = PlayerAddAdapter().also {
            it.onItemClickListener = {
                position -> selectAvatar(position)
                playerAddAdapter.posicionAvatar = position // Le asignamos la posicion al usuario clickeado
                playerAddAdapter.notifyDataSetChanged() // Notificamos a todos del cambio realizado
        } }
    }

    private fun selectAvatar(position: Int) {
        viewModel.setCurrentPlayerId(avatars[position].toLong())
    }



}