package pe.edu.superherocompose.ui.herodetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.superherocompose.data.model.Biography
import pe.edu.superherocompose.data.model.Hero
import pe.edu.superherocompose.repository.HeroRepository
import pe.edu.superherocompose.utils.Result


@Composable
fun HeroDetail(id: String = "599") {

    val repository = HeroRepository()
    val hero = remember {
        mutableStateOf<Hero?>(null)
    }
    repository.searchById(id = id) { result ->
        hero.value = result.data!!
    }

    if (hero.value != null) {
        Column() {
            GlideImage(
                imageModel = { hero.value!!.image.url },
                imageOptions = ImageOptions(contentScale = ContentScale.Fit),
                modifier = Modifier.size(92.dp)
            )
        }
    }

}