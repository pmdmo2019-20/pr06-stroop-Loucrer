package es.iessaladillo.pedrojoya.stroop.ui.game

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.game_fragment.*
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.data.baseData.AppDatabase
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.Game
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.User
import kotlinx.android.synthetic.main.main_activity.*

class GameFragment : Fragment(R.layout.game_fragment) {


    private val viewModel : GameViewModel by viewModels{
        GameViewModelFactory(
            AppDatabase.getInstance(this.requireContext()).gameDao,
            AppDatabase.getInstance(this.requireContext()).userDao)
    }

    val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(activity)
    }

    /*Para guardar los settings*/
    private var timeGame = ""
    private var wordTime = ""
    private var totalAttempts = ""
    private var modGame = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeGameFields()
        setupViews()
        observers()
    }

    private fun observers() {
        observeSameMod()
        observeModGame()
    }

    private fun observeSameMod() {
        viewModel.run {
            currentPrBar.observe(viewLifecycleOwner) {
                pbBar.progress = it
            }

            currentWordCount.observe(viewLifecycleOwner) {
                lblTotalWords.text = it.toString()
            }

            currentWord.observe(viewLifecycleOwner) {
                lblChangeWordsWithColors.text = it
            }

            currentColor.observe(viewLifecycleOwner) {
                lblChangeWordsWithColors.setTextColor(it)
            }

            currentRightWords.observe(viewLifecycleOwner) {
                lblTotalCorrect.text = it.toString()
            }


        }
    }

    private fun observeModGame(){
            if (modGame == "attempts") {
                viewModel.currentAttempts.observe(viewLifecycleOwner) {
                    lblTotalPointsOrAttempts.text = it.toString()
                }
            } else {
                viewModel.currentPoint.observe(viewLifecycleOwner) {
                    lblTotalPointsOrAttempts.text = it.toString()
                }
            }

    }

    private fun getCurrentGame():Game {
        val idUser = settings.getLong("currentPlayer", -1)
        val user = viewModel.queryUser(idUser)
        val wordCounts = viewModel.currentWordCount.value!!
        val totalCorrects = viewModel.currentRightWords.value!!
        val totalWrongs = viewModel.currentWrongWords.value!!
        val totalPoints = viewModel.currentPoint.value!!
        return Game(0,user,modGame,timeGame.toInt(), wordCounts , totalWrongs, totalCorrects, totalPoints)
    }

    private fun setupViews() {
        setupStartGame()
    }

    private fun setupStartGame() {
        viewModel.run {
            currentGameFinish.observe(viewLifecycleOwner){
                // Observamos si la partida a finalizado
                if(it){
                    //Creamos el juego finalizado
                    viewModel.setGameResult(getCurrentGame())
                    //Navegamos al resultado
                    findNavController().navigate(R.id.destinationToResult)
                } else {
                    play()
                    setupBtns()
                }
            }
        }
    }


    private fun play() {
        if (modGame == "time") {
            lblPointsOrAttempts.text = getString(R.string.game_points)
            lblTotalPointsOrAttempts.text = getString(R.string.defaultPoints)
        } else {
            lblPointsOrAttempts.text = getString(R.string.game_attempts)
            lblTotalPointsOrAttempts.text = totalAttempts
        }
        viewModel.startGameThread(timeGame.toInt(),wordTime.toInt())
    }

    private fun setupBtns() {
        imgCorrect.setOnClickListener {
            viewModel.checkRight()
        }
        imgWrong.setOnClickListener {
            viewModel.checkWrong()
        }
    }

    private fun initializeGameFields() {
        modGame = settings.getString(getString(R.string.prefGameMode_key), getString(R.string.prefGameMode_defaultValue))!!.toLowerCase()
        timeGame = settings.getString(getString(R.string.prefGameTime_key), getString(R.string.prefGameTime_defaultValue))!!
        wordTime = settings.getString(getString(R.string.prefWordTime_key), getString(R.string.prefWordTime_defaultValue))!!
        totalAttempts = settings.getString(getString(R.string.prefAttempts_key),getString(R.string.prefAttempts_defaultValue))!!
        viewModel.setCurrentAttempts(totalAttempts.toInt()) //Asignamos los intentos en el viewModel
        viewModel.setModGame(modGame) // Asigamos el modo de juego en el viewModel
        pbBar.max = timeGame.toInt() // Asignamos el tiempo maximo de la partida a la progressBar
    }


}
