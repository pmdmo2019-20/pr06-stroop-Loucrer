package es.iessaladillo.pedrojoya.stroop.ui.game

import android.graphics.Color
import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.stroop.base.Event
import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.GameDao
import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.UserDao
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.Game
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.User
import kotlin.concurrent.thread
import kotlin.random.Random


class GameViewModel(
   private val gameDao: GameDao,
   private val userDao: UserDao
) : ViewModel() {

    @Volatile
    private var isGameFinished: Boolean = false
    @Volatile
    private var currentWordMillis: Int = 0
    @Volatile
    private var millisUntilFinished: Int = 0
    private val handler: Handler = Handler()

    private val listWords : List<String> = listOf("Red", "Blue", "Yellow", "Green")

    private val listColors : List<Int> = listOf(Color.RED,Color.BLUE, Color.YELLOW, Color.GREEN)


    // Este es el color que le vamos a pintar a la palabra
    private val _color : MutableLiveData<Int> = MutableLiveData(listColors[Random.nextInt(4)])
    val currentColor: LiveData<Int>
        get() = _color

    // La palabra que se va ha mostrar
    private val _word : MutableLiveData<String> = MutableLiveData(listWords[Random.nextInt(4)])
    val currentWord: LiveData<String>
        get() = _word

    // El total de las palabras mostradas
    private val _wordsCount : MutableLiveData<Int> = MutableLiveData(0)
    val currentWordCount: LiveData<Int>
        get() = _wordsCount

    // El total de puntos que tenemos
    private val _points : MutableLiveData<Int> = MutableLiveData(0)
    val currentPoint: LiveData<Int>
        get() = _points

    // El numero de intentos que nos va quedando
    private val _attempts : MutableLiveData<Int> = MutableLiveData(0)
    val currentAttempts: LiveData<Int>
        get() = _attempts

    // El numero de palabras correctas
    private val _rightWords : MutableLiveData<Int> = MutableLiveData(0)
    val currentRightWords: LiveData<Int>
        get() = _rightWords

    // El numero de palabras incorrectas
    private val _wrongWords : MutableLiveData<Int> = MutableLiveData(0)
    val currentWrongWords: LiveData<Int>
        get() = _wrongWords

    // El modo de juego que tenemos
    private val _modGame : MutableLiveData<String> = MutableLiveData("")

    // Para la actualizacion de la progressBar
    private val _prBar : MutableLiveData<Int> = MutableLiveData(millisUntilFinished)
    val currentPrBar: LiveData<Int>
        get() = _prBar

    // Para comprobar cuando termina el juego
    private val _finish : MutableLiveData<Boolean> = MutableLiveData(isGameFinished)
    val currentGameFinish: LiveData<Boolean>
        get() = _finish

    // Para mostrar en el caso de error
    private val _message : MutableLiveData<Event<String>> = MutableLiveData()
    val message : LiveData<Event<String>>
        get() = _message

    // Almacenaremos la partida resultante
    private val _currentGame: MutableLiveData<Game> = MutableLiveData()

    /*FUNCIONES MIAS*/
    fun setCurrentAttempts(attempts: Int){
        _attempts.value = attempts
    }

    private fun incrementWordCount(){
        // Esta es la forma de hacer el incremento
        _wordsCount.value = _wordsCount.value?.plus(1)
    }

    private fun incrementWordWrong(){
        _wrongWords.value = _wrongWords.value?.plus(1)
    }

    private fun decrementAttemptsCount(){
        // Esta es la forma de hacer el decremento
        _attempts.value = _attempts.value?.minus(1)
    }

    private fun sumPoints(){
        _points.value = _points.value?.plus(10)
    }

    private fun countCorrectWord(){
        _rightWords.value = _rightWords.value?.plus(1)
    }

    fun setModGame(modGame: String) {
        _modGame.value = modGame
    }

    fun setGameResult(resultGame: Game){
        _currentGame.value = resultGame
    }

    /*QUERIES*/
    private fun insertGameFinalized(){
        thread {
            try {
                gameDao.insertGame(_currentGame.value!!)
            }catch (e: Exception){
                _message.postValue(Event("ERROR"))
            }
        }
    }

    fun queryUser(id: Long): User = userDao.queryUser(id)



    /*FUNCIONES DE PEDRO*/
    private fun onGameTimeTick(millisUntilFinished: Int) {
        // Observamos el movimiento de la barra
        _prBar.value = millisUntilFinished
    }

    private fun onGameTimeFinish() {
        isGameFinished = true
        _finish.value = isGameFinished // Notificamos
        // Insertamos la partida finalizada
        insertGameFinalized()
    }

    private fun isAttemptsLessOrEqualZero() {
        incrementWordWrong()
        if (_modGame.value == "attempts") {
            isDecrementAttempts()
            if (_attempts.value!! <= 0) {
                isGameFinished = true
                _finish.value = isGameFinished
                // Insertamos la partida finalizada
                insertGameFinalized()
            }
        }
    }

    private fun nextWord() {
        _word.value = listWords[Random.nextInt(4)]
        _color.value = listColors[Random.nextInt(4)]
        incrementWordCount()
    }

    fun checkRight() {
        currentWordMillis = 0
        if(listWords.indexOf(_word.value) == listColors.indexOf(_color.value)){
            // Aumentar uno en las correcta
            countCorrectWord()
            // sumamos puntos
            sumPoints()
        } else {
            isAttemptsLessOrEqualZero()
        }
        // Cambiar palabra
        nextWord()
    }


    fun checkWrong() {
        currentWordMillis = 0
        if(listWords.indexOf(_word.value) != listColors.indexOf(_color.value)){
            countCorrectWord()
            sumPoints()
        } else {
            isAttemptsLessOrEqualZero()
        }
        nextWord()
    }

    private fun isDecrementAttempts() {
        if (_modGame.value == "attempts") {
            decrementAttemptsCount()
        }
    }

    /*NO TOCAR*/
    fun startGameThread(gameTime: Int, wordTime: Int) {
        millisUntilFinished = gameTime
        currentWordMillis = 0
        isGameFinished = false
        val checkTimeMillis: Int = wordTime / 2
        thread {
            try {
                while (!isGameFinished) {
                    Thread.sleep(checkTimeMillis.toLong())
                    // Check and publish on main thread.
                    handler.post {
                        if (!isGameFinished) {
                            if (currentWordMillis >= wordTime) {
                                currentWordMillis = 0
                                nextWord()
                            }
                            currentWordMillis += checkTimeMillis
                            millisUntilFinished -= checkTimeMillis
                            onGameTimeTick(millisUntilFinished)
                            if (millisUntilFinished <= 0) {
                                onGameTimeFinish()
                            }
                        }
                    }
                }
            } catch (ignored: Exception) {
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        isGameFinished = true
    }



}