package com.example.exp23.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.exp23.ui.home.HomeScreen
import com.example.exp23.ui.shared.NavigationPath
import com.example.exp23.ui.theme.Exp23Theme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.WalletConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        TestSingleton.activity = this@MainActivity
        onCreateForGooglePay(this@MainActivity)
        setContent {
            Exp23Theme {
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    SystemUiController()
//                    NavComposeApp()
//                }
                ParentScreen()
            }
        }
    }
}

@Composable
fun NavComposeApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = NavigationPath.HomeScreen) {
        composable(NavigationPath.HomeScreen) {
            HomeScreen()
        }
    }
}

@Composable
fun SystemUiController() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = false

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.White,
            darkIcons = useDarkIcons
        )
    }
}

private fun onCreateForGooglePay(activity: MainActivity) {
    val walletOptions = Wallet.WalletOptions.Builder()
        .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
        .build()

    val mGoogleApiClient = Wallet.getPaymentsClient(activity, walletOptions)
}
