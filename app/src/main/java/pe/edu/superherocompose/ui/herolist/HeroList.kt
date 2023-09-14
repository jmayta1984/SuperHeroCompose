package pe.edu.superherocompose.ui.herolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.superherocompose.data.local.AppDatabase
import pe.edu.superherocompose.data.model.Hero
import pe.edu.superherocompose.repository.HeroRepository

@Composable
fun HeroList(
    heroes: MutableState<List<Hero>>,
    selectHero: (String) -> Unit
) {
    val context = LocalContext.current
    val heroDao = AppDatabase.getInstance(context).heroDao()
    val heroRepository = HeroRepository(heroDao)

    LazyColumn {
        items(heroes.value) { hero ->
            HeroRow(hero, selectHero,
                deleteHero = {
                    heroRepository.delete(hero)
                    hero.isFavorite = false
                },
                insertHero = {
                    heroRepository.save(hero)
                    hero.isFavorite = false

                }
            )
        }
    }
}

@Composable
fun HeroRow(
    hero: Hero,
    selectHero: (String) -> Unit,
    deleteHero: () -> Unit,
    insertHero: () -> Unit
) {
    val isFavorite = remember {
        mutableStateOf(false)
    }
    isFavorite.value = hero.isFavorite
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable {
                selectHero(hero.id)
            }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            HeroImage(hero.image.url)
            Column(modifier = Modifier.weight(5f)) {
                Text(text = hero.name, fontWeight = FontWeight.Bold)
                Text(text = hero.biography.fullName)
            }
            IconButton(modifier = Modifier.weight(1f), onClick = {
                if (isFavorite.value) {
                    deleteHero()
                } else {
                    insertHero()
                }
                isFavorite.value = !isFavorite.value
            }) {
                Icon(
                    Icons.Default.Favorite,
                    null,
                    tint = if (isFavorite.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun HeroImage(url: String) {
    GlideImage(
        imageModel = { url },
        imageOptions = ImageOptions(contentScale = ContentScale.Fit),
        modifier = Modifier.size(92.dp)
    )
}