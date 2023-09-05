package pe.edu.superherocompose.data.remote

import pe.edu.superherocompose.data.model.HeroResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HeroService {

    @GET("{api_token}/search/{text_query}")
    fun searchByName(
        @Path("api_token") apiToken: String = "10157703717092094",
        @Path("text_query") textQuery: String
    ): Call<HeroResponse>
}