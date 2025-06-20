package com.example.learnsmarter.pages

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border

import com.example.learnsmarter.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.learnsmarter.ui.theme.LightColorScheme

import com.example.learnsmarter.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Main(
    authViewModel: AuthViewModel,
    navController: NavController
){

    val context = LocalContext.current
    val isLoggedIn = FirebaseAuth.getInstance().currentUser != null
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    val isTablet = screenWidth >= 600

    var imageSize = if (isTablet) 230.dp else 160.dp
    if(isLandscape && !isTablet){
        imageSize = 200.dp
    }

    if(!isLoggedIn){
        LaunchedEffect(Unit) {
            navController.navigate("SignIn") {
                popUpTo("home") { inclusive = true }
            }
        }
        return
    }




    Scaffold(
        modifier = Modifier.fillMaxSize()
            .fillMaxWidth(),

        bottomBar = {
            NavigationBar(
                windowInsets = NavigationBarDefaults.windowInsets,
                containerColor = LightColorScheme.primaryContainer,
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        authViewModel.signOut(context) {
                            navController.navigate("SignIn") {
                                popUpTo("home") { inclusive = true }
                            }
                        }
                    },
                    icon = {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Sign out")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("Flashcards") },
                    icon = {
                        Icon(Icons.Default.Star, contentDescription = "My Flashcards")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("AddFlashcard") },
                    icon = {
                        Icon(Icons.Default.AddCircle, contentDescription = "Add Flashcard")
                    }
                )
            }
        }
    ) { innerPadding ->
        if(!isLandscape) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(LightColorScheme.primary)
                        .padding(top = 5.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {


                    val imageModifier = Modifier
                        .size(imageSize)
                        .border(BorderStroke(4.dp, Color.Black), shape = CircleShape)
                        .clip(CircleShape)

                    Image(
                        painter = painterResource(id = R.drawable.icon_appandroid),
                        contentDescription = "Welcome Image",
                        contentScale = ContentScale.Crop,

                        modifier = imageModifier
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Welcome to Flashcard App!",
                        fontSize = 25.sp,
                        fontStyle = FontStyle.Normal,
                        modifier = Modifier
                            .padding(all = 5.dp),
                        color = LightColorScheme.onBackground
                    )
                }
            }
        }
        else{
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(LightColorScheme.primary),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_appandroid),
                    contentDescription = "Welcome Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(150.dp)
                        .border(BorderStroke(4.dp, Color.Black), shape = CircleShape)
                        .clip(CircleShape)
                )
                Text(
                    text = "Welcome to Flashcard App!",
                    fontSize = 28.sp,
                    fontStyle = FontStyle.Normal,
                    color = LightColorScheme.onBackground
                )
        }
    }

 }
}
