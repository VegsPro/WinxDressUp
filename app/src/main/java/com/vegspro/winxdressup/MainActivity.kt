package com.vegspro.winxdressup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vegspro.winxdressup.ui.screens.*
import com.vegspro.winxdressup.ui.theme.WinxDressUpTheme
import com.vegspro.winxdressup.viewmodel.DressUpViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WinxDressUpTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    WinxApp()
                }
            }
        }
    }
}

@Composable
fun WinxApp() {
    val navController = rememberNavController()
    val viewModel: DressUpViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onStartDressUp = { navController.navigate("characters") },
                onOpenGallery = { navController.navigate("gallery") },
                onSelectRoom = { navController.navigate("rooms") }
            )
        }
        composable("characters") {
            CharacterSelectScreen(
                onCharacterSelected = { char ->
                    viewModel.selectCharacter(char)
                    navController.navigate("dressup")
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable("rooms") {
            RoomsScreen(
                onRoomSelected = { room ->
                    viewModel.selectRoom(room)
                    navController.navigate("characters")
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable("dressup") {
            DressUpScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable("gallery") {
            GalleryScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onLoadOutfit = { saved ->
                    viewModel.loadOutfit(saved)
                    navController.navigate("dressup") {
                        popUpTo("home")
                    }
                }
            )
        }
    }
}
