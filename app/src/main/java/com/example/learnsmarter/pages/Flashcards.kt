package com.example.learnsmarter.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.learnsmarter.viewmodel.FlashcardViewModel
import com.example.learnsmarter.models.Flashcard
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import com.example.learnsmarter.R

@Composable
fun Flashcards(viewModel: FlashcardViewModel) {

    val flashcards by viewModel.flashcards.collectAsState()
//    val pullRefreshState = rememberPullRefreshState(
//        refreshing = false,
//        onRefresh = { viewModel.loadFlashcards() }
//    )

    LaunchedEffect(Unit) {
        viewModel.loadFlashcards()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
//            .pullToRefresh(pullRefreshState)
            .background(MaterialTheme.colorScheme.background)
            .offset(y = 10.dp)
            .padding(16.dp)
            .height(20.dp)
    ) {
        if (flashcards.isEmpty()) {
            Text(
                text = "No flashcards available",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(flashcards) { flashcard ->
                    var showTranslation by remember { mutableStateOf(false) }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showTranslation = !showTranslation },
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(
                                        id = if (showTranslation) R.drawable.poland else R.drawable.uk
                                    ),
                                    contentDescription = "Language Flag",
                                    modifier = Modifier
                                        .size(24.dp)
                                        .padding(end = 8.dp)
                                )
                                Text(
                                    text = if (showTranslation) flashcard.wordPol else flashcard.wordEng,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
//                            Spacer(modifier = Modifier.height(4.dp))
//                            Text(
//                                text = "PL: ${flashcard.wordPol}",
//                                style = MaterialTheme.typography.bodyLarge,
//                                color = MaterialTheme.colorScheme.onSurface
//                            )
                        }
                    }
                }
            }
        }
    }
}

