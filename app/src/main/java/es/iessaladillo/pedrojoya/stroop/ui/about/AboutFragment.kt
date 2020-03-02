package es.iessaladillo.pedrojoya.stroop.ui.about


import android.os.Bundle
import androidx.fragment.app.Fragment
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import kotlinx.android.synthetic.main.about_fragment.*

class AboutFragment : Fragment(R.layout.about_fragment) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        setupToolBar()
    }

    private fun setupToolBar() {
        //Atento a los imports de donde obtenemos el id de la toolbar
        toolbar.inflateMenu(R.menu.fragments_menu)
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)
    }

}
