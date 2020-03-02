package es.iessaladillo.pedrojoya.stroop.ui.settings

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import kotlinx.android.synthetic.main.settings_fragment.*

class SettingFragment: Fragment(R.layout.settings_fragment) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        setupToolbar()
    }

    private fun setupToolbar() {
        toolbar.inflateMenu(R.menu.fragments_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.InfoDialogDestination -> findNavController().navigate(R.id.infoDialogDestination,
                    bundleOf(
                        getString(R.string.ARG_MESSAGE) to getString(R.string.settings_help_description))
                )
            }
            true
        }
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)

    }

}