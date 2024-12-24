package com.joaograca.stockmarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joaograca.stockmarket.ui.companyInfo.CompanyInfoRoute
import com.joaograca.stockmarket.ui.companyInfo.CompanyInfoScreen
import com.joaograca.stockmarket.ui.companyListings.CompanyListingsRoute
import com.joaograca.stockmarket.ui.theme.DarkColorScheme
import com.joaograca.stockmarket.ui.theme.StockSpotlightTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(scrim = DarkColorScheme.background.toArgb()),
            navigationBarStyle = SystemBarStyle.dark(scrim = DarkColorScheme.background.toArgb())
        )
        setContent {
            StockSpotlightTheme {
                Scaffold(contentWindowInsets = WindowInsets.systemBars) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = "company_listings"
                    ) {
                        composable(route = "company_listings") {
                            CompanyListingsRoute(
                                onClickCompany = {
                                    navController.navigate(CompanyInfoRoute(it.symbol))
                                }
                            )
                        }
                        composable<CompanyInfoRoute> {
                            CompanyInfoScreen()
                        }
                    }
                }
            }
        }
    }
}
