package com.example.learnsmarter.pages

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
//import androidx.compose.foundation.R
import com.example.learnsmarter.R
import com.example.learnsmarter.R.drawable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.room.util.TableInfo.Column
import com.google.firebase.auth.FirebaseAuth
import com.example.learnsmarter.api.TranslateWord.translateWord
import com.example.learnsmarter.ui.theme.LightColorScheme
import com.example.learnsmarter.viewmodel.FlashcardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFlashcardPage(viewModel: FlashcardViewModel, navController: NavController) {
    val isLoggedIn = FirebaseAuth.getInstance().currentUser != null

    val englishWord = remember { mutableStateOf("") }
    val polishWord = remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Add Flashcard")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LightColorScheme.primaryContainer,
                    titleContentColor = LightColorScheme.onSecondary
                )
            )
        },
        containerColor = LightColorScheme.primary
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(LightColorScheme.primary)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // English input row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.uk),
                        contentDescription = "UK flag",
                        modifier = Modifier.size(32.dp)
                    )

                    OutlinedTextField(
                        value = englishWord.value,
                        onValueChange = { englishWord.value = it },
                        label = { Text("Enter English word") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Button(
                    onClick = {
                        translateWord(
                            englishWord.value,
                            onSuccess = { word -> polishWord.value = word },
                            onError = { err -> println("Error: $err") }
                        )
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightColorScheme.primaryContainer,
                        contentColor = LightColorScheme.onPrimaryContainer
                    )
                ) {
                    Text("Search")
                }

                // Polish translation row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.poland),
                        contentDescription = "Polish flag",
                        modifier = Modifier.size(32.dp)
                    )

                    OutlinedTextField(
                        value = polishWord.value,
                        onValueChange = { polishWord.value = it },
                        label = { Text("Enter translation") },
                        modifier = Modifier.weight(1f)
                    )
                }

                // Add flashcard button
                Button(
                    onClick = {
                        viewModel.addFlashcard(
                            englishWord.value,
                            polishWord.value
                        ) {
                            Toast.makeText(context, "Flashcard added!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightColorScheme.primaryContainer,
                        contentColor = LightColorScheme.onPrimaryContainer
                    )
                ) {
                    Text("Add Flashcard")
                }
            }
        }
    }
}
