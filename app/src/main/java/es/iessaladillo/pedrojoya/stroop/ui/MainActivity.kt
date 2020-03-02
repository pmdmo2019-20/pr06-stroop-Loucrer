package es.iessaladillo.pedrojoya.stroop.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener


class MainActivity : AppCompatActivity(), OnToolbarAvailableListener {


    private val settings: SharedPreferences by lazy {
        getSharedPreferences("com.mycompany.myAppName", MODE_PRIVATE)
    }

    private val appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(
            R.id.dashboardDestination)
            .build()

    // Obtenemos el grafo de navegacion:
    private val navController: NavController by lazy {
        findNavController(R.id.navHostFragment) //Este es el id de nuestro grafo en nuestra actividad
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setupViews()
    }

    private fun setupViews() {

    }

    override fun onToolbarCreated(toolbar: Toolbar) {
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onToolbarDestroyed() {}

    override fun onResume() {
        super.onResume()
        if (settings.getBoolean("FirstExecution", true)){
            navController.navigate(R.id.assistantDestination)
            settings.edit().putBoolean("FirstExecution", false).apply()
        }
    }


}
