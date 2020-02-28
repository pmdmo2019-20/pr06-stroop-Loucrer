package es.iessaladillo.pedrojoya.stroop.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import es.iessaladillo.pedrojoya.stroop.R


class MainActivity : AppCompatActivity() {

    // Obtenemos el grafo de navegacion:
    private val navController: NavController by lazy {
        findNavController(R.id.navHostFragment) //Este es el id de nuestro grafo en nuestra actividad
    }



    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setupViews()
    }

    private fun setupViews() {

    }


}
