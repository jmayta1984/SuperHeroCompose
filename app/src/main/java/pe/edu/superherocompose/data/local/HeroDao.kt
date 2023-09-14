package pe.edu.superherocompose.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HeroDao {

    @Insert
    fun save(hero: HeroEntity)

    @Delete
    fun delete(hero: HeroEntity)

    @Query("select * from heroes")
    fun getAll(): List<HeroEntity>

    @Query("select * from heroes where :id")
    fun getById(id: String): HeroEntity?
}