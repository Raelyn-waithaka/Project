package com.example.giftyhaus.navigation

import UpdateOrderScreen

import com.example.giftyhaus.ui.theme.screens.home.HomeScreen
import com.example.giftyhaus.ui.theme.screens.login.LoginScreen




import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.giftyhaus.ui.theme.screens.bars.TopBarUI
import com.example.giftyhaus.ui.theme.screens.orders.AddProductScreen
import com.example.giftyhaus.ui.theme.screens.orders.CategoryProductsScreen

import com.example.giftyhaus.ui.theme.screens.products.ViewProductScreen
import com.example.giftyhaus.ui.theme.screens.register.RegisterScreen
import com.example.giftyhaus.ui.theme.screens.splashScreen.SplashScreen



@Composable
fun AppNavHost(navController:NavHostController= rememberNavController(),startDestination:String= ROUTE_SPLASH){
    NavHost(navController=navController,startDestination=startDestination) {
        composable(ROUTE_SPLASH) {
            SplashScreen {
                navController.navigate(ROUTE_REGISTER) {
                    popUpTo(ROUTE_SPLASH) { inclusive = true }
                }
            }

        }
        composable("category_products/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            CategoryProductsScreen(category = category, navController = navController)
        }

        composable (ROUTE_TOP_BAR) { TopBarUI() }
        composable ( ROUTE_REGISTER ){ RegisterScreen(navController) }
        composable(ROUTE_LOGIN) { LoginScreen(navController) }
        composable(ROUTE_ADD_PRODUCT) { AddProductScreen(navController) }
        composable(ROUTE_HOME) { HomeScreen(navController ) }
        composable(ROUTE_VIEW_ORDERS) {
            ViewProductScreen(navController)

        }
        composable("$ROUTE_UPDATE_PRODUCTS/{orderId}") { passedData ->
            UpdateOrderScreen(
                navController.toString()
            )
        }
    }
}