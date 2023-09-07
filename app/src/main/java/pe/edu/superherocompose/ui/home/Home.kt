package pe.edu.superherocompose.ui.home

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pe.edu.superherocompose.ui.herodetail.HeroDetail
import pe.edu.superherocompose.ui.herolist.Search

@Composable
fun Home() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Detail.route){

        composable(Routes.Search.route){
            Search()
        }

        composable(Routes.Detail.route){
            HeroDetail()
        }

    }
}

sealed class Routes(val route: String){
    object Search: Routes("HeroSearch")
    object Detail: Routes("HeroDetail")
}