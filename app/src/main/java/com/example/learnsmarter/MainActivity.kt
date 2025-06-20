package com.example.learnsmarter

import FlashcardPager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.learnsmarter.pages.AddFlashcardPage

//pages
import com.example.learnsmarter.pages.Home
import com.example.learnsmarter.pages.SignInScreen
import com.example.learnsmarter.pages.Main
import com.example.learnsmarter.pages.SplashScreen

import com.example.learnsmarter.ui.theme.LearnSmarterTheme

import com.example.learnsmarter.viewmodel.AuthViewModel
import com.example.learnsmarter.viewmodel.FlashcardViewModel

import com.example.learnsmarter.utils.getGoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this) // init firebase
        val isLoggedIn = FirebaseAuth.getInstance().currentUser != null // variable to check whether useer is logged in
        val startDest = "Splash"

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            val context = LocalContext.current
            val authViewModel: AuthViewModel = viewModel()
            val flashcardViewModel: FlashcardViewModel = viewModel()

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) { result ->
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    authViewModel.signInWithGoogle(
                        account,
                        onSuccess = {
                            navController.navigate("Main")
                        },
                        onError = { errorMsg ->
                            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
                        }
                    )
                } catch (e: Exception) {
                    Toast.makeText(context, "Sign-in failed", Toast.LENGTH_SHORT).show()
                }
            }

            val signInClient = getGoogleSignInClient(context)

            NavHost(navController = navController, startDestination = startDest) {

                composable("Splash") {
                    SplashScreen(navController)
                }
                composable("home") {
                    Home(navController)
                }
                composable("SignIn") {
                    SignInScreen(navController) {
                        launcher.launch(signInClient.signInIntent)
                    }
                }
                composable("Main"){
                    Main(
                        authViewModel = authViewModel,
                        navController = navController
                    )

                }
                composable("AddFlashcard"){
                    AddFlashcardPage(flashcardViewModel,navController)
                }
                composable("Flashcards"){

                    LaunchedEffect(Unit) {
                        flashcardViewModel.loadFlashcards()
                    }
                    val flashcards = flashcardViewModel.flashcards.collectAsState()
                    val flashcardsList = flashcards.value

                    FlashcardPager(navController,flashcardsList)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LearnSmarterTheme {
        Greeting("Android")
    }
}