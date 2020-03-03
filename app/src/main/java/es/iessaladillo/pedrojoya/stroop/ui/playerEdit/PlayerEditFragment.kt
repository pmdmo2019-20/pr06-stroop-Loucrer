package es.iessaladillo.pedrojoya.stroop.ui.playerEdit

import android.content.SharedPreferences
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.avatars
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.base.observeEvent
import es.iessaladillo.pedrojoya.stroop.data.baseData.AppDatabase
import es.iessaladillo.pedrojoya.stroop.extensions.hideSoftKeyboard
import kotlinx.android.synthetic.main.item_avatar_player.*
import kotlinx.android.synthetic.main.player_fragment_edit.*

class PlayerEditFragment: Fragment(R.layout.player_fragment_edit) {

    private lateinit var playerEditAdapter: PlayerEditAdapter

    private val viewModel: PlayerEditViewModel by viewModels {
        PlayerEditViewModelFactory(
            AppDatabase.getInstance(this.requireContext()).userDao
        )
    }


    // Obtenemos el ID pasado por el bundle que hemos pasado por el navigate.
    private val userId: Long by lazy {
        arguments!!.getLong(getString(R.string.ARGS_USER_ID))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
        viewModel.setCurrentPlayerAvatar(avatars.indexOf(viewModel.queryUser(userId).imageId))
    }

    private fun setupViews() {
        setupToolbar()
        setupAdapter()
        setupRecyclerView()
        submitListAvatars(avatars)
        setupCurrentUser()
        observeEvents()
        fabSave.setOnClickListener { save() }
    }

    private fun save() {
        viewModel.currentPlayerAvatar.observe(this) {
            if (lblActualPlayerEdit.text.isNotEmpty()){
                viewModel.updateUser(
                    userId,
                    lblActualPlayerEdit.text.toString(),
                    avatars[viewModel.currentPlayerAvatar.value!!]
                )
            }
        }
        lblActualPlayerEdit.hideSoftKeyboard()
        imgCurrentPlayerEdit.hideSoftKeyboard()
    }

    private fun observeEvents() {
        viewModel.message.observeEvent(this) {
            Snackbar.make(lstAvatars, it, Snackbar.LENGTH_SHORT).show()
        }
        viewModel.onBack.observeEvent(this) {
            if (it) {
                activity!!.onBackPressed()
            }
        }
    }

    private fun setupCurrentUser() {
        lblActualPlayerEdit.setText(viewModel.queryUser(userId).userName)
        imgCurrentPlayerEdit.setImageResource(viewModel.queryUser(userId).imageId)
    }

    private fun submitListAvatars(avatars: List<Int>) {
        playerEditAdapter.submitList(avatars)
    }

    private fun setupRecyclerView() {
        lstAvatars.run {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 3)
            adapter = playerEditAdapter
        }
    }

    private fun setupAdapter() {
        playerEditAdapter = PlayerEditAdapter().also {
            it.onItemClickListener = { position -> selectAvatar(position) }
        }
    }

    private fun selectAvatar(position: Int) {
        viewModel.setCurrentPlayerAvatar(position)
        viewModel.currentPlayerAvatar.observe(this) {
            imgCurrentPlayerEdit.setImageResource(avatars[it])
        }
    }

    private fun setupToolbar() {
        toolbar.inflateMenu(R.menu.fragment_edit_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.deleteDestination -> findNavController().navigate(R.id.deletedDialogDestination,
                    bundleOf(
                        getString(R.string.ARGS_USER_DELETE) to userId)
                )

                R.id.InfoDialogDestination -> findNavController().navigate(R.id .infoDialogDestination,
                    bundleOf(
                        getString(R.string.ARG_MESSAGE) to getString(R.string.player_edition_help_description))
                )
            }
            true
        }
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)
    }

}