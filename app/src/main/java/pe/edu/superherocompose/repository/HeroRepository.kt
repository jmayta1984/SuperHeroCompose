package pe.edu.superherocompose.repository

import pe.edu.superherocompose.data.local.HeroDao
import pe.edu.superherocompose.data.local.HeroEntity
import pe.edu.superherocompose.data.model.Hero
import pe.edu.superherocompose.data.model.HeroResponse
import pe.edu.superherocompose.data.remote.ApiClient
import pe.edu.superherocompose.data.remote.HeroService
import pe.edu.superherocompose.utils.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HeroRepository(
    private val heroDao: HeroDao,
    private val heroService: HeroService = ApiClient.getHeroService()
) {

    fun searchByName(name: String, callback: (Result<List<Hero>>) -> Unit) {
        val searchByName = heroService.searchByName(textQuery = name)
        searchByName.enqueue(object : Callback<HeroResponse> {
            override fun onResponse(call: Call<HeroResponse>, response: Response<HeroResponse>) {
                if (response.isSuccessful) {
                    try {
                        val heroes = response.body()!!.heroes
                        heroes.forEach { hero ->
                            hero.isFavorite = heroDao.getById(hero.id) != null
                        }
                        callback(Result.Success(response.body()!!.heroes))
                    } catch (e: Exception) {
                        callback(Result.Success(listOf<Hero>()))
                    }
                }
            }

            override fun onFailure(call: Call<HeroResponse>, t: Throwable) {
                callback(Result.Error(t.localizedMessage as String))
            }
        })
    }

    fun searchById(id: String, callback: (Result<Hero>) -> Unit) {
        val searchById = heroService.searchById(id = id)

        searchById.enqueue(object : Callback<Hero> {
            override fun onResponse(call: Call<Hero>, response: Response<Hero>) {
                if (response.isSuccessful) {
                    callback(Result.Success(response.body()!!))
                }
            }

            override fun onFailure(call: Call<Hero>, t: Throwable) {
                callback(Result.Error(t.localizedMessage as String))
            }
        })
    }

    fun save(hero: Hero) {
        heroDao.save(HeroEntity(hero.id))
        hero.isFavorite = true
    }

    fun delete(hero: Hero) {
        heroDao.delete(HeroEntity(hero.id))
        hero.isFavorite = false
    }
}