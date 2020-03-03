package es.iessaladillo.pedrojoya.stroop.data.baseData

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.GameDao
import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.UserDao
import es.iessaladillo.pedrojoya.stroop.data.baseData.dao.UserWithGameDao
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.Game
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.User
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.UserGame

// En entities añadiremos todas las tablas que contendra nuestra base de datos
// Esto es copy paste de los apuntes
@Database(
    entities = [User::class, Game::class, UserGame::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract val userDao: UserDao
    abstract val gameDao: GameDao
    abstract val userWithGameDao: UserWithGameDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "app_database"
                        )
                            //Si la hace en el UI el propio android piensa que va a ralentizar el sistema y peta
                            //por eso es necesario llamar a esta funcion antes del build()
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}