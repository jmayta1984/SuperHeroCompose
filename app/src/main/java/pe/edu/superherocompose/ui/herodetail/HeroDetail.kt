package pe.edu.superherocompose.ui.herodetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.superherocompose.data.local.AppDatabase
import pe.edu.superherocompose.data.model.Biography
import pe.edu.superherocompose.data.model.Hero
import pe.edu.superherocompose.repository.HeroRepository
import pe.edu.superherocompose.utils.Result


@Composable
fun HeroDetail(id: String) {
    val context = LocalContext.current
    val heroDao = AppDatabase.getInstance(context).heroDao()
    val repository = HeroRepository(heroDao)
    val hero = remember {
        mutableStateOf<Hero?>(null)
    }
    repository.searchById(id = id) { result ->
        hero.value = result.data!!
    }

    if (hero.value != null) {

        Column {
            Spacer(modifier = Modifier.height(64.dp))
            HeroImage(hero.value!!.image.url)
            Spacer(modifier = Modifier.height(8.dp))
            HeroHeader(hero.value!!.name)
            Spacer(modifier = Modifier.height(8.dp))
            HeroBiography(hero.value!!)
            Spacer(modifier = Modifier.height(8.dp))
            HeroPowerStats(hero.value!!)


        }

    }
}


@Composable
fun HeroImage(url: String) {
    GlideImage(
        imageModel = { url },
        imageOptions = ImageOptions(contentScale = ContentScale.Crop),
        modifier = Modifier
            .size(256.dp)
            .clip(shape = RoundedCornerShape(4.dp))
    )
}

@Composable
fun HeroHeader(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold
    )

}

@Composable
fun HeroBiography(hero: Hero) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
            .shadow(elevation = 2.dp)
    ) {
        Column {
            Text(
                text = "Biography",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )
            Text(text = hero.biography.fullName)
        }
    }
}

@Composable
fun HeroPowerStats(hero: Hero) {
    val power = hero.powerStats.speed

    power.toFloatOrNull()?.let {
        Slider(value = it, onValueChange = {}, valueRange = 0f..100f)
    }
}