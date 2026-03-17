package com.example.reservaspistas.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Reserva::class],
    version = 5
)
//si se hace algun cambio al codigo y no permite
// visualizar lo mas probable es que toca cambiar la version del database
//para actualizarlo de la siguiente forma version = 3 -> version = 4 -> version=5
// intentar hacer que la version cambie aumentandole 1 a la version para
// que no halla tanto problema en identificar la version

abstract class AppDatabase : RoomDatabase() {

    abstract fun reservaDao(): ReservaDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "reservas_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}