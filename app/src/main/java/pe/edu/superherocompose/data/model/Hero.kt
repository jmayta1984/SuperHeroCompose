package pe.edu.superherocompose.data.model

import com.google.gson.annotations.SerializedName


data class HeroResponse(
    @SerializedName("results")
    val heroes: List<Hero>
)

data class Hero(
    val id: String,
    val name: String,
    val biography: Biography,
    @SerializedName("powerstats")
    val powerStats: PowerStats,
    val image: HeroImage
)

data class Biography(
    @SerializedName("full-name")
    val fullName: String
)

data class HeroImage(
    val url: String
)

data class PowerStats(
    val intelligence: String,
    val strength: String,
    val speed: String,
    val durability: String,
    val power: String,
    val combat: String
)