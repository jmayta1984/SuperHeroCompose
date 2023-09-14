package pe.edu.superherocompose.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HeroEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun heroDao(): HeroDao

    companion object {
        fun getInstance(context: Context): AppDatabase {
            val db =
                Room.databaseBuilder(context, AppDatabase::class.java, "hero_compose_db")
                    .allowMainThreadQueries()
                    .build()
            return db
        }
    }
}