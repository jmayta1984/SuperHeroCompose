package pe.edu.superherocompose.ui.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pe.edu.superherocompose.ui.herodetail.HeroDetail
import pe.edu.superherocompose.ui.herolist.Search

@Composable
fun Home() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Search.route) {

        composable(Routes.Search.route) {
            Search() { id ->
                navController.navigate("${Routes.Detail.route}/$id")
            }
        }

        composable(
            route = Routes.Detail.routeWithArgument,
            arguments = listOf(navArgument(Routes.Detail.argument) { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") as String
            HeroDetail(id)
        }


    }
}

sealed class Routes(val route: String) {
    object Search : Routes("HeroSearch")
    object Detail : Routes("HeroDetail") {
        const val routeWithArgument = "HeroDetail/{id}"
        const val argument = "id"
    }
}